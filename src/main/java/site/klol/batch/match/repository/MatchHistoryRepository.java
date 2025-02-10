package site.klol.batch.match.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.klol.batch.match.entity.MatchHistory;

import java.util.List;

public interface MatchHistoryRepository extends JpaRepository<MatchHistory, Long> {

    List<MatchHistory> findAllBySummoner_SeqNo(Long seqNo);

}
