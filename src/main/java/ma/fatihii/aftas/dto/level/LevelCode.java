package ma.fatihii.aftas.dto.level;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LevelCode {
    @Min(message = "Code Niveau doit supérieur à 1",value = 1)
    private Integer code;
}
