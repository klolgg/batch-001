package site.klol.batch.batch001;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import site.klol.batch.batch001.constants.JobParamConstant;
import site.klol.batch.common.enums.YNFlag;
import site.klol.batch.match.entity.MatchHistory;
import site.klol.batch.match.repository.MatchHistoryRepository;
import site.klol.batch.summoner.entity.Summoner;
import site.klol.batch.summoner.repository.SummonerRepository;
import site.klol.batch.user.entity.User;
import site.klol.batch.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@SpringBatchTest
@SpringBootTest
@ActiveProfiles("test")
class Batch001JobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private SummonerRepository summonerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    MatchHistoryRepository matchHistoryRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    @AfterEach
    void deleteAll() {
        mongoTemplate.getCollection("match_details").deleteMany(new org.bson.Document());
    }

    @Test
    @DisplayName("테스트 데이터의 최근 40게임 정보가 성공적으로 삽입됐는지 확인하기")
    void batch001Job(@Autowired Job job) throws Exception {
        this.jobLauncherTestUtils.setJob(job);
        initData();

        JobParameters jobParameters = new JobParametersBuilder()
            .addString(JobParamConstant.REQUEST_DATE, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
            .addString(JobParamConstant.REQUEST_TIME, LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
            .toJobParameters();

        System.out.printf("JobParam: %s", jobParameters);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        List<MatchHistory> all = matchHistoryRepository.findAll().stream()
            .filter(m -> m.getIsUpdated().equals(YNFlag.Y))
            .collect(Collectors.toList());

        Assertions.assertThat(all.size()).isEqualTo(40);
        Assertions.assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    public void initData() {
        User user = User.of("testKakaoId", "testNickname", YNFlag.Y);
        User user2 = User.of("testKakaoId2", "testNickname2", YNFlag.Y);

        User saveUser = userRepository.save(user);
        User saveUser2 = userRepository.save(user2);

        Summoner summoner = Summoner.builder()
            .isMainSummoner(YNFlag.N)
            .user(saveUser)
            .puuid("_PT8-VdDjDwBeoLpGPstPjxyLSnp_ra6IVPz-XpnyEr5OKutq-siMcML7sh6s1_2SKD09Afy2pwkQw")
            .summonerId("뾰로롱")
            .summonerTag("1111")
            .summonerIcon("testIconUrl")
            .summonerLevel(1)
            .summonerLp(1)
            .summonerTier("testTier")
            .build();
        Summoner summoner2 = Summoner.builder()
            .isMainSummoner(YNFlag.N)
            .user(saveUser)
            .puuid("testpuuid")
            .summonerId("Destiny")
            .summonerTag("KR1")
            .summonerIcon("testIconUrl")
            .summonerLevel(1)
            .summonerLp(1)
            .summonerTier("testTier")
            .build();


        Summoner saveSummoner = summonerRepository.save(summoner);
        Summoner saveSummoner2 = summonerRepository.save(summoner2);
    }
}