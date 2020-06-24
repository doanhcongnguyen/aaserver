package com.dasan.aaserver.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginationDto<T> {

    private List<T> list;
    private int pageNumber;
    private int pageSize;
    private int total;
}
