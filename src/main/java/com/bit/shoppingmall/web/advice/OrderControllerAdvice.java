package com.bit.shoppingmall.web.advice;

import com.bit.shoppingmall.app.exception.order.OrderProductNotEnoughStockQuantityException;
import com.bit.shoppingmall.web.controller.LikesController;
import com.bit.shoppingmall.web.controller.OrderController;
import com.bit.shoppingmall.web.restcontroller.LikesRestController;
import com.bit.shoppingmall.web.restcontroller.OrderRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Order(0)
@ControllerAdvice(assignableTypes = {OrderController.class, OrderRestController.class})
public class OrderControllerAdvice {

    @ExceptionHandler(OrderProductNotEnoughStockQuantityException.class)
    public ModelAndView orderProductNotEnoughStockQuantityException(
            OrderProductNotEnoughStockQuantityException ex, Model model) {
        log.warn("WARN={}", ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());
        return new ModelAndView("redirect:/product/1/list");
    }
}
