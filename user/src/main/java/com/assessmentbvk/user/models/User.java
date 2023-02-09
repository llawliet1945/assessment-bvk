package com.assessmentbvk.user.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "usrs", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "usrs_id")
    private Integer userId;

    @Column(name = "usrs_uuid")
    private String userUuid;

    @Column(name = "usrs_first_name")
    private String userFirstName;

    @Column(name = "usrs_last_name")
    private String userLastName;
    @Column(name = "usrs_email")
    private String userEmail;

    @Column(name = "usrs_phone")
    private String userPhone;

    @Column(name = "usrs_status")
    private String userStatus;

    @Column(name = "usrs_pass")
    private String userPass;

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
