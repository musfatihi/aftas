package ma.fatihii.aftas.model.compositeKeys;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import ma.fatihii.aftas.model.Competition;
import ma.fatihii.aftas.model.Member;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Embeddable
public class RankingCompositeKey implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_code")
    @NonNull
    private Competition competition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_num")
    @NonNull
    private Member member;
}
