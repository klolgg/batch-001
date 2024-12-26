package site.klol.batch001.riot;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class RiotURLResolver {
    @Value("${riot.domain}")
    private String domain;
    @Value("${riot.api.account-v1.account-by-riot-id}")
    private String riotAccountUrl;
    @Value("${riot.api.match-v5.match-list-by-puuid}")
    private String matchListUrl;
    private static final String API_KEY_NAME = "api_key";

    private final RiotAPIKeyManager riotAPIKeyManager;

    public String getRiotAccountURL(String gameName, String tagLine) {
        // todo: 한국어는 인코딩이 필요하다고 함. 그러면 영어는 상관없음?
        return getURL(()-> riotAccountUrl.replace("{gameName}", gameName).replace("{tagLine}", tagLine));
    }

    public String getMatchListURL(String puuid) {
        return getURL(() -> matchListUrl.replace("{puuid}", puuid));
    }

    private String getURL(Supplier<String> supplier) {
        final String url = supplier.get();

        return addAPIKey(url);
    }

    private String addAPIKey(String url) {
        return url + "?" + API_KEY_NAME + "=" + riotAPIKeyManager.getApiKey();
    }

}
