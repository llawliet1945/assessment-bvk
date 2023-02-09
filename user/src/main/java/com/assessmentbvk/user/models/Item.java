package com.assessmentbvk.user.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "itms", schema = "public")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "itms_id")
    private Integer itemId;

    @Column(name = "itms_uuid")
    private String itemUuid;

    @Column(name = "itms_cat_id")
    private Integer itemCatId;

    @Column(name = "itms_name")
    private String itemName;

    @Column(name = "itms_img")
    private String itemImage;

    @Column(name = "itms_qty")
    private Integer itemQty;

    @Column(name = "itms_price")
    private Integer itemPrice;

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
