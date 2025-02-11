package site.klol.batch.riot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import site.klol.batch.common.LoggerContext;
import site.klol.batch.riot.RiotURLResolver;
import site.klol.batch.riot.dto.LeagueEntryDTO;
import site.klol.batch.riot.dto.SummonerDTO;
import site.klol.batch.riot.exception.InvalidRiotKeyException;
import site.klol.batch.riot.exception.RiotAPIException;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class V1RiotAPIServiceTest {

    @Mock
    private RiotURLResolver riotURLResolver;

    private V1RiotAPIService v1RiotAPIService;

    private String apiKey = "RGAPI-cfb32079-7805-44c0-8477-bc8fa43887d6";
    private String summonerId = "Ase8z2q1pVttycYq5soaNCeVf9oUF1wQORecntU51cOLxaxFzm00FCYWSQ";
    private String puuid = "v0z94ORaNAvVLf5DeKRnrjNn1nOJ5q3JAW9mYaBh8yufH0NNnHFGnUZ2iRsHyk8xT_AFa8iFB-dRKw";
    @BeforeEach
    void setUp() {
        v1RiotAPIService = new V1RiotAPIService(new RestTemplate(), riotURLResolver);
        Logger logger = LoggerFactory.getLogger(V1RiotAPIService.class);
        LoggerContext.setLogger(logger);
    }

    @Nested
    @DisplayName("getSummonerByPuuid 테스트")
    class GetSummonerByPuuidTest {
        
        @Test
        @DisplayName("정상적으로 소환사 정보를 반환해야 한다")
        void shouldReturnSummonerData() {
            // given
            String mockUrl = String.format("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/%s?api_key=%s",puuid,apiKey);

            SummonerDTO expectedResponse = new SummonerDTO();
            expectedResponse.setId("test-summoner-id");
            expectedResponse.setPuuid(puuid);
            expectedResponse.setName("TestSummoner");

            when(riotURLResolver.getSummonerByPuuid(puuid)).thenReturn(mockUrl);

            // when
            SummonerDTO result = v1RiotAPIService.getSummonerByPuuid(puuid);

            // then
            System.out.println(result);
            assertThat(result).isNotNull();
            assertThat(result.getPuuid()).isEqualTo(puuid);
        }

        @Test
        @DisplayName("잘못된 puuid 접근 시 ")
        void shouldThrowExceptionWhenSummonerNotFound() {
            // given
            String puuid = "non-existent-puuid";
            String mockUrl = String.format("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/non-existent-puuid?api_key=%s", apiKey);

            when(riotURLResolver.getSummonerByPuuid(puuid)).thenReturn(mockUrl);

            // when & then
            assertThatThrownBy(() -> v1RiotAPIService.getSummonerByPuuid(puuid))
                .isInstanceOf(InvalidRiotKeyException.class)
                .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST);
        }
    }

    @Nested
    @DisplayName("getLeagueEntriesInAllQueuesByAccountId 테스트")
    class GetLeagueEntriesTest {
        
        @Test
        @DisplayName("리그 정보가 없을 때 빈 Set을 반환해야 한다")
        void shouldReturnEmptySetWhenNoLeagueEntries() {
            // given
            String mockUrl = String.format("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/%s?api_key=%s", summonerId, apiKey);
            when(riotURLResolver.getLeagueEntiresInAllQueuesByAccountIdURL(summonerId)).thenReturn(mockUrl);

            // when
            Set<LeagueEntryDTO> result = v1RiotAPIService.getLeagueEntriesInAllQueuesByAccountId(summonerId);

            // then
            assertThat(result).hasSize(0);
        }
    }
}
