package site.klol.batch001.job.step03;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.PlatformTransactionManager;
import site.klol.batch001.common.exception.NoSkipException;
import site.klol.batch001.match.entity.MatchHistory;

@Configuration
@RequiredArgsConstructor
public class MatchHistoryProcessingStepConfig {
    private final JobRepository jobRepository;
    private final EntityManagerFactory emf;
    private final MongoTemplate mongoTemplate;
    private static final int CHUNK_SIZE = 10;
    private static final String QUERY_STRING = "SELECT m FROM MatchHistory m WHERE m.isUpdated = 'N'";
    public static final String STEP_NAME = "matchHistoryProcessingStep";
    public static final String READER_NAME = "notUpdatedMatchIdJpaPagingItemReader";
    @Bean
    @JobScope
    public Step matchHistoryProcessingStep(PlatformTransactionManager transactionManager, StepExecutionListener batchTerminationStepListener) {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<MatchHistory, MatchHistory>chunk(CHUNK_SIZE, transactionManager)
                .reader(notUpdatedMatchIdJpaPagingItemReader())
                .processor(matchHistoryProcessor())
                .writer(matchHistoryWriter())
                .faultTolerant()
                .noSkip(NoSkipException.class)
                .listener(batchTerminationStepListener)
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<MatchHistory> notUpdatedMatchIdJpaPagingItemReader() {
        JpaPagingItemReader<MatchHistory> reader = new JpaPagingItemReader<MatchHistory>() {
            // 같은 테이블을 대상으로 읽고 쓰기 때문에 page 번호를 0으로 고정.
            @Override
            public int getPage() {
                return 0;
            }
        };

        reader.setQueryString(QUERY_STRING);
        reader.setPageSize(CHUNK_SIZE);
        reader.setEntityManagerFactory(emf);
        reader.setName(READER_NAME);

        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<MatchHistory, MatchHistory> matchHistoryProcessor() {
        return matchHistory -> {
            // todo: 따로 component로 뺴야할거 같음.
            Query query = new Query();
            query.addCriteria(Criteria.where("metadata.matchId").is(matchHistory.getMatchId()));

            boolean matchDetails = mongoTemplate.exists(query, "match_details");

            if (matchDetails) {
                matchHistory.setIsUpdatedToY();
            }

            return matchHistory;
        };
    }
    @Bean
    @StepScope
    public JpaItemWriter<MatchHistory> matchHistoryWriter() {
        return new JpaItemWriterBuilder<MatchHistory>()
            .entityManagerFactory(emf)
            .build();
    }
}
