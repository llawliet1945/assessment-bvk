package com.assessmentbvk.user.controllers;

import com.assessmentbvk.user.dto.RequestDiscountDTO;
import com.assessmentbvk.user.services.DiscountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RefreshScope
@ControllerAdvice
@RequestMapping(value = "/discount", produces= MediaType.APPLICATION_JSON_VALUE)
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping(value = "/add")
    public ResponseEntity<String> addDiscount(@RequestHeader(name = "userId") String userId, @RequestBody RequestDiscountDTO request) throws JsonProcessingException {
        return discountService.addDiscount(Integer.parseInt(userId), request);
    }

    @PutMapping(value = "/update/{discountUuid}")
    public ResponseEntity<String> updateDiscount(@RequestHeader(name = "userId") String userId,
        @PathVariable String discountUuid, @RequestBody RequestDiscountDTO request) throws JsonProcessingException {
        return discountService.updateDiscount(Integer.parseInt(userId), discountUuid, request);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<String> listDiscount(@RequestHeader(name = "userId") String userId) throws JsonProcessingException {
        return discountService.listDiscount(Integer.parseInt(userId));
    }

    @GetMapping(value = "/detail/{discountUuid}")
    public ResponseEntity<String> detailDiscount(@RequestHeader(name = "userId") String userId, @PathVariable String discountUuid) throws JsonProcessingException {
        return discountService.detailDiscount(Integer.parseInt(userId), discountUuid);
    }

    @DeleteMapping(value = "/delete/{discountUuid}")
    public ResponseEntity<String> deleteDiscount(@PathVariable String discountUuid) throws JsonProcessingException {
        return discountService.deleteDiscount(discountUuid);
    }

}
