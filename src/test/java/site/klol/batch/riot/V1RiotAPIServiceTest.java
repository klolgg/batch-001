package site.klol.batch.riot;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class V1RiotAPIServiceTest {

    @Test
    void getPUUID() {
//        String url = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/뾰로롱/1111?api_key=RGAPI-ec4f7460-fe5a-4fd6-b386-452f2913c6e2";
        String url = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/NaNiNo/KR1?api_key=RGAPI-43dab18b-fdfb-4621-933b-3cef58fd2fd9";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(forEntity);
    }

    @Test
    void getAccountByPuuid() {
        String url = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-puuid/_PT8-VdDjDwBeoLpGPstPjxyLSnp_ra6IVPz-XpnyEr5OKutq-siMcML7sh6s1_2SKD09Afy2pwkQw?api_key=RGAPI-ec4f7460-fe5a-4fd6-b386-452f2913c6e2";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(forEntity);
    }
}