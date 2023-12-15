package ma.fatihii.aftas.dto.ranking;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankingReq {

    @NotNull
    private RankingCompositeKeyValidation rankingCompositeKey;
}
