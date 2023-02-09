package com.assessmentbvk.user.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "itms_cart", schema = "public")
public class ItemCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "itms_cart_id")
    private Integer itemCartId;

    @Column(name = "itms_cart_uuid")
    private String itemCartUuid;

    @Column(name = "itms_id")
    private Integer itemId;

    @Column(name = "cart_id")
    private Integer cartId;

    @Column(name = "itms_qty")
    private Integer itemQty;

    @Column(name = "isdel")
    private Integer isdel;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Jakarta")
    private Date createdDate;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Jakarta")
    private Date updatedDate;
}
