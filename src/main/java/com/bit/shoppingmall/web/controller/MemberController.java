package com.bit.shoppingmall.web.controller;

import com.bit.shoppingmall.app.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/registerForm")
    public String registerForm() throws Exception {
        return "member/registerForm";
    }


}
