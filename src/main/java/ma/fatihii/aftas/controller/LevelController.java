package ma.fatihii.aftas.controller;


import jakarta.validation.Valid;
import ma.fatihii.aftas.dto.level.LevelReq;
import ma.fatihii.aftas.dto.level.LevelResp;
import ma.fatihii.aftas.service.Implmnts.LevelService;
import ma.fatihii.aftas.service.Intrfcs.ILevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/levels")
@CrossOrigin
public class LevelController {
    private final ILevel levelService;

    @Autowired
    LevelController(ILevel levelService){
        this.levelService = levelService;
    }


    @GetMapping("/{code}")
    public ResponseEntity<LevelResp> getLevel(@PathVariable Integer code) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(levelService.findById(code).get());
    }

    @GetMapping
    public ResponseEntity<List<LevelResp>> getLevels() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(levelService.findAll());
    }

    @PostMapping
    public ResponseEntity<LevelResp> saveLevel(@RequestBody @Valid LevelReq levelReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(levelService.save(levelReq).get());
    }
}
