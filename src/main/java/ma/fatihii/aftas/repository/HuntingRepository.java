package ma.fatihii.aftas.repository;

import ma.fatihii.aftas.model.Competition;
import ma.fatihii.aftas.model.Fish;
import ma.fatihii.aftas.model.Hunting;
import ma.fatihii.aftas.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HuntingRepository extends JpaRepository<Hunting,Integer> {

    Optional<Hunting> findByCompetitionAndMemberAndFish(
            Competition competition,
            Member member,
            Fish fish
    );

    List<Hunting> findByCompetitionAndMember(
            Competition competition,
            Member member
    );

}
