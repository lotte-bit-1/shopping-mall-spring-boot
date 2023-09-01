package com.bit.shoppingmall.web.restcontroller;

import com.bit.shoppingmall.app.dto.member.request.LoginDto;
import com.bit.shoppingmall.app.dto.member.request.MemberRegisterDto;
import com.bit.shoppingmall.app.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memberApi")
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<Boolean> memberAdd(@RequestBody MemberRegisterDto dto) throws Exception {
        return new ResponseEntity<>(memberService.addMember(dto), HttpStatus.CREATED);
    }

    @GetMapping("/loginCheck/{email}")
    public ResponseEntity<Boolean> loginCheck(@PathVariable("email") String email) throws Exception {
        return ResponseEntity.ok(memberService.isDuplicatedEmail(email));
    }
    
}
