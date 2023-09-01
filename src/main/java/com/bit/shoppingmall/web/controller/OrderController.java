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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ModelAttribute("memberId")
    public Long memberId() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession httpSession = attr.getRequest().getSession();
        return ((MemberDetail) httpSession.getAttribute("loginMember")).getId();
    }

    /* 상품 주문 폼 */
    @GetMapping("/directOrderForm")
    public String getCreateOrderForm(
            @ModelAttribute("memberId") Long memberId,
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Long quantity,
            Model model) {
        OrderCreateForm createOrderForm = orderService.getCreateOrderForm(memberId, productId);
        if(createOrderForm.getProduct().getQuantity() - quantity < 0) {
            throw new OrderProductNotEnoughStockQuantityException();
        }
        model.addAttribute("memberName", createOrderForm.getMemberName());
        model.addAttribute("defaultAddress", createOrderForm.getDefaultAddress());
        model.addAttribute("product", createOrderForm.getProduct());
        model.addAttribute("productQuantity", quantity);
        model.addAttribute("coupons", createOrderForm.getCoupons());

        return "order/orderForm";
    }

    /* 장바구니 상품 주문 폼 */
    @GetMapping("/cartOrderForm")
    public String getCreateCartOrderForm(@ModelAttribute("memberId") Long memberId, Model model) {
        OrderCartCreateForm createCartOrderForm = orderService.getCreateCartOrderForm(memberId);
        model.addAttribute("memberName", createCartOrderForm.getMemberName());
        model.addAttribute("defaultAddress", createCartOrderForm.getDefaultAddress());
        model.addAttribute("products", createCartOrderForm.getProducts());
        model.addAttribute("coupons", createCartOrderForm.getCoupons());

        return "order/orderCartForm";
    }

    @GetMapping("/orders")
    public String getProductOrderDetail(@ModelAttribute("memberId") Long memberId,
                                        Model model) {
        List<ProductOrderDto> productOrders =
                orderService.getProductOrdersForMemberCurrentYear(memberId);
        model.addAttribute("productOrders", productOrders);

        return "order/orderList";
    }

    @GetMapping("/orders/{orderId}")
    public String getProductOrderDetail(@ModelAttribute("memberId") Long memberId,
                                        @PathVariable("orderId") Long orderId,
                                        Model model) {
        ProductOrderDetailDto productOrderDetail =
                orderService.getOrderDetailsForMemberAndOrderId(orderId, memberId);
        model.addAttribute("products", productOrderDetail.getProducts());
        model.addAttribute("payment", productOrderDetail.getPayment());
        model.addAttribute("delivery", productOrderDetail.getDelivery());
        model.addAttribute("productOrderDetail", productOrderDetail);

        return "order/orderDetail";
    }
}
