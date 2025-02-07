package site.klol.batch001.riot;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class RiotAPIServiceTest {

    @Test
    void getPuuid() {
        String url = "http://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/d112/12313?api_key=RGAPI-ec4f7460-fe5a-4fd6-b386-452f2913c6e2";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(forEntity);
    }
}