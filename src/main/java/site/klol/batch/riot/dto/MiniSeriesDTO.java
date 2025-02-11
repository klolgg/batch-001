package site.klol.batch.riot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a mini series (promotion series) information from the Riot Games API.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MiniSeriesDTO {
    
    /**
     * Number of losses in the mini series.
     */
    @JsonProperty("losses")
    private int losses;

    /**
     * String showing the progress of the mini series (e.g., "WLL" for 1 win, 2 losses).
     */
    @JsonProperty("progress")
    private String progress;

    /**
     * Number of wins needed to win the mini series.
     */
    @JsonProperty("target")
    private int target;

    /**
     * Number of wins in the mini series.
     */
    @JsonProperty("wins")
    private int wins;
}
