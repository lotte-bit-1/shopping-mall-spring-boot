package com.bit.shoppingmall.web.controller;

import com.bit.shoppingmall.app.dto.address.AddressInfo;
import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.service.address.AddressService;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/address")
public class AddressController {
  private final AddressService addressService;

  @GetMapping("save")
  public ResponseEntity<String> getAddressList(
      @RequestParam String roadName,
      @RequestParam String addrDetail,
      @RequestParam String zipCode,
      HttpSession session,
      Model model) {
    MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");
    Long id = loginMember.getId();
    AddressInfo addressInfo =
        AddressInfo.builder().roadName(roadName).addrDetail(addrDetail).zipCode(zipCode).build();

    addressService.addAddress(addressInfo, id);

    return ResponseEntity.ok().body("배송지 추가 완료");
  }
}
