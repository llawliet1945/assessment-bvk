package com.assessmentbvk.user.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ctgory", schema = "public")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "ctgory_id")
    private Integer categoryId;

    @Column(name = "ctgory_uuid")
    private String categoryUuid;

    @Column(name = "ctgory_name")
    private String categoryName;

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
