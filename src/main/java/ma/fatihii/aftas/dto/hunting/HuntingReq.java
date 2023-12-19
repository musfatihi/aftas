package ma.fatihii.aftas.dto.hunting;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import ma.fatihii.aftas.dto.competition.CompetitionCode;
import ma.fatihii.aftas.dto.fish.FishName;
import ma.fatihii.aftas.dto.member.MemberNum;

@Getter
@Setter
public class HuntingReq {

    @Positive(message = "Nombre de poissons doit etre positif")
    private Integer numberOfFish;
    @Valid
    private CompetitionCode competition;
    @Valid
    private MemberNum member;
    @Valid
    private FishName fish;
}
