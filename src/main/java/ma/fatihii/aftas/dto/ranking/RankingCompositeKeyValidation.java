package ma.fatihii.aftas.dto.ranking;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import ma.fatihii.aftas.dto.competition.CompetitionCode;
import ma.fatihii.aftas.dto.member.MemberNum;

@Getter
@Setter
public class RankingCompositeKeyValidation {

    @NotNull
    private CompetitionCode competition;
    @NotNull
    private MemberNum member;
}
