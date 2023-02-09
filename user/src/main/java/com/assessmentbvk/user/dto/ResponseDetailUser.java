package com.assessmentbvk.user.dto;

import lombok.Data;

@Data
public class ResponseDetailUser {

    private String userUuid;

    private String userFirstName;

    private String userLastName;

    private String userEmail;

    private String userPhone;

    private String userStatus;
}
