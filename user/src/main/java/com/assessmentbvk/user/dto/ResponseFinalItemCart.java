package com.assessmentbvk.user.dto;

import lombok.Data;

@Data
public class ResponseFinalItemCart {
    private String itemUuid;
    private String itemName;
    private Integer itemQty;
    private Integer itemPrice;
    private Integer itemTotalPrice;
}
