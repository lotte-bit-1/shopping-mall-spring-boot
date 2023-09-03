package com.bit.shoppingmall.app.service.payment;

import com.bit.shoppingmall.app.dto.payment.request.KakaoPayApproveRequestDto;
import com.bit.shoppingmall.app.dto.payment.request.KakaoPayReadyRequestDto;
import com.bit.shoppingmall.app.dto.payment.response.KakaoPayApproveResponseDto;
import com.bit.shoppingmall.app.dto.payment.response.KakaoPayReadyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoPayService {

    private final Environment env;

    private final KakaoPayApiClient kakaoPayApiClient;

    public KakaoPayReadyResponseDto ready(KakaoPayReadyRequestDto kakaoPayReadyRequestDto) {
        String kakaoAdminKey = env.getProperty("KAKAO_ADMIN_KEY");
        return kakaoPayApiClient.ready(
                String.format("KakaoAK %s", kakaoAdminKey),
                kakaoPayReadyRequestDto.getCid(),
                kakaoPayReadyRequestDto.getPartnerOrderId(),
                kakaoPayReadyRequestDto.getPartnerUserId(),
                kakaoPayReadyRequestDto.getItemName(),
                kakaoPayReadyRequestDto.getQuantity(),
                kakaoPayReadyRequestDto.getTotalAmount(),
                kakaoPayReadyRequestDto.getTaxFreeAmount(),
                kakaoPayReadyRequestDto.getApprovalUrl(),
                kakaoPayReadyRequestDto.getCancelUrl(),
                kakaoPayReadyRequestDto.getFailUrl());
    }

    public KakaoPayApproveResponseDto approve(KakaoPayApproveRequestDto kakaoPayApproveRequestDto) {
        String kakaoAdminKey = env.getProperty("KAKAO_ADMIN_KEY");
        return kakaoPayApiClient.approve(
                String.format("KakaoAK %s", kakaoAdminKey),
                kakaoPayApproveRequestDto.getCid(),
                kakaoPayApproveRequestDto.getTid(),
                kakaoPayApproveRequestDto.getPartnerOrderId(),
                kakaoPayApproveRequestDto.getPartnerUserId(),
                kakaoPayApproveRequestDto.getPgToken());
    }
}
