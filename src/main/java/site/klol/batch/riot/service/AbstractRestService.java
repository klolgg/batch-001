package site.klol.batch.riot.service;

import org.springframework.web.client.HttpClientErrorException;
import site.klol.batch.common.LoggerContext;
import site.klol.batch.riot.exception.InvalidRiotKeyException;

import java.util.function.Supplier;

public abstract class AbstractRestService implements RiotAPIService{
    protected  <T> T doExecute(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound e) {
            LoggerContext.getLogger().info("Riot API Request is not appropriate: {}", e.getMessage());
            return null;
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.Forbidden e) {
            LoggerContext.getLogger().error("Riot API Key is invalid: {}", e.getMessage());
            throw new InvalidRiotKeyException(e);
        }
    }
}
