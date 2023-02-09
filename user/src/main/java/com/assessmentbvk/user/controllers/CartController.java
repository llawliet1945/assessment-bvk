package com.assessmentbvk.user.controllers;

import com.assessmentbvk.user.dto.RequestItem;
import com.assessmentbvk.user.dto.RequestItemCart;
import com.assessmentbvk.user.services.CartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping(value = "/add")
    public ResponseEntity<String> addCart(@RequestHeader(name = "userId") String userId, @RequestBody RequestItemCart request) throws JsonProcessingException {
        return cartService.addCart(Integer.parseInt(userId), request);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<String> detailCart(@RequestHeader(name = "userId") String userId) throws JsonProcessingException {
        return cartService.detailCart(Integer.parseInt(userId));
    }

    @DeleteMapping(value = "/delete/{categoryUuid}")
    public ResponseEntity<String> deleteItem(
        @RequestHeader(name = "userId") String userId, @PathVariable String cartUuid, @PathVariable String itemUuid) throws JsonProcessingException {
        return cartService.deleteItemOnCart(Integer.parseInt(userId), cartUuid, itemUuid);
    }

}
