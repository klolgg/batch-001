package site.klol.batch001.job.step01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import site.klol.batch001.summoner.entity.Summoner;
import site.klol.batch001.summoner.repository.SummonerRepository;

import java.util.Arrays;

@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class })
@SpringBatchTest
@SpringBootTest
@ActiveProfiles("test")
class NewMatchIdStepConfigTest {
    Summoner[] smnrList = new Summoner[12];

    @Autowired
    JpaPagingItemReader<Summoner> summonerJpaPagingItemReader;
    @Autowired
    SummonerRepository summonerRepository;

    @Test
    void test() {
        initTestData();

        long count = summonerRepository.count();
        Assertions.assertEquals(12L, count);
    }

    private void initTestData() {
        smnrList[0] = Summoner.builder()
            .summonerId("뾰로롱")
            .summonerTag("1111")
            .build();
        smnrList[1] = Summoner.builder()
            .summonerId("뾰로롱")
            .summonerTag("1112")
            .build();
        smnrList[2] = Summoner.builder()
            .summonerId("뾰로롱")
            .summonerTag("1113")
            .build();
        smnrList[3] = Summoner.builder()
            .summonerId("뾰로롱")
            .summonerTag("1114")
            .build();
        smnrList[4] = Summoner.builder()
            .summonerId("뾰로롱")
            .summonerTag("1115")
            .build();
        smnrList[5] = Summoner.builder()
            .summonerId("뾰로롱")
            .summonerTag("1116")
            .build();
        smnrList[6] = Summoner.builder()
            .summonerId("뾰로롱")
            .summonerTag("1117")
            .build();
        smnrList[7] = Summoner.builder()
            .summonerId("뾰로롱")
            .summonerTag("1118")
            .build();
        smnrList[8] = Summoner.builder()
            .summonerId("뾰로롱")
            .summonerTag("1119")
            .build();
        smnrList[9] = Summoner.builder()
            .summonerId("뾰로롱")
            .summonerTag("1120")
            .build();
        smnrList[10] = Summoner.builder()
            .summonerId("뾰로롱")
            .summonerTag("1121")
            .build();
        smnrList[11] = Summoner.builder()
            .summonerId("뾰로롱")
            .summonerTag("1122")
            .build();
        summonerRepository.saveAll(Arrays.asList(smnrList));
    }

}