package site.klol.batch001.riot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class RiotAPIServiceTest {

    @Test
    void getPuuid() {
        String url = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/d112/12313?api_key=RGAPI-e5b6770d-b9a8-41d4-a7a6-a0b1d6213ca1";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(forEntity);
    }
}