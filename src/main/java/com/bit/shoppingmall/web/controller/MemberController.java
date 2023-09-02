package com.bit.shoppingmall.web.controller;

import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.dto.member.response.MypageMemberDetail;
import com.bit.shoppingmall.app.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/registerForm")
    public String registerForm() throws Exception {
        return "member/registerForm";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) throws Exception {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("mypage")
    public String mypage(@SessionAttribute("loginMember") MemberDetail loginMember, Model model) throws Exception {
        MypageMemberDetail mypageMemberDetail = memberService.getMypageMemberDetail(loginMember.getId());
        model.addAttribute("memberInfo", mypageMemberDetail);
        return "member/mypage";
    }
}
