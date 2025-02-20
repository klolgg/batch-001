package site.klol.batch.riot;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import site.klol.batch.riot.dto.AccountDTO;

class V1RiotAPIServiceTest {

    @Test
    void getPUUID() {
        String template = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/{id}/KR1?api_key=RGAPI-ec4f7460-fe5a-4fd6-b386-452f2913c6e2";

        List<String> list = Arrays.asList("Destiny", "이차가식기전에", "칼과 창방패", "도천지");

        for (String id : list) {
            String url = template.replace("{id}", id);
            System.out.println(url);
            RestTemplate restTemplate = new RestTemplate();
            AccountDTO body = restTemplate.getForEntity(url, AccountDTO.class).getBody();
            System.out.println(body);
        }
    }
}