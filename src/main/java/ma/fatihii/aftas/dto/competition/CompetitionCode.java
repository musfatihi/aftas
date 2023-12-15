package ma.fatihii.aftas.dto.competition;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompetitionCode {

    @NotBlank(message = "Code competition ne doit pas etre vide")
    private String code;
}
