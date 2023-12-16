package ma.fatihii.aftas.controller;

import jakarta.validation.Valid;
import ma.fatihii.aftas.dto.ranking.RankingReq;
import ma.fatihii.aftas.dto.ranking.RankingResp;
import ma.fatihii.aftas.model.Competition;
import ma.fatihii.aftas.model.Member;
import ma.fatihii.aftas.model.compositeKeys.RankingCompositeKey;
import ma.fatihii.aftas.service.Intrfcs.IRanking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/rankings")
@CrossOrigin
public class RankingController {

    private final IRanking rankingService;

    @Autowired
    RankingController(IRanking rankingService){
        this.rankingService = rankingService;
    }

    @PostMapping
    public ResponseEntity<RankingResp> saveRanking(@RequestBody @Valid RankingReq rankingReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rankingService.save(rankingReq).get());
    }

    @GetMapping("/competition/{code}")
    public ResponseEntity<List<RankingResp>> getLeaderBoard(@PathVariable String code) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rankingService.getLeaderBoard(code));
    }

    @DeleteMapping("/{code}/{num}")
    public ResponseEntity<String> deleteRanking(@PathVariable String code,
                                                @PathVariable Integer num) {
        rankingService.delete(new RankingCompositeKey(new Competition(code),new Member(num)));
        return ResponseEntity
                .ok()
                .body("Place reservée supprimée avec succès");
    }

}
