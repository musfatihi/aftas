package ma.fatihii.aftas.controller;

import jakarta.validation.Valid;
import ma.fatihii.aftas.dto.member.MemberReq;
import ma.fatihii.aftas.dto.member.MemberResp;
import ma.fatihii.aftas.service.Intrfcs.IMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/members")
@CrossOrigin
public class MemberController {

    private final IMember memberService;

    @Autowired
    MemberController(IMember memberService){
        this.memberService = memberService;
    }

    @GetMapping("/{num}")
    public ResponseEntity<MemberResp> getMember(@PathVariable Integer num) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.findById(num).get());
    }

    @GetMapping
    public ResponseEntity<List<MemberResp>> filterMembers(
            @RequestParam(required = false) Integer num,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String familyName
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.filter(num,name,familyName));
    }

    @PostMapping
    public ResponseEntity<MemberResp> saveMember(@RequestBody @Valid MemberReq memberReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberService.save(memberReq).get());
    }

}