package com.assessmentbvk.user.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "dscnt", schema = "public")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "dscnt_id")
    private Integer discountId;

    @Column(name = "dscnt_uuid")
    private String discountUuid;

    @Column(name = "dscnt_code")
    private String discountCode;

    @Column(name = "dscnt_desc")
    private String discountDesc;

    @Column(name = "dscnt_qty")
    private Integer discountQty;

    @Column(name = "dscnt_exprt_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Jakarta")
    private Date discountExpDate;

    @Column(name = "dscnt_rate")
    private Integer discountRate;

    @Column(name = "dscnt_min_price")
    private Integer discountMinPrice;

    @Column(name = "dscnt_max_rate")
    private Integer discountMaxRate;

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
