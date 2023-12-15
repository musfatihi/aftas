package ma.fatihii.aftas.repository;

import ma.fatihii.aftas.model.Competition;
import ma.fatihii.aftas.model.Ranking;
import ma.fatihii.aftas.model.compositeKeys.RankingCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, RankingCompositeKey> {

    Integer countByRankingCompositeKeyCompetition(Competition competition);

    List<Ranking> findByRankingCompositeKeyCompetition(Competition competition);

}
