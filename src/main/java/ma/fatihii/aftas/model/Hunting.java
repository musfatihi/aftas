package ma.fatihii.aftas.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table
public class Hunting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer numberOfFish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_code")
    private Competition competition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_num")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fish_name")
    private Fish fish;
}
