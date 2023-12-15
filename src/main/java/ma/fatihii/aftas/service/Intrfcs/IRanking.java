package ma.fatihii.aftas.service.Intrfcs;

import ma.fatihii.aftas.dto.ranking.RankingReq;
import ma.fatihii.aftas.dto.ranking.RankingResp;
import ma.fatihii.aftas.model.compositeKeys.RankingCompositeKey;

import java.util.List;

public interface IRanking extends GenericInterface<RankingReq, RankingCompositeKey, RankingResp>{
    List<RankingResp> getLeaderBoard(String code);
}
