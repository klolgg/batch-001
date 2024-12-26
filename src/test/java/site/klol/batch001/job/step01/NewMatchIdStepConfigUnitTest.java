package site.klol.batch001.job.step01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import site.klol.batch001.common.enums.YNFlag;
import site.klol.batch001.summoner.entity.Summoner;
import site.klol.batch001.summoner.repository.SummonerRepository;
import site.klol.batch001.user.entity.User;
import site.klol.batch001.user.repository.UserRepository;

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
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1112").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1113").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1114").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1115").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1116").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1117").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1118").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1119").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1120").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1121").isMainSummoner(YNFlag.Y).build(),
            Summoner.builder().user(user).summonerId("뾰로롱").summonerTag("1122").isMainSummoner(YNFlag.Y).build());

        saveSummoners = summonerRepository.saveAll(summonerList);
    }

}