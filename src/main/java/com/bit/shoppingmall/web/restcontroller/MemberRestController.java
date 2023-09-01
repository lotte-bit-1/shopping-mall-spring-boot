package com.bit.shoppingmall.web.restcontroller;

import com.bit.shoppingmall.app.dto.member.request.LoginDto;
import com.bit.shoppingmall.app.dto.member.request.MemberRegisterDto;
import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.exception.member.RegisterException;
import com.bit.shoppingmall.app.service.member.MemberService;
import com.bit.shoppingmall.web.restcontroller.validation.MemberValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping()
    public ResponseEntity<Boolean> memberAdd(@RequestBody MemberRegisterDto dto) throws Exception {
        registerValidationCheck(dto);
        return new ResponseEntity<>(memberService.addMember(dto), HttpStatus.CREATED);
    }

    @GetMapping("/loginCheck/{email}")
    public ResponseEntity<Boolean> loginCheck(@PathVariable("email") String email) throws Exception {
        return ResponseEntity.ok(memberService.isDuplicatedEmail(email));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@ModelAttribute LoginDto dto, HttpSession session) throws Exception {
        MemberDetail memberDetail = memberService.login(dto);
        session.setAttribute("loginMember", memberDetail);
        return ResponseEntity.ok().build();
    }

    private void registerValidationCheck(MemberRegisterDto dto) {
        if (!MemberValidation.isValidEmail(dto.getEmail())) {
            throw new RegisterException();
        }
        if (!MemberValidation.isValidPassword(dto.getPassword())) {
            throw new RegisterException();
        }
        if (!MemberValidation.isValidName(dto.getName())) {
            throw new RegisterException();
        }
    }

}
