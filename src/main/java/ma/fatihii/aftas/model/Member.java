package ma.fatihii.aftas.model;

import jakarta.persistence.*;
import lombok.*;
import ma.fatihii.aftas.enums.IdentityDocumentType;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table
public class Member {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num;

    private String name;

    private String familyName;

    private LocalDate accessDate=LocalDate.now();

    private String nationality;

    private IdentityDocumentType identityDocument;

    private String identityNumber;

    @OneToMany(mappedBy = "member",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private List<Hunting> huntingList;

    @OneToMany(mappedBy = "rankingCompositeKey.member",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private List<Ranking> rankingList;
}
