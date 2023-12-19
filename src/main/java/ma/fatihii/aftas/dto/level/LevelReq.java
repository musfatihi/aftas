package ma.fatihii.aftas.dto.level;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LevelReq {

    @Min(value = 1,message = "Code Niveau doit etre superieur ou égal à 1")
    private Integer code;

    @NotBlank(message = "La description de niveau ne doit pas etre vide")
    private String description;

    @Positive(message = "Les points doivent etre positifs")
    private Integer points;
}
