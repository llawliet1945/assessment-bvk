package com.assessmentbvk.user.dto;

import lombok.Data;

@Data
public class RequestItem {

    private Integer itemCatId;

    private String itemName;

    private String itemImage;

    private Integer itemQty;

    private Integer itemPrice;

}
