package site.klol.batch.riot.service;

import java.util.List;

public interface RiotAPIService {
    /** 소환사 이름, 태그로 riot puuid 가져오는 API */
    String getPUUID(String gameName, String tagLine);
    /** puuid로 최근 N게임 matchId 가져오는 API */
    // todo: start, limit 파라미터 추가해서 match id 동적으로 get하게 변경 필요
    List<String> getMatchIdList(String puuid);
    /** matchId에 맞는 매치 정보 가져오는 API */
    // json이 복잡해서 Object로 가져와서 그대로 moongdb에 저장
    Object getMatchDetails(String matchId);
}
