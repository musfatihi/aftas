package ma.fatihii.aftas.dto.member;

import lombok.Getter;
import lombok.Setter;
import ma.fatihii.aftas.enums.IdentityDocumentType;

import java.time.LocalDate;

@Getter
@Setter
public class MemberResp {
    private Integer num;
    private String name;
    private String familyName;
    private LocalDate accessDate;
    private String nationality;
    private IdentityDocumentType identityDocument;
    private String identityNumber;
}
