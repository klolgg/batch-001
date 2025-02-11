package site.klol.batch.riot;

import static site.klol.batch.riot.RiotURLResolver.API_KEY_NAME;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.klol.batch.common.LoggerContext;
import site.klol.batch.riot.config.RiotProperties;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RiotURLResolver {
    public static final String API_KEY_NAME = "api_key";

    private final RiotProperties riotProperties;
    private final RiotAPIKeyManager riotAPIKeyManager;

    public String getRiotAccountURL(String gameName, String tagLine) {
        return new RiotURLBuilder(riotProperties, riotAPIKeyManager)
            .withRegion(riotProperties.getApi().getAccountV1().getRegion())
            .withEndpoint(riotProperties.getApi().getAccountV1().getAccountByRiotId())
            .withPathVariable("gameName", gameName)
            .withPathVariable("tagLine", tagLine)
            .build();
    }
    public String getMatchListURL(String puuid) {
        return new RiotURLBuilder(riotProperties, riotAPIKeyManager)
            .withRegion(riotProperties.getApi().getMatchV5().getRegion())
            .withEndpoint(riotProperties.getApi().getMatchV5().getMatchListByPuuid())
            .withPathVariable("puuid", puuid)
            .build();
    }

    public String getMatchDetailURL(String matchId) {
        return new RiotURLBuilder(riotProperties, riotAPIKeyManager)
            .withRegion(riotProperties.getApi().getMatchV5().getRegion())
            .withEndpoint(riotProperties.getApi().getMatchV5().getMatchDetailByMatchId())
            .withPathVariable("matchId", matchId)
            .build();
    }
}

class RiotURLBuilder {
    private final RiotProperties riotProperties;
    private final RiotAPIKeyManager riotAPIKeyManager;
    private String region;
    private String endpoint;
    private Map<String, String> pathVariables;

    public RiotURLBuilder(RiotProperties riotProperties, RiotAPIKeyManager riotAPIKeyManager) {
        this.riotProperties = riotProperties;
        this.riotAPIKeyManager = riotAPIKeyManager;
        this.pathVariables = new HashMap<>();
    }

    public RiotURLBuilder withRegion(String region) {
        this.region = region;
        return this;
    }

    public RiotURLBuilder withEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public RiotURLBuilder withPathVariable(String name, String value) {
        this.pathVariables.put(name, value);
        return this;
    }

    public String build() {
        String url = endpoint;
        for (Map.Entry<String, String> entry : pathVariables.entrySet()) {
            url = url.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        LoggerContext.getLogger().info("URL: {}", url);

        return "https://" + region + "." + riotProperties.getDomain() + url + "?" + API_KEY_NAME + "=" + riotAPIKeyManager.getApiKey();
    }
}
