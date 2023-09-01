package com.bit.shoppingmall.web.restcontroller;

import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import com.bit.shoppingmall.app.service.likes.ProductLikesService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikesRestController {

  private final ProductLikesService likesService;

  @PostMapping
  private int addLikes(@PathVariable Long productId,
      @SessionAttribute("loginMember") MemberDetail loginMember) throws Exception {
    return likesService.addLikes(
        ProductAndMemberCompositeKey.builder().productId(productId).memberId(loginMember.getId())
            .build());
  }

  @DeleteMapping
  private int cancelLikes(@RequestParam Long productId,
      @SessionAttribute("loginMember") MemberDetail loginMember) throws Exception {
    return likesService.removeLikes(
        ProductAndMemberCompositeKey.builder().productId(productId).memberId(loginMember.getId())
            .build());
  }

  @DeleteMapping("/some")
  private int cancelSomeLikes(@RequestParam List<Long> productIdList,
      @SessionAttribute("loginMember") MemberDetail loginMember) throws Exception {
    List<ProductAndMemberCompositeKey> compKey = new ArrayList<>();
    for (Long productId : productIdList) {
      compKey.add(
          ProductAndMemberCompositeKey.builder().productId(productId).memberId(loginMember.getId()).build()
      );
    }
    return likesService.removeSomeLikes(compKey);
  }

}
