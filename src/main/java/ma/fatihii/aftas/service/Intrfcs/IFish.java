package ma.fatihii.aftas.service.Intrfcs;

import ma.fatihii.aftas.dto.fish.FishReq;
import ma.fatihii.aftas.dto.fish.FishResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFish extends GenericInterface<FishReq,String, FishResp>{
    Page<FishResp> findAllFish(Pageable pageable);
}
