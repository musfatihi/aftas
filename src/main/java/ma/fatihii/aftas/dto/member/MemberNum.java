package ma.fatihii.aftas.dto.member;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberNum {

    @Positive(message = "Num d'adherent doit etre positif")
    private Integer num;
}
