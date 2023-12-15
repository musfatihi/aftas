package ma.fatihii.aftas.repository;

import ma.fatihii.aftas.model.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishRepository extends JpaRepository<Fish,String> {
}
