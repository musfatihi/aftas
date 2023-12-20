package ma.fatihii.aftas.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import ma.fatihii.aftas.model.compositeKeys.RankingCompositeKey;

@Getter
@Setter
@Table
@Entity
public class Ranking implements Comparable<Ranking>{
    @EmbeddedId
    private RankingCompositeKey rankingCompositeKey;

    private Integer rank;

    private Integer score;

    @Override
    public int compareTo(Ranking that) {
        return that.getScore().compareTo(this.getScore());
    }

}
