package site.klol.batch001.job.step01;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import site.klol.batch001.common.enums.YNFlag;
import site.klol.batch001.match.dto.MatchIdDTO;
import site.klol.batch001.match.entity.MatchHistory;
import site.klol.batch001.match.repository.MatchHistoryRepository;
import site.klol.batch001.riot.RiotAPIService;
import site.klol.batch001.summoner.entity.Summoner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class NewMatchIdStepConfig {
    private final MatchHistoryRepository matchHistoryRepository;
    private final JobRepository jobRepository;
    private final EntityManagerFactory emf;
    private final RiotAPIService riotAPIService;
    private static final int CHUNK_SIZE = 10;
    private static final String STEP_NAME = "newMatchIdStep";
    @Bean
    @JobScope
    // ToDo: mongodb transactionmanager추가하면 아마도 트랜잭션 매니저 2개여서 하나 특정해야 할 수 있음.
    public Step newMatchIdStep(PlatformTransactionManager transactionManager) {
        return new StepBuilder(STEP_NAME, jobRepository)
            .<Summoner, List<MatchHistory>>chunk(CHUNK_SIZE, transactionManager)
            .reader(summonerJpaPagingItemReader())
            .processor(compositeProcessor())
            .writer(newMatchIdListWriter())
            .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Summoner> summonerJpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<Summoner>()
            .name("summonerJpaPagingItemReader")
            .entityManagerFactory(emf)
            .pageSize(CHUNK_SIZE)
            .queryString("SELECT s FROM Summoner s")
            .build();
    }

    @Bean
    @StepScope
    public CompositeItemProcessor compositeProcessor() {
        List<ItemProcessor> delegates = new ArrayList<>(2);

        delegates.add(riotMatchIdProcessor());
        delegates.add(newMatchIdFilteringProcessor());

        CompositeItemProcessor processor = new CompositeItemProcessor<>();

        processor.setDelegates(delegates);

        return processor;
    }

    @Bean
    @StepScope
    public ItemProcessor<Summoner, MatchIdDTO> riotMatchIdProcessor() {
        return summoner -> {
            final String puuid = riotAPIService.getPuuid(summoner.getSummonerId(), summoner.getSummonerTag())
                .orElseThrow(IllegalArgumentException::new);
            final List<String> matchIdList = riotAPIService.getMatchIdList(puuid);

            return new MatchIdDTO(summoner, matchIdList);
        };
    }

    @Bean
    @StepScope
    public ItemProcessor<MatchIdDTO, List<MatchHistory>> newMatchIdFilteringProcessor() {
        return matchIdDto ->{
            List<MatchHistory> matchHistories = new ArrayList<>();

            final List<MatchHistory> oldMatchHistories = matchHistoryRepository.findAllBySummoner_SeqNo(matchIdDto.getSummoner().getSeqNo());
            final List<String> oldMatchIdList = oldMatchHistories.stream()
                .map(MatchHistory::getMatchId)
                .collect(Collectors.toCollection(ArrayList::new));

            ArrayList<String> filteredMatchIdList = matchIdDto.getMatchIdList().stream()
                .filter(matchId -> !oldMatchIdList.contains(matchId))
                .collect(Collectors.toCollection(ArrayList::new));

            for(String matchId : filteredMatchIdList) {
                matchHistories.add(MatchHistory.of(matchIdDto.getSummoner(), matchId, YNFlag.Y));
            }

            return matchHistories;
        };
    }

    @Bean
    @StepScope
    public JpaItemListWriter<MatchHistory> newMatchIdListWriter() {
        JpaItemWriter<MatchHistory> matchHistoryJpaItemWriter = new JpaItemWriter<>();
        JpaItemListWriter<MatchHistory> matchHistoryJpaItemListWriter = new JpaItemListWriter<>(matchHistoryJpaItemWriter);
        matchHistoryJpaItemWriter.setEntityManagerFactory(emf);
        matchHistoryJpaItemListWriter.setEntityManagerFactory(emf);

        return matchHistoryJpaItemListWriter;
    }
}
