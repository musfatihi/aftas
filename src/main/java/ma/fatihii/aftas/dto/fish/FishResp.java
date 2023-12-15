package ma.fatihii.aftas.dto.fish;

import lombok.Getter;
import lombok.Setter;
import ma.fatihii.aftas.dto.level.LevelResp;

@Setter
@Getter
public class FishResp {
    private String name;
    private Double averageWeight;
    private LevelResp level;
}
