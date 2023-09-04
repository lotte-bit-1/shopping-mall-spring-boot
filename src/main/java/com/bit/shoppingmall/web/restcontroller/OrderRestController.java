package com.bit.shoppingmall.web.restcontroller;

import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.dto.order.request.OrderCartCreateDto;
import com.bit.shoppingmall.app.dto.order.request.OrderCreateDto;
import com.bit.shoppingmall.app.entity.Order;
import com.bit.shoppingmall.app.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    @ModelAttribute("memberId")
    public Long memberId(@SessionAttribute("loginMember") MemberDetail memberDetail) {
        return memberDetail.getId();
    }

    @PostMapping("/direct")
    public ResponseEntity<Long> createOrder(
            @ModelAttribute("memberId") Long memberId,
            @RequestParam(value = "couponId") Long couponId,
            @Valid @RequestBody OrderCreateDto orderCreateDto) throws Exception {
        orderCreateDto.setMemberId(memberId);
        orderCreateDto.setCouponId(couponId);
        Order order = orderService.createOrder(orderCreateDto);

        return ResponseEntity.ok().body(order.getId());
    }

    @PostMapping("/cart")
    public ResponseEntity<Long> createCartOrder(
            @ModelAttribute("memberId") Long memberId,
            @RequestParam(value = "couponId") Long couponId,
            @Valid @RequestBody OrderCartCreateDto orderCartCreateDto) throws Exception {
        orderCartCreateDto.setMemberId(memberId);
        orderCartCreateDto.setCouponId(couponId);
        Order cartOrder = orderService.createCartOrder(orderCartCreateDto);

        return ResponseEntity.ok().body(cartOrder.getId());
    }

    @DeleteMapping("/{orderId}")
    public Long deleteOrder(@ModelAttribute("memberId") Long memberId, @PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return orderId;
    }
}
