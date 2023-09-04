package com.bit.shoppingmall.app.service.payment;

import com.bit.shoppingmall.app.dto.payment.response.KakaoPayApproveResponseDto;
import com.bit.shoppingmall.app.dto.payment.response.KakaoPayReadyResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "kakaoPayApiClient",
        url = "https://kapi.kakao.com/v1/payment"
)
public interface KakaoPayApiClient {

    @PostMapping(value = "/ready", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoPayReadyResponseDto ready(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("cid") String cid,
            @RequestParam("partner_order_id") String partnerOrderId,
            @RequestParam("partner_user_id") String partnerUserId,
            @RequestParam("item_name") String itemName,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("total_amount") Integer totalAmount,
            @RequestParam("tax_free_amount") Integer taxFreeAmount,
            @RequestParam("approval_url") String approvalUrl,
            @RequestParam("cancel_url") String cancelUrl,
            @RequestParam("fail_url") String failUrl);

    @PostMapping(value = "/approve", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoPayApproveResponseDto approve(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("cid") String cid,
            @RequestParam("tid") String tid,
            @RequestParam("partner_order_id") String partnerOrderId,
            @RequestParam("partner_user_id") String partnerUserId,
            @RequestParam("pg_token") String pgToken);
}
