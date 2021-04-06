package com.dcn.aaserver.domain.dto;

import lombok.Data;

@Data
public class RoleSearchDto {

    private Long id;
    private String code;
    private int pageSize;
    private int pageNumber;
}
