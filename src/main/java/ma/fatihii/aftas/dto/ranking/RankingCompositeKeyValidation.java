package ma.fatihii.aftas.dto.ranking;

import jakarta.validation.Valid;

import lombok.Getter;
import lombok.Setter;

import ma.fatihii.aftas.dto.competition.CompetitionCode;
import ma.fatihii.aftas.dto.member.MemberNum;

@Getter
@Setter
public class RankingCompositeKeyValidation {

    @Valid
    private CompetitionCode competition;
    @Valid
    private MemberNum member;
}
