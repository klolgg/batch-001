package site.klol.batch.batch001.step02;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.PlatformTransactionManager;
import site.klol.batch.common.LoggerContext;
import site.klol.batch.common.exception.NoSkipException;
import site.klol.batch.match.entity.MatchHistory;
import site.klol.batch.riot.service.V1RiotAPIService;

import static site.klol.batch.match.constants.MatchDetailsConstants.MATCH_DETAILS_COLLECTION_NAME;

@Configuration
@RequiredArgsConstructor
public class NewMatchDetailsStepConfig {
    private final JobRepository jobRepository;
    private final EntityManagerFactory emf;
    private final V1RiotAPIService v1RiotAPIService;
    private final MongoTemplate mongoTemplate;

    private static final int CHUNK_SIZE = 10;
    private static final String QUERY_STRING = "SELECT m FROM MatchHistory m WHERE m.isUpdated = 'N'";
    public static final String STEP_NAME = "newMatchDetailsStep";
    public static final String READER_NAME = "newMatchIdJpaPagingItemReader";

    @Bean
    @JobScope
    public Step newMatchDetailsStep(PlatformTransactionManager transactionManager, StepExecutionListener batchTerminationStepListener) {
        return new StepBuilder(STEP_NAME, jobRepository)
            .<MatchHistory, Object>chunk(CHUNK_SIZE, transactionManager)
            .reader(newMatchIdJpaPagingItemReader())
            .processor(matchDetailsProcessor())
            .writer(matchDetailsWriter())
            .faultTolerant()
            .noSkip(NoSkipException.class)
            .listener(batchTerminationStepListener)
            .build();
    }
    @Bean
    @StepScope
    public JpaPagingItemReader<MatchHistory> newMatchIdJpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<MatchHistory>()
            .name(READER_NAME)
            .entityManagerFactory(emf)
            .pageSize(CHUNK_SIZE)
            .queryString(QUERY_STRING)
            .build();
    }
    @Bean
    @StepScope
    public ItemProcessor<MatchHistory, Object> matchDetailsProcessor() {
        return matchHistory -> {
            Query query = new Query();
            query.addCriteria(Criteria.where("metadata.matchId").is(matchHistory.getMatchId()));

            boolean matchDetails = mongoTemplate.exists(query, "match_details");

            if (matchDetails) {
                LoggerContext.getLogger().info("matchId is already exist!: {}", matchHistory.getMatchId());
                return null;
            }
            return v1RiotAPIService.getMatchDetails(matchHistory.getMatchId());
        };
    }

    @Bean
    @StepScope
    public MongoItemWriter<Object> matchDetailsWriter() {
        return new MongoItemWriterBuilder<Object>()
            .template(mongoTemplate)
            .collection(MATCH_DETAILS_COLLECTION_NAME)
            .mode(MongoItemWriter.Mode.INSERT)
            .build();
    }
}
