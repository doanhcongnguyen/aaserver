package com.dasan.aaserver.domain.dto;

import lombok.Data;

@Data
public class UserSearchDto {

    private String username;
    private String fullName;
    private String email;
    private String language;
    private String telephone;
    private int pageSize;
    private int pageNumber;
}
