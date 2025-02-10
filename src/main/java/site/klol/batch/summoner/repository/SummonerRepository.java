package site.klol.batch.summoner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.klol.batch.summoner.entity.Summoner;

public interface SummonerRepository extends JpaRepository<Summoner, Long> {
}
