package com.bit.shoppingmall.web.controller;

import com.bit.shoppingmall.app.dto.address.response.AddressForMyPage;
import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.dto.member.response.MypageMemberDetail;
import com.bit.shoppingmall.app.service.address.AddressService;
import com.bit.shoppingmall.app.service.member.MemberService;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
  private final MemberService memberService;
  private final AddressService addressService;

  @GetMapping("/registerForm")
  public String registerForm() throws Exception {
    return "member/registerForm";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) throws Exception {
    session.invalidate();
    return "redirect:/";
  }

  @GetMapping("mypage")
  public String mypage(@SessionAttribute("loginMember") MemberDetail loginMember, Model model)
      throws Exception {
    MypageMemberDetail mypageMemberDetail =
        memberService.getMypageMemberDetail(loginMember.getId());
    AddressForMyPage addressListByMemberId =
        addressService.getAddressListByMemberId(loginMember.getId());
    if (addressListByMemberId.getDefaultAddress() == null)
      model.addAttribute("defaultError", "기본 주소지가 없습니다");
    if (addressListByMemberId.getSubAddresses().size() == 0)
      model.addAttribute("subError", "주소지를 추가해 주세요");
    model.addAttribute("defaultAddr", addressListByMemberId.getDefaultAddress());
    model.addAttribute("sub", addressListByMemberId.getSubAddresses());
    model.addAttribute("memberInfo", mypageMemberDetail);
    return "member/mypage";
  }
}
