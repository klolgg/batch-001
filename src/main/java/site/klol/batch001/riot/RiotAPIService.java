package site.klol.batch001.riot;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.service.invoker.UrlArgumentResolver;
import site.klol.batch001.riot.dto.AccountDTO;

import javax.naming.spi.Resolver;

@Component
@RequiredArgsConstructor
public class RiotAPIService {

    private final RestTemplate restTemplate;
    private final RiotURLResolver riotURLResolver;

    public Optional<String> getPuuid(String gameName, String tagLine) {
        // todo: url 생성하는 부분 + try catch로 에러 처리하는 부분
        final String fullURL = riotURLResolver.getRiotAccountURL(gameName, tagLine);

        AccountDTO body = restTemplate.getForEntity(fullURL, AccountDTO.class).getBody();
        return Optional.ofNullable(body.getPuuid());
    }


    public List<String> getMatchIdList(String puuid) {
        // todo: {puuid} 이부분 replace 하도록
        final String fullURL = null;

        String[] result = restTemplate.getForObject(
            fullURL,
            String[].class
        );

        return Arrays.asList(result);
    }

    public String getMatchDetails(String matchId) {
        // todo: {puuid} 이부분 replace 하도록
        final String fullURL = null;

        String result = restTemplate.getForObject(
            fullURL,
            String.class
        );

        return result;
    }
}
