package ma.fatihii.aftas.dto.fish;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import ma.fatihii.aftas.dto.level.LevelCode;

@Getter
@Setter
public class FishReq {

    @NotBlank(message = "Nom de la poisson ne peut pas etre vide")
    private String name;

    @Positive(message = "Pois moyen doit etre positif")
    private Double averageWeight;

    @Valid
    private LevelCode level;
}
