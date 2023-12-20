package ma.fatihii.aftas.dto.competition;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class CompetitionReq {
    private String code;
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
    @Min(message = "Nombre de participants doit etre au moins 2", value = 2L)
    private Integer numberOfParticipants;
    @NotBlank(message = "Place ne peut pas etre vide")
    @Pattern(regexp = ".{3,}", message = "Place doit contenir au moins trois caracteres")
    private String location;
    @Positive(message = "Montant doit etre positif")
    private Double amount;
}
