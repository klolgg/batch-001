package site.klol.batch.riot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a summoner from the Riot Games API.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SummonerDTO {
    
    /**
     * Encrypted account ID. Max length 56 characters.
     */
    @JsonProperty("accountId")
    private String accountId;

    /**
     * ID of the summoner icon associated with the summoner.
     */
    @JsonProperty("profileIconId")
    private int profileIconId;

    /**
     * Date summoner was last modified specified as epoch milliseconds.
     * Updates on: summoner name change, summoner level change, or profile icon change.
     */
    @JsonProperty("revisionDate")
    private long revisionDate;

    /**
     * Encrypted summoner ID. Max length 63 characters.
     */
    @JsonProperty("id")
    private String id;

    /**
     * Encrypted PUUID. Exact length of 78 characters.
     */
    @JsonProperty("puuid")
    private String puuid;

    /**
     * Summoner name.
     */
    @JsonProperty("name")
    private String name;

    /**
     * Summoner level associated with the summoner.
     */
    @JsonProperty("summonerLevel")
    private long summonerLevel;
}
