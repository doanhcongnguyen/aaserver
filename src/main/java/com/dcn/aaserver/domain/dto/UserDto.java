package com.dcn.aaserver.domain.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String fullName;
    private String password;
    private String email;
    private String language;
    private String telephone;
    private boolean changePass;
}
