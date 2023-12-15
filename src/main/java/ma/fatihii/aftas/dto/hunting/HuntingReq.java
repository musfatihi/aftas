package ma.fatihii.aftas.dto.hunting;


import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private CompetitionCode competition;
    @NotNull
    private MemberNum member;
    @NotNull
    private FishName fish;
}
