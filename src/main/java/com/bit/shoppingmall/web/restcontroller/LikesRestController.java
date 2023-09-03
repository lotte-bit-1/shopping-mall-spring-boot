package com.bit.shoppingmall.web.restcontroller;

import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import com.bit.shoppingmall.app.service.likes.ProductLikesService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikesRestController {

  private final ProductLikesService likesService;

  @PostMapping("/{productId}")
  public ResponseEntity<Integer> addLikes(@PathVariable Long productId,
      @SessionAttribute("loginMember") MemberDetail loginMember) throws Exception {
    return new ResponseEntity<>(likesService.addLikes(
        ProductAndMemberCompositeKey.builder().productId(productId).memberId(loginMember.getId())
            .build()), HttpStatus.CREATED);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Integer> cancelLikes(@PathVariable Long productId,
      @SessionAttribute("loginMember") MemberDetail loginMember) throws Exception {
    return ResponseEntity.ok().body(likesService.removeLikes(
        ProductAndMemberCompositeKey.builder().productId(productId).memberId(loginMember.getId())
            .build()));
  }

  @DeleteMapping("/some")
  public ResponseEntity<Integer> cancelSomeLikes(@RequestBody List<Long> productIdList,
      @SessionAttribute("loginMember") MemberDetail loginMember) throws Exception {
    List<ProductAndMemberCompositeKey> compKey = new ArrayList<>();
    for (Long productId : productIdList) {
      compKey.add(
          ProductAndMemberCompositeKey.builder().productId(productId).memberId(loginMember.getId())
              .build()
      );
    }
    return ResponseEntity.ok().body(likesService.removeSomeLikes(compKey));
  }

}
