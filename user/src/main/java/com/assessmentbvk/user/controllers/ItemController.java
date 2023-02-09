package com.assessmentbvk.user.controllers;

import com.assessmentbvk.user.dto.RequestItem;
import com.assessmentbvk.user.services.ItemService;
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
@RequestMapping(value = "/item", produces= MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/detail/{itemUuid}")
    public ResponseEntity<String> detailItem(@PathVariable String itemUuid) throws JsonProcessingException {
        return itemService.detailItem(itemUuid);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<String> listCategory(
        @RequestParam(defaultValue = "", required = false, name = "search") String search,
        @PageableDefault(sort = {"itemUuid"}, direction = Sort.Direction.DESC, size = 30, page = 0) Pageable pageable
    ) throws JsonProcessingException {
        return itemService.listItem(search, pageable);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<String> addItem(@RequestHeader(name = "userId") String userId, @RequestBody RequestItem request) throws JsonProcessingException {
        return itemService.addItem(Integer.parseInt(userId), request);
    }

    @PutMapping(value = "/update/{itemUuid}")
    public ResponseEntity<String> updateItem(@RequestHeader(name = "userId") String userId, @PathVariable String itemUuid, @RequestBody RequestItem request) throws JsonProcessingException {
        return itemService.updateItem(Integer.parseInt(userId), itemUuid, request);
    }

    @DeleteMapping(value = "/delete/{categoryUuid}")
    public ResponseEntity<String> deleteItem(@PathVariable String itemUuid) throws JsonProcessingException {
        return itemService.deleteItem(itemUuid);
    }
}
