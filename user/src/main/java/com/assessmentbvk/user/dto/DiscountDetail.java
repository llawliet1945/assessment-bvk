package com.assessmentbvk.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DiscountDetail {

    private String discountUuid;

    private String discountCode;

    private String discountDesc;

    private Integer discountQty;

    private Date discountExpDate;

    private Integer discountRate;

    private Integer discountMinPrice;

    private Integer discountMaxRate;

}
