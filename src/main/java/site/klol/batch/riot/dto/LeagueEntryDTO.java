package site.klol.batch.riot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a league entry for a summoner from the Riot Games API.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LeagueEntryDTO {
    
    /**
     * League ID for this entry.
     */
    @JsonProperty("leagueId")
    private String leagueId;

    /**
     * Player's encrypted summoner ID.
     */
    @JsonProperty("summonerId")
    private String summonerId;

    /**
     * Type of queue (e.g., "RANKED_SOLO_5x5", "RANKED_FLEX_SR").
     */
    @JsonProperty("queueType")
    private String queueType;

    /**
     * Tier of the league (e.g., "EMERALD", "PLATINUM").
     */
    @JsonProperty("tier")
    private String tier;

    /**
     * The player's division within a tier (e.g., "I", "II").
     */
    @JsonProperty("rank")
    private String rank;

    /**
     * League points in the division.
     */
    @JsonProperty("leaguePoints")
    private int leaguePoints;

    /**
     * Number of wins in this queue type.
     */
    @JsonProperty("wins")
    private int wins;

    /**
     * Number of losses in this queue type.
     */
    @JsonProperty("losses")
    private int losses;

    /**
     * Indicates if the player is on a hot streak.
     */
    @JsonProperty("hotStreak")
    private boolean hotStreak;

    /**
     * Indicates if the player is a veteran in this league.
     */
    @JsonProperty("veteran")
    private boolean veteran;

    /**
     * Indicates if the player is new to this league.
     */
    @JsonProperty("freshBlood")
    private boolean freshBlood;

    /**
     * Indicates if the player is inactive.
     */
    @JsonProperty("inactive")
    private boolean inactive;

    /**
     * Information about the player's mini series (promotion series), if any.
     */
    @JsonProperty("miniSeries")
    private MiniSeriesDTO miniSeries;
}
