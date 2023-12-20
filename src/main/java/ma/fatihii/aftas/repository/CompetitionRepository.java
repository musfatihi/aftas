package ma.fatihii.aftas.repository;

import ma.fatihii.aftas.model.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition,String> {
    Optional<Competition> findByDate(LocalDate date);

    @Query("SELECT e FROM Competition e WHERE e.date>local date or (e.date=local date and e.startTime>local time)")
    Page<Competition> findByStatusPending(Pageable pageable);

    @Query("SELECT e FROM Competition e WHERE (e.date=local date and e.startTime<local time and e.endTime> local time)")
    Page<Competition> findByStatusCurrent(Pageable pageable);

    @Query("SELECT e FROM Competition e WHERE e.date>local date or (e.date=local date and e.endTime<local time)")
    Page<Competition> findByStatusClosed(Pageable pageable);


}
