package site.klol.batch.riot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
@Slf4j
public class RiotURLResolver {
    @Value("${riot.domain}")
    private String domain;
    @Value("${riot.api.account-v1.account-by-riot-id}")
    private String riotAccountURL;
    @Value("${riot.api.match-v5.match-list-by-puuid}")
    private String matchListURL;
    @Value("${riot.api.match-v5.match-detail-by-match-id}")
    private String matchDetailURL;
    private static final String API_KEY_NAME = "api_key";

    private final RiotAPIKeyManager riotAPIKeyManager;

    public String getRiotAccountURL(String gameName, String tagLine) {
        return getURL(()-> riotAccountURL.replace("{gameName}", gameName).replace("{tagLine}", tagLine));
    }

    public String getMatchListURL(String puuid) {
        return getURL(() -> matchListURL.replace("{puuid}", puuid));
    }

    public String getMatchDetailURL(String matchId){
        return getURL(() -> matchDetailURL.replace("{matchId}", matchId));
    }
    private String getURL(Supplier<String> supplier) {
        final String url = supplier.get();
        log.info("URL: {}", url);

        return domain + addAPIKey(url);
    }

    private String addAPIKey(String url) {
        return url + "?" + API_KEY_NAME + "=" + riotAPIKeyManager.getApiKey();
    }

}
