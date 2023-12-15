package ma.fatihii.aftas.repository;

import ma.fatihii.aftas.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level,Integer> {
}
