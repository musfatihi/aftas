package ma.fatihii.aftas.repository;

import ma.fatihii.aftas.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition,String> {
}
