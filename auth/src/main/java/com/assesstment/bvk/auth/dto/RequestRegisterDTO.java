package com.assesstment.bvk.auth.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestRegisterDTO {

    private String userFirstName;

    private String userLastName;

    @NotNull
    private String userEmail;

    private String userPhone;

    @NotNull
    @Size(min = 4)
    private String userPass;

    @NotNull
    @Size(min = 4)
    private String userConfirmPass;
}
