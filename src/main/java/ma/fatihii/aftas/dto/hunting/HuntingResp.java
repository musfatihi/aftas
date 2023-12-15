package ma.fatihii.aftas.dto.hunting;

import lombok.Getter;
import lombok.Setter;
import ma.fatihii.aftas.dto.competition.CompetitionResp;
import ma.fatihii.aftas.dto.fish.FishResp;
import ma.fatihii.aftas.dto.member.MemberResp;


@Getter
@Setter
public class HuntingResp {

    private Integer id;
    private Integer numberOfFish;
    private CompetitionResp competition;
    private MemberResp member;
    private FishResp fish;
}
