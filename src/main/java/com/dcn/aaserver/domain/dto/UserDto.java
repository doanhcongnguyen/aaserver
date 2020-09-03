package com.dcn.aaserver.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDto {

    private Long id;
    @NotNull
    private String username;
    private String fullName;
    private String password;
    private String email;
    private String language;
    private String telephone;
}
