package com.assessmentbvk.user.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PagingItemList {
    Map<String, Object> pages;
    List<ItemListDTO> data;
}
