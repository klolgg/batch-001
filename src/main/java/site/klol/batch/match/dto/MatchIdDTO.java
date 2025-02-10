package site.klol.batch.match.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import site.klol.batch.summoner.entity.Summoner;

@AllArgsConstructor
@Getter
public class MatchIdDTO {
    /* 소환사 **/
    private Summoner summoner;
    /* 매치 아이디 리스트 **/
    private List<String> matchIdList;
}
