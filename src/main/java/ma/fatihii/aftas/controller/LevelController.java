package ma.fatihii.aftas.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.fatihii.aftas.dto.level.LevelReq;
import ma.fatihii.aftas.dto.level.LevelResp;
import ma.fatihii.aftas.service.Intrfcs.ILevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/levels")
@CrossOrigin
@RequiredArgsConstructor
public class LevelController {

    private final ILevel levelService;


    @GetMapping("/{code}")
    public ResponseEntity<LevelResp> getLevel(@PathVariable Integer code) {
        return ResponseEntity
                .ok()
                .body(levelService.findById(code).get());
    }


    @GetMapping
    public ResponseEntity<List<LevelResp>> getLevels() {
        return ResponseEntity
                .ok()
                .body(levelService.findAll());
    }


    @PostMapping
    public ResponseEntity<LevelResp> saveLevel(@RequestBody @Valid LevelReq levelReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(levelService.save(levelReq).get());
    }

    @PutMapping
    public ResponseEntity<LevelResp> updateLevel(@RequestBody @Valid LevelReq levelReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(levelService.update(levelReq).get());
    }


    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteLevel(@PathVariable Integer code) {
        levelService.delete(code);
        return ResponseEntity
                .ok()
                .body("Niveau supprimé avec succès");
    }

}
