package site.klol.batch.riot.service;

import java.util.Optional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import site.klol.batch.common.LoggerContext;
import site.klol.batch.riot.exception.InvalidRiotKeyException;

import java.util.function.Supplier;
import site.klol.batch.riot.exception.RiotAPIException;

public abstract class AbstractRestService implements RiotAPIService{
    protected RestTemplate restTemplate;
    protected AbstractRestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    protected  <T> Optional<T> executeAndGetBody(String url, Class<T> responseType) {
        try {
            LoggerContext.getLogger().debug("Making request to: {}", url);
            ResponseEntity<T> response = restTemplate.getForEntity(url, responseType);
            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException e) {
            handleClientError(e, url);
            return Optional.empty();
        } catch (HttpServerErrorException e) {
            handleServerError(e, url);
            return Optional.empty();
        } catch (Exception e) {
            handleUnexpectedError(e, url);
            return Optional.empty();
        }
    }

    protected  <T> Optional<T> executeAndGetBody(String url, ParameterizedTypeReference<T> responseType) {
        try {
            LoggerContext.getLogger().debug("Making request to: {}", url);
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException e) {
            handleClientError(e, url);
            return Optional.empty();
        } catch (HttpServerErrorException e) {
            handleServerError(e, url);
            return Optional.empty();
        } catch (Exception e) {
            handleUnexpectedError(e, url);
            return Optional.empty();
        }
    }

    private void handleClientError(HttpClientErrorException e, String url) {
        LoggerContext.getLogger().error("Client error for URL {}: {} - {}", url, e.getStatusCode(), e.getResponseBodyAsString());
        if (e.getStatusCode() == HttpStatus.NOT_FOUND ) {
            throw new RiotAPIException("Resource not found", HttpStatus.NOT_FOUND, e);
        } else if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
            throw new RiotAPIException("Rate limit exceeded", HttpStatus.TOO_MANY_REQUESTS, e);
        } else if ( e.getStatusCode() == HttpStatus.UNAUTHORIZED || e.getStatusCode() == HttpStatus.FORBIDDEN) {
            LoggerContext.getLogger().error("Riot API Key is invalid: {}", e.getMessage());
            throw new InvalidRiotKeyException(e);
        } else if ( e.getStatusCode() == HttpStatus.BAD_REQUEST) {
            LoggerContext.getLogger().error("Riot API Request is not appropriate: {}", e.getMessage());
            throw new InvalidRiotKeyException(e);
        }
        throw new RiotAPIException("Client error: " + e.getMessage(), (HttpStatus) e.getStatusCode(), e);
    }

    private void handleServerError(HttpServerErrorException e, String url) {
        LoggerContext.getLogger().error("Server error for URL {}: {} - {}", url, e.getStatusCode(), e.getResponseBodyAsString());
        throw new RiotAPIException("Riot API server error", (HttpStatus) e.getStatusCode(), e);
    }

    private void handleUnexpectedError(Exception e, String url) {
        LoggerContext.getLogger().error("Unexpected error for URL {}: {}", url, e.getMessage(), e);
        throw new RiotAPIException("Unexpected error while calling Riot API", HttpStatus.INTERNAL_SERVER_ERROR, e);
    }
}
