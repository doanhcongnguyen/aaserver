package com.dcn.aaserver.repository;

import com.dcn.aaserver.domain.dto.PaginationDto;
import com.dcn.aaserver.domain.entity.RoleEntity;

import java.util.List;

public interface RoleRepositoryCustom {

    PaginationDto filter(String code, int pageSize, int pageNumber);

    void deleteMultipleByIds(List<Long> id);
}
