package ma.fatihii.aftas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CompetitionController {

    private final ICompetition competitionService;

    @GetMapping("/{code}")
    public ResponseEntity<CompetitionResp> getCompetition(@PathVariable String code) {
        return ResponseEntity
                .ok()
                .body(competitionService.findById(code).get());
    }
    

    @GetMapping("/close/{code}")
    public ResponseEntity<String> closeCompetition(@PathVariable String code) {
        this.competitionService.closeCompetition(code);
        return ResponseEntity
                .ok()
                .body("Competittion fermée");
    }

    @GetMapping
    public ResponseEntity<Page<CompetitionResp>> getCompetitions(
            @RequestParam(required = false) String status,
            Pageable pageable) {
        return ResponseEntity
                .ok()
                .body(competitionService.findAllCompetitions(status,pageable));
    }


    @PostMapping
    public ResponseEntity<CompetitionResp> saveCompetition(@RequestBody @Valid CompetitionReq competitionReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(competitionService.save(competitionReq).get());
    }


    @PutMapping
    public ResponseEntity<CompetitionResp> updateCompetition(@RequestBody @Valid CompetitionReq competitionReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(competitionService.update(competitionReq).get());
    }


    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteCompetition(@PathVariable String code) {
        competitionService.delete(code);
        return ResponseEntity
                .ok()
                .body("Compétition supprimée avec succès");
    }

}
