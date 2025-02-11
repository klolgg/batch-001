package site.klol.batch.riot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "riot")
@Getter
@Setter
public class RiotProperties {
    private String domain;
    @NestedConfigurationProperty
    private ApiProperties api;
    @Getter
    @Setter
    public static class ApiProperties {
        private AccountV1Properties accountV1;
        private MatchV5Properties matchV5;
    }

    @Getter
    @Setter
    public static class AccountV1Properties {
        private String region;
        private String accountByRiotId;
    }

    @Getter
    @Setter
    public static class MatchV5Properties {
        private String region;
        private String matchListByPuuid;
        private String matchDetailByMatchId;
    }
}
