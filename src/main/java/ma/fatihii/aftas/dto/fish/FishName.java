package ma.fatihii.aftas.dto.fish;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FishName {

    @NotBlank(message = "Nom de la poisson ne peut pas etre vide")
    private String name;
}
