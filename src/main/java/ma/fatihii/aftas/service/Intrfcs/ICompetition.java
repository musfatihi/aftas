package ma.fatihii.aftas.service.Intrfcs;

import ma.fatihii.aftas.dto.competition.CompetitionReq;
import ma.fatihii.aftas.dto.competition.CompetitionResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICompetition extends GenericInterface<CompetitionReq, String, CompetitionResp>{
    Page<CompetitionResp> findAllCompetitions(String status, Pageable pageable);

    boolean closeCompetition(String code);
}
