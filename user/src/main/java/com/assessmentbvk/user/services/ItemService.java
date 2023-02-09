package com.assessmentbvk.user.services;

import com.assessmentbvk.user.dto.*;
import com.assessmentbvk.user.models.Category;
import com.assessmentbvk.user.models.Item;
import com.assessmentbvk.user.repositories.CategoryRepository;
import com.assessmentbvk.user.repositories.ItemRepository;
import com.assessmentbvk.user.utility.GenerateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<String> listItem(String search, Pageable pageable) throws JsonProcessingException {
        PagingItemList paging = new PagingItemList();
        Sort sort = pageable.getSort();
        List<Sort.Order> newSortOrder = new ArrayList<>();
        for (Sort.Order order : sort) {
            if (order.getProperty().equalsIgnoreCase("itemName")){
                newSortOrder.add(new Sort.Order(order.getDirection(), order.getProperty()));
            }else{
                newSortOrder.add(new Sort.Order(order.getDirection(), order.getProperty()));
            }
        }
        Sort newSort = Sort.by(newSortOrder);
        Pageable newPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(), newSort);
        Page<ItemListDTO> itemList = itemRepository.findByIsdel(0, search, newPageable);
        HashMap<String, Object> pages = new HashMap<>();
        pages.put("size", newPageable.getPageSize());
        pages.put("totalElements", itemList.getTotalElements());
        pages.put("totalPages", itemList.getTotalPages());
        pages.put("page", newPageable.getPageNumber());
        paging.setPages(pages);
        paging.setData(itemList.getContent());
        return GenerateResponse.success("Get data success", paging);
    }

    public ResponseEntity<String> detailItem(String itemUuid) throws JsonProcessingException {
        Optional<Item> item = itemRepository.findByItemUuidAndIsdel(itemUuid, 0);
        if (item.isEmpty()) {
            return GenerateResponse.notFound("Item not found", null);
        }
        ResponseDetailItem response = modelMapper.map(item.get(), ResponseDetailItem.class);
        return GenerateResponse.success("Get data success", response);
    }

    public ResponseEntity<String> addItem(Integer userId, RequestItem request) throws JsonProcessingException {
        Item item = modelMapper.map(request, Item.class);
        item.setItemUuid(UUID.randomUUID().toString());
        item.setIsdel(0);
        item.setCreatedBy(userId);
        item.setCreatedDate(new Date());
        itemRepository.save(item);
        return GenerateResponse.success("Add new item success", null);
    }

    public ResponseEntity<String> updateItem(Integer userId, String itemUuid, RequestItem request) throws JsonProcessingException {
        Optional<Item> item = itemRepository.findByItemUuidAndIsdel(itemUuid, 0);
        if (item.isEmpty()) {
            return GenerateResponse.notFound("item not found", null);
        }
        item.get().setItemName(request.getItemName());
        item.get().setItemCatId(request.getItemCatId());
        item.get().setItemImage(request.getItemImage());
        item.get().setItemQty(request.getItemQty());
        item.get().setItemPrice(request.getItemPrice());
        item.get().setUpdatedBy(userId);
        item.get().setUpdatedDate(new Date());
        itemRepository.save(item.get());
        return GenerateResponse.success("Update item success", null);
    }

    public ResponseEntity<String> deleteItem(String itemUuid) throws JsonProcessingException {
        Optional<Item> item = itemRepository.findByItemUuidAndIsdel(itemUuid, 0);
        if (item.isEmpty()) {
            return GenerateResponse.notFound("item not found", null);
        }
        item.get().setIsdel(1);
        itemRepository.save(item.get());
        return GenerateResponse.success("Delete item success", null);
    }
}
