package ma.fatihii.aftas.dto.ranking;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankingResp {
    private RankingCompositeKeyResp rankingCompositeKey;
    private Integer rank;
    private Integer score;
}
