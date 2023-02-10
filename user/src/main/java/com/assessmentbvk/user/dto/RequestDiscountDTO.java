package com.assessmentbvk.user.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestDiscountDTO {

    @NotNull
    private String discountCode;

    private String discountDesc;

    private Integer discountQty;

    private Integer discountExpDate;

    private Integer discountRate;

    private Integer discountMinPrice;

    private Integer discountMaxRate;

}
