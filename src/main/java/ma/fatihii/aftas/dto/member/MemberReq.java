package ma.fatihii.aftas.dto.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ma.fatihii.aftas.enums.IdentityDocumentType;

@Getter
@Setter
public class MemberReq {

    private Integer num;

    @NotBlank(message = "Prénom ne doit pas etre vide")
    private String name;

    @NotBlank(message = "Nom ne doit pas etre vide")
    private String familyName;

    @NotBlank(message = "Nationalité ne doit pas etre vide")
    private String nationality;

    @NotNull
    private IdentityDocumentType identityDocument;

    @NotBlank(message = "Numéro d'identité ne doit pas etre vide")
    private String identityNumber;
}
