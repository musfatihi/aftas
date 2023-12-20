package ma.fatihii.aftas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.fatihii.aftas.dto.fish.FishReq;
import ma.fatihii.aftas.dto.fish.FishResp;
import ma.fatihii.aftas.service.Intrfcs.IFish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/v1/fish")
@CrossOrigin
@RequiredArgsConstructor
public class FishController {

    private final IFish fishService;

    @GetMapping("/{name}")
    public ResponseEntity<FishResp> getFish(@PathVariable String name) {
        return ResponseEntity
                .ok()
                .body(fishService.findById(name).get());
    }


    @GetMapping
    public ResponseEntity<Page<FishResp>> getFishList(Pageable pageable) {
        return ResponseEntity
                .ok()
                .body(fishService.findAllFish(pageable));
    }


    @PostMapping
    public ResponseEntity<FishResp> saveFish(@Valid @RequestBody  FishReq fishReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fishService.save(fishReq).get());
    }


    @PutMapping
    public ResponseEntity<FishResp> updateFish(@RequestBody @Valid FishReq fishReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fishService.update(fishReq).get());
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteFish(@PathVariable String name) {
        fishService.delete(name);
        return ResponseEntity
                .ok()
                .body("Poisson supprimée avec succès");
    }
}
