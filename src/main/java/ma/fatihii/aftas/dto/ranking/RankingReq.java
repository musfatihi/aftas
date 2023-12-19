package ma.fatihii.aftas.dto.ranking;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankingReq {

    @Valid
    private RankingCompositeKeyValidation rankingCompositeKey;
}
