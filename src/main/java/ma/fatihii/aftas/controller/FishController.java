package ma.fatihii.aftas.controller;

import jakarta.validation.Valid;
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
public class FishController {

    private final IFish fishService;

    @Autowired
    FishController(IFish fishService){
        this.fishService = fishService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<FishResp> getFish(@PathVariable String name) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fishService.findById(name).get());
    }

    @GetMapping
    public ResponseEntity<Page<FishResp>> getFishList(Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fishService.findAllFish(pageable));
    }

    @PostMapping
    public ResponseEntity<FishResp> saveFish(@RequestBody @Valid FishReq fishReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fishService.save(fishReq).get());
    }

}
