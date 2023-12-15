package ma.fatihii.aftas.dto.competition;

import lombok.Getter;
import lombok.Setter;
import ma.fatihii.aftas.dto.ranking.RankingResp;
import ma.fatihii.aftas.model.Ranking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CompetitionResp {
    private String code;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer numberOfParticipants;
    private String location;
    private Double amount;
    private List<RankingResp> rankingList;
}
