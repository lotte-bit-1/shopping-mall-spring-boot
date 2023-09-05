package com.bit.shoppingmall.web.restcontroller;

import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import com.bit.shoppingmall.app.service.cart.CartService;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CartRestController {

  private final CartService cartService;
  private Long memberId;

  private Long getMemberId() {
    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpSession httpSession = attr.getRequest().getSession();
    return ((MemberDetail) httpSession.getAttribute("loginMember")).getId();

  }


  @PostMapping("/api/carts/products/{productId}")
  public ResponseEntity<String> addCart(@PathVariable Long productId,
      Long requestQuantity) {
    memberId = getMemberId();
    cartService.putItemInCart(new ProductAndMemberCompositeKey(productId, memberId),
        requestQuantity);
    return ResponseEntity.ok("카트에 성공적으로 상품이 추가 되었습니다.");
  }

  @DeleteMapping("/api/carts/products/{productId}")
  public ResponseEntity<String> deleteCart(@PathVariable Long productId)
      throws Exception {
    memberId = getMemberId();
    log.info("request memberInfo: " + memberId);
    cartService.deleteItemInCart(new ProductAndMemberCompositeKey(productId, memberId));
    return ResponseEntity.ok("카트의 상품이 삭제되었습니다.");
  }

  @PutMapping("/api/carts/products/{productId}")
  public ResponseEntity<String> updateCart(@PathVariable Long productId, @RequestParam Long requestQuantity) {
    memberId = getMemberId();
    cartService.updateItemInCart(new ProductAndMemberCompositeKey(productId, memberId),
        requestQuantity);
    return ResponseEntity.ok("상품의 개수가 업데이트 되었습니다.");
  }

}