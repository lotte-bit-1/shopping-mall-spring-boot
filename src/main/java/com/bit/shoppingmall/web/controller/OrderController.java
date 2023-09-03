package com.bit.shoppingmall.web.controller;

import com.bit.shoppingmall.app.dto.member.response.MemberDetail;
import com.bit.shoppingmall.app.dto.order.form.OrderCartCreateForm;
import com.bit.shoppingmall.app.dto.order.form.OrderCreateForm;
import com.bit.shoppingmall.app.dto.order.response.ProductOrderDetailDto;
import com.bit.shoppingmall.app.dto.order.response.ProductOrderDto;
import com.bit.shoppingmall.app.exception.order.OrderProductNotEnoughStockQuantityException;
import com.bit.shoppingmall.app.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ModelAttribute("memberId")
    public Long memberId(@SessionAttribute("loginMember") MemberDetail memberDetail) {
        return memberDetail.getId();
    }

    /* 상품 주문 폼 */
    @GetMapping("/direct")
    public String getCreateOrderForm(
            @ModelAttribute("memberId") Long memberId,
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Long quantity,
            Model model) {
        OrderCreateForm createOrderForm = orderService.getCreateOrderForm(memberId, productId);
        if(createOrderForm.getProduct().getQuantity() - quantity < 0) {
            throw new OrderProductNotEnoughStockQuantityException();
        }

        model.addAllAttributes(Map.of(
                "memberName", createOrderForm.getMemberName(),
                "defaultAddress", createOrderForm.getDefaultAddress(),
                "product", createOrderForm.getProduct(),
                "productQuantity", quantity,
                "coupons", createOrderForm.getCoupons()
        ));

        return "order/orderForm";
    }

    /* 장바구니 상품 주문 폼 */
    @GetMapping("/cart")
    public String getCreateCartOrderForm(@ModelAttribute("memberId") Long memberId, Model model) {
        OrderCartCreateForm createCartOrderForm = orderService.getCreateCartOrderForm(memberId);
        model.addAllAttributes(Map.of(
                "memberName", createCartOrderForm.getMemberName(),
                "defaultAddress", createCartOrderForm.getDefaultAddress(),
                "products", createCartOrderForm.getProducts(),
                "coupons", createCartOrderForm.getCoupons()
        ));

        return "order/orderCartForm";
    }

    @GetMapping
    public String getProductOrderDetail(@ModelAttribute("memberId") Long memberId,
                                        Model model) {
        List<ProductOrderDto> productOrders =
                orderService.getProductOrdersForMemberCurrentYear(memberId);
        model.addAttribute("productOrders", productOrders);

        return "order/orderList";
    }

    @GetMapping("/{orderId}")
    public String getProductOrderDetail(@ModelAttribute("memberId") Long memberId,
                                        @PathVariable("orderId") Long orderId,
                                        Model model) {
        ProductOrderDetailDto productOrderDetail =
                orderService.getOrderDetailsForMemberAndOrderId(orderId, memberId);
        model.addAllAttributes(Map.of(
                "products", productOrderDetail.getProducts(),
                "payment", productOrderDetail.getPayment(),
                "delivery", productOrderDetail.getDelivery(),
                "productOrderDetail", productOrderDetail
        ));

        return "order/orderDetail";
    }
}
