package site.klol.batch001.riot;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class V1RiotAPIServiceTest {

    @Test
    void getPUUID() {
        String url = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/뾰로롱/1111";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(forEntity);
    }
}