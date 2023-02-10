package com.assessmentbvk.user.controllers;

import com.assessmentbvk.user.dto.RequestItemCart;
import com.assessmentbvk.user.services.CartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RefreshScope
@ControllerAdvice
@RequestMapping(value = "/cart", produces= MediaType.APPLICATION_JSON_VALUE)
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping(value = "/add")
    public ResponseEntity<String> addCart(@RequestHeader(name = "userId") String userId, @RequestBody RequestItemCart request) throws JsonProcessingException {
        return cartService.addCart(Integer.parseInt(userId), request);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<String> detailCart(@RequestHeader(name = "userId") String userId) throws JsonProcessingException {
        return cartService.detailCart(Integer.parseInt(userId));
    }

    @DeleteMapping(value = "/delete/{cartUuid}/{itemUuid}")
    public ResponseEntity<String> deleteItemOnCart(
        @RequestHeader(name = "userId") String userId, @PathVariable String cartUuid, @PathVariable String itemUuid) throws JsonProcessingException {
        return cartService.deleteItemOnCart(Integer.parseInt(userId), cartUuid, itemUuid);
    }

    @GetMapping(value = "/reduceItem/{cartUuid}/{itemUuid}")
    public ResponseEntity<String> reduceItemOnCart(@RequestHeader(name = "userId") String userId,
        @PathVariable String cartUuid, @PathVariable String itemUuid,
        @RequestParam(required = true, name = "amount", defaultValue = "1") String amount) throws JsonProcessingException {
        return cartService.reduceItemOnCart(Integer.parseInt(userId), cartUuid, itemUuid, Integer.parseInt(amount));
    }

    @PostMapping(value = "/calculate")
    public ResponseEntity<String> calculateItemOnCart(@RequestHeader(name = "userId") String userId, @RequestBody RequestItemCart request) throws JsonProcessingException {
        return cartService.calculateItemOnCart(Integer.parseInt(userId), request);
    }

}
