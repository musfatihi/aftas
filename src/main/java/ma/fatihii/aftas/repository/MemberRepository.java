package ma.fatihii.aftas.repository;

import ma.fatihii.aftas.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer> {
    List<Member> findByName(String name);

    List<Member> findByFamilyName(String familyName);
}
