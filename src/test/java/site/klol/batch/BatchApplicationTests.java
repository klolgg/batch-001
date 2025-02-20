package site.klol.batch;

import java.util.List;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import site.klol.batch.summoner.entity.Summoner;
import site.klol.batch.summoner.repository.SummonerRepository;


@ActiveProfiles("local")
@SpringBootTest
class BatchApplicationTests {

	@Autowired
	SummonerRepository summonerRepository;
	@Test
	void contextLoads() {
		List<Summoner> all = summonerRepository.findAll();
		for (Summoner s : all) {
			System.out.println(s);
		}
	}

}
