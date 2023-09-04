package com.bit.shoppingmall.app.dto.payment.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoPayReadyRequestDto {

    private String cid;
    private String partnerOrderId;
    private String partnerUserId;
    private String itemName;
    private Integer quantity;
    private Integer totalAmount;
    private Integer taxFreeAmount;
    private String approvalUrl;
    private String cancelUrl;
    private String failUrl;
}
