package site.klol.batch.batch002;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import site.klol.batch.summoner.repository.SummonerRepository;

@SpringBatchTest
@SpringBootTest
@ActiveProfiles("test")
class Batch002JobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private SummonerRepository summonerRepository;
}