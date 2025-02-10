package site.klol.batch.job.step01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import site.klol.batch.common.enums.YNFlag;
import site.klol.batch.summoner.entity.Summoner;
import site.klol.batch.summoner.repository.SummonerRepository;
import site.klol.batch.user.entity.User;
import site.klol.batch.user.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@SpringBatchTest
@SpringBootTest
@ActiveProfiles("test")
class NewMatchIdStepConfigUnitTest {
    @Autowired
    JpaPagingItemReader<Summoner> summonerJpaPagingItemReader;
    @Autowired
    SummonerRepository summonerRepository;
    @Autowired
    UserRepository userRepository;
    List<Summoner> saveSummoners;
    @Test
    void test() throws Exception {
        // given
        initTestData();
        int cnt = 0;
        long target = summonerRepository.count();
        // when
        summonerJpaPagingItemReader.open(new ExecutionContext());
        while(summonerJpaPagingItemReader.read() != null){
            cnt += 1;
        }
        // then
        Assertions.assertEquals(cnt, target);
    }

    private void initTestData() {
        User user = User.of("kakaoId", "nickname", YNFlag.N);

        userRepository.save(user);

        List<Summoner> summonerList = Arrays.asList(Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1111").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1112").puuid("testPuuid1").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1113").puuid("testPuuid2").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1114").puuid("testPuuid3").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1115").puuid("testPuuid4").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1116").puuid("testPuuid5").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1117").puuid("testPuuid6").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1118").puuid("testPuuid7").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1119").puuid("testPuuid8").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1120").puuid("testPuuid9").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1121").puuid("testPuuid10").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1122").puuid("testPuuid11").isMainSummoner(YNFlag.Y).build());

        saveSummoners = summonerRepository.saveAll(summonerList);
    }

}