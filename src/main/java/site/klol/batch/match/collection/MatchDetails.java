package site.klol.batch.match.collection;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "match_details")
public class MatchDetails {

    private Long id;
    private MetaData metaData;
    private Info info;

    @Getter
    @Setter
    static class MetaData {
        private String matchId;
    }

    @Getter
    @Setter
    static class Info {
        // todo: 게임 중간 데이터도 저장되나?? 값이 gameComplete인걸 보면 중간에도 검색될거 같다.
        private String endOfGameResult;
        private String gameMode;
        private String gameType;
    }
}
