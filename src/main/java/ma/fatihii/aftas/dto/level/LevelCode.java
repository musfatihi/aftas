package ma.fatihii.aftas.dto.level;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LevelCode {
    @Positive(message = "Code Niveau doit etre positif")
    private Integer code;
}
