package ma.fatihii.aftas.repository;

import ma.fatihii.aftas.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelRepository extends JpaRepository<Level,Integer> {
    List<Level> findAllByOrderByCode();
}
