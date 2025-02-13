package site.klol.batch;

import io.micrometer.common.util.StringUtils;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.test.context.ActiveProfiles;
import site.klol.batch.common.LoggerContext;
import site.klol.batch.common.enums.YNFlag;
import site.klol.batch.riot.service.V1RiotAPIService;
import site.klol.batch.summoner.entity.Summoner;
import site.klol.batch.summoner.repository.SummonerRepository;
import site.klol.batch.user.entity.User;
import site.klol.batch.user.repository.UserRepository;


@SpringBootTest
@ActiveProfiles("local")
class BatchApplicationTests {

	@Autowired
	UserRepository userRepository;
	@Autowired
	SummonerRepository summonerRepository;
	@Autowired
	V1RiotAPIService riotAPIService;
	@Test
	void contextLoads() {
		LoggerContext.setLogger(LoggerFactory.getLogger(BatchApplicationTests.class));

		User user = userRepository.save(User.of("testKakaoId3", "testNickname1", YNFlag.Y));

		List<Summoner> all = summonerRepository.findAll();

		for (Summoner s : all) {
			if(StringUtils.isBlank(s.getPuuid())){
				String puuid = riotAPIService.getPUUID(s.getSummonerId(), s.getSummonerTag());
				s.setPuuid(puuid);
			}
			summonerRepository.save(s);
		}

//
//		Summoner summoner = Summoner.builder()
//			.user(user)
//			.puuid(puuid)
//			.summonerId(summonerId)
//			.summonerTag(summonerTag)
//			.isMainSummoner(YNFlag.Y)
//			.build();

//		Summoner summoner3 = Summoner.builder()
//			.user(user)
//			.puuid(puuid)
//			.summonerId(summonerId3)
//			.summonerTag(summonerTag3)
//			.isMainSummoner(YNFlag.Y)
//			.build();

//		summonerRepository.save(summoner);


	}

}
