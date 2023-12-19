package ma.fatihii.aftas.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table
public class Level {
    @Id
    private Integer code;

    private String description;

    private Integer points;

    @OneToMany(mappedBy = "level",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private List<Fish> fishList;
}
