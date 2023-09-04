package com.bit.shoppingmall.web.controller;

import com.bit.shoppingmall.app.dto.address.response.AddressForMyPage;
import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.service.address.AddressService;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("api/address")
public class AddressController {
  private final AddressService addressService;

  @GetMapping("addresses")
  public String getAddressList(HttpSession session, Model model) {
    MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");
    Long memberId = null;
    if (loginMember != null) {
      memberId = loginMember.getId();
    }
    AddressForMyPage addressListByMemberId = addressService.getAddressListByMemberId(memberId);
    if (addressListByMemberId.getDefaultAddress() == null)
      model.addAttribute("defaultError", "기본 주소지가 없습니다");
    if (addressListByMemberId.getSubAddresses().size() == 0)
      model.addAttribute("subError", "주소지를 추가해 주세요");
    model.addAttribute("default", addressListByMemberId.getDefaultAddress());
    model.addAttribute("sub", addressListByMemberId.getSubAddresses());
    return "";
  }
}
