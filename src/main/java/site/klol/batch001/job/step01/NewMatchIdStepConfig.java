package site.klol.batch001.job.step01;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.CollectionUtils;
import site.klol.batch001.common.exception.NoSkipException;
import site.klol.batch001.match.dto.MatchIdDTO;
import site.klol.batch001.match.entity.MatchHistory;
import site.klol.batch001.match.repository.MatchHistoryRepository;
import site.klol.batch001.riot.service.V1RiotAPIService;
import site.klol.batch001.summoner.entity.Summoner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Configuration for the first step of the batch job that processes new match IDs.
 * This step:
 * 1. Reads summoners from the database
 * 2. Fetches their match IDs from Riot API
 * 3. Filters out already processed matches
 * 4. Saves new match histories to the database
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class NewMatchIdStepConfig {
    private final MatchHistoryRepository matchHistoryRepository;
    private final JobRepository jobRepository;
    private final EntityManagerFactory emf;
    private final V1RiotAPIService v1RiotAPIService;

    private static final int CHUNK_SIZE = 10;
    private static final String QUERY_STRING = "SELECT s FROM Summoner s";
    public static final String STEP_NAME = "newMatchIdStep";
    public static final String READER_NAME = "summonerJpaPagingItemReader";

    @Bean
    @JobScope
    public Step newMatchIdStep(PlatformTransactionManager transactionManager, StepExecutionListener batchTerminationStepListener) {
        return new StepBuilder(STEP_NAME, jobRepository)
            .<Summoner, List<MatchHistory>>chunk(CHUNK_SIZE, transactionManager)
            .reader(summonerJpaPagingItemReader())
            .processor(compositeProcessor())
            .writer(newMatchIdListWriter())
            .faultTolerant()
            .noSkip(NoSkipException.class)
            .listener(batchTerminationStepListener)
            .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Summoner> summonerJpaPagingItemReader() {
        log.debug("Initializing summoner paging item reader");
        return new JpaPagingItemReaderBuilder<Summoner>()
            .name(READER_NAME)
            .entityManagerFactory(emf)
            .pageSize(CHUNK_SIZE)
            .queryString(QUERY_STRING)
            .build();
    }

    @Bean
    @StepScope
    public CompositeItemProcessor<Summoner, List<MatchHistory>> compositeProcessor() {
        List<ItemProcessor<?, ?>> delegates = new ArrayList<>(2);
        delegates.add(riotMatchIdProcessor());
        delegates.add(newMatchIdFilteringProcessor());

        CompositeItemProcessor<Summoner, List<MatchHistory>> processor = new CompositeItemProcessor<>();
        processor.setDelegates(delegates);

        return processor;
    }

    @Bean
    @StepScope
    public ItemProcessor<Summoner, MatchIdDTO> riotMatchIdProcessor() {
        return summoner -> {
            log.debug("Processing summoner: {}", summoner.getSummonerId());
            String puuid = summoner.getPuuid();

            final List<String> matchIdList = v1RiotAPIService.getMatchIdList(puuid);
            if (CollectionUtils.isEmpty(matchIdList)) {
                log.info("No matches found for summoner: {}#{}", summoner.getSummonerId(), summoner.getSummonerTag());
                return null;
            }

            log.debug("Found {} matches for summoner: {}#{}", matchIdList.size(), summoner.getSummonerId(), summoner.getSummonerTag());
            return new MatchIdDTO(summoner, matchIdList);
        };
    }

    @Bean
    @StepScope
    public ItemProcessor<MatchIdDTO, List<MatchHistory>> newMatchIdFilteringProcessor() {
        return matchIdDto -> {
            final Summoner summoner = matchIdDto.getSummoner();
            log.debug("Filtering matches for summoner: {}", summoner.getSummonerId());

            final List<String> oldMatchIdList = findOldMatchIdList(summoner);
            final List<MatchHistory> newMatchHistories = getNewMatchHistories(matchIdDto, oldMatchIdList, summoner);

            if (newMatchHistories.isEmpty()) {
                log.info("No new matches found for summoner: {}", summoner.getSummonerId());
                return null;
            }

            log.debug("Found {} new matches for summoner: {}", newMatchHistories.size(), summoner.getSummonerId());
            return newMatchHistories;
        };
    }

    private static List<MatchHistory> getNewMatchHistories(MatchIdDTO matchIdDto, List<String> oldMatchIdList, Summoner summoner) {
        return matchIdDto.getMatchIdList().stream()
            .filter(matchId -> !oldMatchIdList.contains(matchId))
            .map(newMatchId -> MatchHistory.getNotUpdatedMatchHistory(summoner, newMatchId))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<String> findOldMatchIdList(Summoner summoner) {
        return matchHistoryRepository.findAllBySummoner_SeqNo(summoner.getSeqNo()).stream()
            .map(MatchHistory::getMatchId)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    @Bean
    @StepScope
    public JpaItemListWriter<MatchHistory> newMatchIdListWriter() {
        log.debug("Initializing match history list writer");
        JpaItemWriter<MatchHistory> matchHistoryJpaItemWriter = new JpaItemWriter<>();
        JpaItemListWriter<MatchHistory> matchHistoryJpaItemListWriter = new JpaItemListWriter<>(matchHistoryJpaItemWriter);
        
        matchHistoryJpaItemWriter.setEntityManagerFactory(emf);
        matchHistoryJpaItemListWriter.setEntityManagerFactory(emf);

        return matchHistoryJpaItemListWriter;
    }
}
