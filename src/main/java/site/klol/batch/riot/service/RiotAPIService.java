package site.klol.batch.riot.service;

import java.util.List;
import java.util.Set;
import site.klol.batch.riot.dto.AccountDTO;
import site.klol.batch.riot.dto.LeagueEntryDTO;
import site.klol.batch.riot.dto.SummonerDTO;

public interface RiotAPIService {

    /**
     * 소환사 이름, 태그로 riot puuid 가져오는 API
     * @param gameName
     * @param tagLine
     * @return
     */
    String getPUUID(String gameName, String tagLine);
    /**
     * puuid로 최근 N게임 matchId 가져오는 API
     * @param puuid
     * @return
     */
    // todo: start, limit 파라미터 추가해서 match id 동적으로 get하게 변경 필요
    List<String> getMatchIdList(String puuid);
    /**
     * matchId에 맞는 매치 정보 가져오는 API
     * @param matchId
     * @return
     */
    Object getMatchDetails(String matchId);

    /**
     * puuid로 account 정보 가져오는 API
     * @param puuid
     * @return
     */
    AccountDTO getAccountByPuuid(String puuid);

    /**
     * puuid로 summoner 정보 가져오는 API
     * @param puuid
     * @return
     */
    SummonerDTO getSummonerByPuuid(String puuid);

    /**
     * accountId로 league 정보 가져오는 API
     * @param accountId
     * @return
     */
    Set<LeagueEntryDTO> getLeagueEntriesInAllQueuesByAccountId(String accountId);
}
