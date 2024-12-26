package site.klol.batch001.job.step02;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
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
import org.springframework.transaction.PlatformTransactionManager;
import site.klol.batch001.common.enums.YNFlag;
import site.klol.batch001.match.entity.MatchHistory;
import site.klol.batch001.riot.RiotAPIService;

@Configuration
@RequiredArgsConstructor
public class NewMatchDetailsStepConfig {
    private final JobRepository jobRepository;
    private final EntityManagerFactory emf;
    private final RiotAPIService riotAPIService;
    private final MongoTemplate mongoTemplate;
    private static final int CHUNK_SIZE = 10;
    private static final String STEP_NAME = "newMatchDetailsStep";

    @Bean
    public Step newMatchDetailsStep(PlatformTransactionManager transactionManager) {
        return new StepBuilder(STEP_NAME, jobRepository)
            .<MatchHistory, String>chunk(CHUNK_SIZE, transactionManager)
            .reader(newMatchIdJpaPagingItemReader())
            .processor(matchDetailsProcessor())
            .writer(matchDetailsWriter())
            .build();
    }

    @Bean
    public MongoItemWriter<String> matchDetailsWriter() {
        return new MongoItemWriterBuilder<String>()
            .template(mongoTemplate)
            .collection("match_details")
            .build();
    }

    private ItemProcessor<MatchHistory, String> matchDetailsProcessor() {
        return matchHistory -> {
            String matchDetails = riotAPIService.getMatchDetails(matchHistory.getMatchId());

            matchHistory.setIsUpdated(YNFlag.Y);

            return matchDetails;
        };
    }

    private JpaPagingItemReader<MatchHistory> newMatchIdJpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<MatchHistory>()
            .name("newMatchIdJpaPagingItemReader")
            .entityManagerFactory(emf)
            .pageSize(CHUNK_SIZE)
            .queryString("SELECT m FROM MatchHistory m WHERE m.isUpdated = 'N'")
            .build();
    }
}
