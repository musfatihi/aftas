package ma.fatihii.aftas.dto.competition;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class CompetitionReq {
    @Future
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @NotNull
    //@JsonFormat(pattern = "hh:mm:ss")
    private LocalTime startTime;
    @NotNull
    //@JsonFormat(pattern = "hh:mm:ss")
    private LocalTime endTime;
    @Positive(message = "Nombre de participants doit etre positif")
    private Integer numberOfParticipants;
    @NotBlank(message = "Place ne peut pas etre vide")
    private String location;
    @Positive(message = "Montant doit etre positif")
    private Double amount;
}
