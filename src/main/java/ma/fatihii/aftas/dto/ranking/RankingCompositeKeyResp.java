package ma.fatihii.aftas.dto.ranking;

import lombok.Getter;
import lombok.Setter;
import ma.fatihii.aftas.dto.competition.CompetitionResp;
import ma.fatihii.aftas.dto.member.MemberResp;


@Getter
@Setter
public class RankingCompositeKeyResp {
    //private CompetitionResp competition;
    private MemberResp member;
}
