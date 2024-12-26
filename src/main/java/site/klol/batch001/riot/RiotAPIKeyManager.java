package site.klol.batch001.riot;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class RiotAPIKeyManager {
    private static final String API_KEY_ENV_NAME = "RIOT_API_KEY";

    private String apiKey;
    private LocalDate lastFetchDay;

    public String getApiKey() {
        if(isExpired()){
            refreshApiKey();
        }

        return apiKey;
    }

    private void refreshApiKey() {
        if(!isExpired()) return;

        final String newApiKey = Optional.ofNullable(System.getenv(API_KEY_ENV_NAME))
            .orElseThrow(() -> new RuntimeException("Missing environment variable " + API_KEY_ENV_NAME));

        this.apiKey = newApiKey;
    }

    private boolean isExpired() {
        if (lastFetchDay == null) return true;

        return LocalDate.now().isAfter(lastFetchDay);
    }
}
