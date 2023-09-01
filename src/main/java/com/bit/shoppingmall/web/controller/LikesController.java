package com.bit.shoppingmall.web.controller;

import com.bit.shoppingmall.app.dto.likes.response.LikesListWithPagination;
import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.service.likes.ProductLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikesController {

  private final ProductLikesService likesService;
  private Long memberId;

  @GetMapping
  public String getLikes(@RequestParam(required = false, defaultValue = "1") Integer curPage,
      @SessionAttribute("loginMember") MemberDetail loginMember, Model model)
      throws Exception {
    memberId = loginMember.getId();
    LikesListWithPagination products = likesService.getMemberLikes(memberId, curPage);
    model.addAttribute("products", products);
    return "/likes/likesList";
  }

}
