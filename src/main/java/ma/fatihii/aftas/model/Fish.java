package ma.fatihii.aftas.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table
public class Fish {
    @Id
    private String name;

    private Double averageWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_code")
    private Level level;

    @OneToMany(mappedBy = "fish",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private List<Hunting> huntingList;
}
