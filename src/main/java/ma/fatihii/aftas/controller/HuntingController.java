package ma.fatihii.aftas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.fatihii.aftas.dto.hunting.HuntingReq;
import ma.fatihii.aftas.dto.hunting.HuntingResp;
import ma.fatihii.aftas.service.Intrfcs.IHunting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/huntings")
@CrossOrigin
@RequiredArgsConstructor
public class HuntingController {

    private final IHunting huntingService;

    @PostMapping
    public ResponseEntity<HuntingResp> saveHunting(@RequestBody @Valid HuntingReq huntingReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(huntingService.save(huntingReq).get());
    }

}
