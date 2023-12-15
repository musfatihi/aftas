package ma.fatihii.aftas.controller;

import jakarta.validation.Valid;
import ma.fatihii.aftas.dto.competition.CompetitionReq;
import ma.fatihii.aftas.dto.competition.CompetitionResp;
import ma.fatihii.aftas.service.Intrfcs.ICompetition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/v1/competitions")
@CrossOrigin
public class CompetitionController {

    private final ICompetition competitionService;

    @Autowired
    CompetitionController(ICompetition competitionService){
        this.competitionService = competitionService;
    }

    @GetMapping("/{code}")
    public ResponseEntity<CompetitionResp> getCompetition(@PathVariable String code) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(competitionService.findById(code).get());
    }

    @GetMapping("/close/{code}")
    public ResponseEntity<String> closeCompetition(@PathVariable String code) {
        this.competitionService.closeCompetition(code);
        return ResponseEntity.status(HttpStatus.OK).body("Competittion fermée");
    }

    @GetMapping
    public ResponseEntity<Page<CompetitionResp>> getCompetitions(
            @RequestParam(required = false) String status,
            Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(competitionService.findAllCompetitions(status,pageable));
    }


    @PostMapping
    public ResponseEntity<CompetitionResp> saveCompetition(@RequestBody @Valid CompetitionReq competitionReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(competitionService.save(competitionReq).get());
    }

}
