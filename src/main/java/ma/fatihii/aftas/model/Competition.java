package ma.fatihii.aftas.model;

import jakarta.persistence.*;
import lombok.*;
import ma.fatihii.aftas.helpers.Utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table
public class Competition {
    @Id
    @NonNull
    private String code;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer numberOfParticipants;

    private String location;

    private Double amount;

    @OneToMany(mappedBy = "competition",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private List<Hunting> huntingList;

    @OneToMany(mappedBy = "rankingCompositeKey.competition",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private List<Ranking> rankingList=new ArrayList<>();

    @PrePersist
    private void generateId() {
        code = location.substring(0,3)+"-"+Utils.formatDate(date);
    }

}
