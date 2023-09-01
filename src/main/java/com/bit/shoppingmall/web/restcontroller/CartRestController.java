package com.bit.shoppingmall.web.restcontroller;

import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import com.bit.shoppingmall.app.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/cartApi")
public class CartRestController {

  private final CartService cartService;

  @PostMapping("/product-id/{productId}")
  public ResponseEntity<String> addCart(@RequestParam Long memberId,@PathVariable Long productId, Long requestQuantity){
      cartService.putItemInCart(new ProductAndMemberCompositeKey(productId,memberId), requestQuantity);
    return ResponseEntity.ok("카트에 성공적으로 상품이 추가 되었습니다.");
  }
  @PutMapping("/product-id/{productId}")
  public ResponseEntity<String> updateCart(@RequestParam Long memberId,@PathVariable Long productId, Long requestQuantity){
    cartService.updateItemInCart(new ProductAndMemberCompositeKey(productId,memberId), requestQuantity);
    return ResponseEntity.ok("상품의 개수가 업데이트 되었습니다.");
}
}