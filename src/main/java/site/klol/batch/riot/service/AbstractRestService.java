package site.klol.batch.riot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;
import site.klol.batch.riot.exception.InvalidRiotKeyException;

import java.util.function.Supplier;

@Slf4j
public abstract class AbstractRestService implements RiotAPIService{
    protected  <T> T doExecute(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound e) {
            log.info("Riot API Request is not appropriate: {}", e.getMessage());
            return null;
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.Forbidden e) {
            log.error("Riot API Key is invalid: {}", e.getMessage());
            throw new InvalidRiotKeyException(e);
        }
    }
}
