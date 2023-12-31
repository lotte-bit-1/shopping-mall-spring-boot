package com.bit.shoppingmall.web.restcontroller;

import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.dto.order.request.OrderCartCreateDto;
import com.bit.shoppingmall.app.dto.order.request.OrderCreateDto;
import com.bit.shoppingmall.app.dto.payment.request.KakaoPayApproveRequestDto;
import com.bit.shoppingmall.app.dto.payment.request.KakaoPayReadyRequestDto;
import com.bit.shoppingmall.app.dto.payment.response.KakaoPayReadyResponseDto;
import com.bit.shoppingmall.app.service.payment.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentRestController {

    private final String cid = "TC0ONETIME";
    private final KakaoPayService kakaoPayService;

    @ModelAttribute("memberId")
    public Long memberId(@SessionAttribute("loginMember") MemberDetail memberDetail) {
        return memberDetail.getId();
    }

    @PostMapping("/kakao/ready/direct")
    public ResponseEntity<KakaoPayReadyResponseDto> kakaoPaymentReadyDirect(
            @ModelAttribute("memberId") Long memberId,
            @RequestBody OrderCreateDto orderCreateDto,
            HttpSession httpSession) {

        String partnerOrderId = UUID.randomUUID().toString();
        KakaoPayReadyRequestDto kakaoPayReadyRequestDto = KakaoPayReadyRequestDto.builder()
                .cid(cid)
                .partnerOrderId(partnerOrderId)
                .partnerUserId(String.valueOf(memberId))
                .itemName("테스트 물건")
                .quantity(orderCreateDto.getQuantity().intValue())
                .totalAmount(orderCreateDto.getTotalPrice().intValue())
                .taxFreeAmount(0)
                .approvalUrl(String.format("http://localhost:8080/orders/direct?productId=%s&quantity=%s&couponId=%s",
                        orderCreateDto.getProductId(), orderCreateDto.getQuantity(), orderCreateDto.getCouponId()))
                .cancelUrl(String.format("http://localhost:8080/orders/direct?productId=%s&quantity=%s",
                        orderCreateDto.getProductId(), orderCreateDto.getQuantity()))
                .failUrl(String.format("http://localhost:8080/orders/direct?productId=%s&quantity=%s",
                        orderCreateDto.getProductId(), orderCreateDto.getQuantity()))
                .build();
        KakaoPayReadyResponseDto kakaoPayReadyResponseDto = kakaoPayService.ready(kakaoPayReadyRequestDto);

        // order code, tid 임시 저장
        httpSession.setAttribute("partner_order_id", partnerOrderId);
        httpSession.setAttribute("order_tid", kakaoPayReadyResponseDto.getTid());

        return ResponseEntity.ok().body(kakaoPayReadyResponseDto);
    }

    @PostMapping("/kakao/ready/cart")
    public ResponseEntity<KakaoPayReadyResponseDto> kakaoPaymentReadyCart(
            @ModelAttribute("memberId") Long memberId,
            @RequestBody OrderCartCreateDto orderCartCreateDto,
            HttpSession httpSession) {

        String partnerOrderId = UUID.randomUUID().toString();
        KakaoPayReadyRequestDto kakaoPayReadyRequestDto = KakaoPayReadyRequestDto.builder()
                .cid(cid)
                .partnerOrderId(partnerOrderId)
                .partnerUserId(String.valueOf(memberId))
                .itemName("테스트 물건")
                .quantity(1)
                .totalAmount(orderCartCreateDto.getTotalPrice().intValue())
                .taxFreeAmount(0)
                .approvalUrl(String.format("http://localhost:8080/orders/cart?couponId=%s",
                        orderCartCreateDto.getCouponId()))
                .cancelUrl("http://localhost:8080/orders/cart")
                .failUrl("http://localhost:8080/orders/cart")
                .build();
        KakaoPayReadyResponseDto kakaoPayReadyResponseDto = kakaoPayService.ready(kakaoPayReadyRequestDto);

        // order code, tid 임시 저장
        httpSession.setAttribute("partner_order_id", partnerOrderId);
        httpSession.setAttribute("order_tid", kakaoPayReadyResponseDto.getTid());

        return ResponseEntity.ok().body(kakaoPayReadyResponseDto);
    }

    @GetMapping("/kakao/approve")
    public ResponseEntity<Long> kakaoPaymentApprove(
            @ModelAttribute("memberId") Long memberId,
            @RequestParam("orderId") String orderId,
            @RequestParam("pg_token") String pgToken,
            @SessionAttribute("partner_order_id") String partnerOrderId,
            @SessionAttribute("order_tid") String tid,
            HttpSession httpSession) {
        KakaoPayApproveRequestDto kakaoPayApproveRequestDto = KakaoPayApproveRequestDto.builder()
                .cid(cid)
                .tid(tid)
                .pgToken(pgToken)
                .partnerOrderId(partnerOrderId)
                .partnerUserId(String.valueOf(memberId))
                .build();

        kakaoPayService.approve(kakaoPayApproveRequestDto);

        httpSession.removeAttribute("order_tid");
        httpSession.removeAttribute("partner_order_id");

        return ResponseEntity.ok().body(Long.valueOf(orderId));
    }
}
