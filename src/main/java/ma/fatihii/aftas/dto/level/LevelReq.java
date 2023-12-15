package ma.fatihii.aftas.dto.level;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LevelReq {
    @NotBlank(message = "La description de niveau ne doit pas etre vide")
    private String description;

    @Positive(message = "Les points doivent etre positifs")
    private Integer points;
}
