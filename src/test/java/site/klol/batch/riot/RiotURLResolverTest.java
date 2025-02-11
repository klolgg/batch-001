package site.klol.batch.riot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.klol.batch.common.LoggerContext;
import site.klol.batch.riot.config.RiotProperties;
import site.klol.batch.riot.config.RiotProperties.ApiProperties;
import site.klol.batch.riot.config.RiotProperties.AccountV1Properties;
import site.klol.batch.riot.config.RiotProperties.MatchV5Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RiotURLResolverTest {

    @Mock
    private RiotProperties riotProperties;
    @Mock
    private RiotAPIKeyManager riotAPIKeyManager;
    @Mock
    private ApiProperties apiProperties;
    @Mock
    private AccountV1Properties accountV1Properties;
    @Mock
    private MatchV5Properties matchV5Properties;

    private RiotURLResolver riotURLResolver;

    @BeforeEach
    void setUp() {
        Logger logger = LoggerFactory.getLogger(RiotURLResolver.class);
        LoggerContext.setLogger(logger);
        riotURLResolver = new RiotURLResolver(riotProperties, riotAPIKeyManager);
    }

    // region getRiotAccountURL
    @Test
    @DisplayName("getRiotAccountURL should return correct URL with gameName and tagLine")
    void getRiotAccountURL_ShouldReturnCorrectURL() {
        // given
        String gameName = "testUser";
        String tagLine = "KR1";
        String accountByRiotIdPath = "/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}";
        
        when(riotProperties.getDomain()).thenReturn("api.riotgames.com");
        when(riotProperties.getApi()).thenReturn(apiProperties);
        when(apiProperties.getAccountV1()).thenReturn(accountV1Properties);
        when(accountV1Properties.getAccountByRiotId()).thenReturn(accountByRiotIdPath);
        when(accountV1Properties.getRegion()).thenReturn("asia");
        when(riotAPIKeyManager.getApiKey()).thenReturn("test-api-key");

        // when
        String result = riotURLResolver.getRiotAccountURL(gameName, tagLine);

        // then
        String expectedUrl = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/testUser/KR1?api_key=test-api-key";
        assertThat(result).isEqualTo(expectedUrl);
    }
    // endregion

    // region getMatchListURL
    @Test
    @DisplayName("getMatchListURL should return correct URL with puuid")
    void getMatchListURL_ShouldReturnCorrectURL() {
        // given
        String puuid = "test-puuid";
        String matchListPath = "/lol/match/v5/matches/by-puuid/{puuid}/ids";
        
        when(riotProperties.getDomain()).thenReturn("api.riotgames.com");
        when(riotProperties.getApi()).thenReturn(apiProperties);
        when(apiProperties.getMatchV5()).thenReturn(matchV5Properties);
        when(matchV5Properties.getMatchListByPuuid()).thenReturn(matchListPath);
        when(matchV5Properties.getRegion()).thenReturn("asia");
        when(riotAPIKeyManager.getApiKey()).thenReturn("test-api-key");

        // when
        String result = riotURLResolver.getMatchListURL(puuid);

        // then
        String expectedUrl = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/test-puuid/ids?api_key=test-api-key";
        assertThat(result).isEqualTo(expectedUrl);
    }
    // endregion

    // region getMatchDetailURL
    @Test
    @DisplayName("getMatchDetailURL should return correct URL with matchId")
    void getMatchDetailURL_ShouldReturnCorrectURL() {
        // given
        String matchId = "KR_12345";
        String matchDetailPath = "/lol/match/v5/matches/{matchId}";
        
        when(riotProperties.getDomain()).thenReturn("api.riotgames.com");
        when(riotProperties.getApi()).thenReturn(apiProperties);
        when(apiProperties.getMatchV5()).thenReturn(matchV5Properties);
        when(matchV5Properties.getMatchDetailByMatchId()).thenReturn(matchDetailPath);
        when(matchV5Properties.getRegion()).thenReturn("asia");
        when(riotAPIKeyManager.getApiKey()).thenReturn("test-api-key");

        // when
        String result = riotURLResolver.getMatchDetailURL(matchId);

        // then
        String expectedUrl = "https://asia.api.riotgames.com/lol/match/v5/matches/KR_12345?api_key=test-api-key";
        assertThat(result).isEqualTo(expectedUrl);
    }
    // endregion
}
