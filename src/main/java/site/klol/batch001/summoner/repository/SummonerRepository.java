package site.klol.batch001.summoner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.klol.batch001.summoner.entity.Summoner;

public interface SummonerRepository extends JpaRepository<Summoner, Long> {
}
