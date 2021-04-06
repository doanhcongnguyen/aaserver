package com.dcn.aaserver.service;

import com.dcn.aaserver.domain.dto.RoleDto;
import com.dcn.aaserver.domain.dto.RoleSearchDto;
import com.dcn.aaserver.domain.entity.RoleEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {

    List<RoleDto> getRoles();

    Page<RoleDto> search(RoleSearchDto dto);

    RoleDto getByCode(String code);

    RoleDto create(RoleDto dto);

    void update(RoleDto dto);

    void delete(Long id);

    void deleteMultipleByIds(List<Long> ids);

    List<RoleEntity> getByIds(List<Long> ids);
}
