package site.klol.batch.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    // ToDo: connection pool 설정하기.
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
