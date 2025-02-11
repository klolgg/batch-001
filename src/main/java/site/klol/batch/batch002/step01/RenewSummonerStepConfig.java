package site.klol.batch.batch002.step01;


import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import site.klol.batch.common.exception.NoSkipException;
import site.klol.batch.riot.dto.AccountDTO;
import site.klol.batch.riot.service.V1RiotAPIService;
import site.klol.batch.summoner.entity.Summoner;

@Configuration
@RequiredArgsConstructor
public class RenewSummonerStepConfig {

    private final JobRepository jobRepository;
    private final EntityManagerFactory emf;
    private final V1RiotAPIService v1RiotAPIService;

    private static final int CHUNK_SIZE = 10;
    public static final String STEP_NAME = "renewSummonerStep";

    @Bean
    @JobScope
    public Step renewSummonerStep(
        PlatformTransactionManager transactionManager,
        StepExecutionListener batchTerminationStepListener,
        JpaPagingItemReader<Summoner> summonerJpaPagingItemReader
    ) {
        return new StepBuilder(STEP_NAME, jobRepository)
            .<Summoner, Summoner>chunk(CHUNK_SIZE, transactionManager)
            .reader(summonerJpaPagingItemReader)
            .processor(newAccountInfoByPuuidProcessor())
            .writer(renewSummonerWriter())
            .faultTolerant()
            .noSkip(NoSkipException.class)
            .listener(batchTerminationStepListener)
            .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<? super Summoner,? extends Summoner> newAccountInfoByPuuidProcessor() {
        return summoner -> {
            final String puuid = summoner.getPuuid();

            final AccountDTO accountByPUUID = v1RiotAPIService.getAccountByPUUID(puuid);

            if(summoner.isChangedAccount(accountByPUUID)){
                summoner.updateAccount(accountByPUUID);
            }

            return summoner;
        };
    }

    @Bean
    @StepScope
    public ItemWriter<? super Summoner> renewSummonerWriter() {
        JpaItemWriter<Summoner> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(emf);

        return jpaItemWriter;
    }

}
