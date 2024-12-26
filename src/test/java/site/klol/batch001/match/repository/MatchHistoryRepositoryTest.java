package site.klol.batch001.match.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import site.klol.batch001.common.enums.YNFlag;
import site.klol.batch001.match.entity.MatchHistory;
import site.klol.batch001.summoner.entity.Summoner;
import site.klol.batch001.summoner.repository.SummonerRepository;
import site.klol.batch001.user.entity.User;
import site.klol.batch001.user.repository.UserRepository;

import java.util.List;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class MatchHistoryRepositoryTest {
    @Autowired
    MatchHistoryRepository matchHistoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SummonerRepository summonerRepository;
    @Test
    @DisplayName("생성한 쿼리메서드가 올바르게 작동하는지 확인")
    void findAllBySummoner_SeqNo() {
        // given
        Summoner save = 테스트_데이터_초기화();
        // when
        List<MatchHistory> targetList = matchHistoryRepository.findAllBySummoner_SeqNo(save.getSeqNo());
        // then
        Assertions.assertEquals(2, targetList.size());
    }

    private Summoner 테스트_데이터_초기화() {
        User user = User.of("kakaoId", "nickname", YNFlag.N);
        User saveUser = userRepository.save(user);

        Summoner summoner = Summoner.builder().user(saveUser).summonerId("뾰로롱").summonerTag("1113").isMainSummoner(YNFlag.Y).build();
        Summoner saveSummoner = summonerRepository.save(summoner);

        MatchHistory match = MatchHistory.getNotUpdatedMatchHistory(saveSummoner, "matchId");
        MatchHistory match2 = MatchHistory.getNotUpdatedMatchHistory(saveSummoner, "matchId2");
        matchHistoryRepository.save(match);
        matchHistoryRepository.save(match2);

        return saveSummoner;
    }
}