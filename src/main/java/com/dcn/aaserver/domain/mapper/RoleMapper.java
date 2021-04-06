package com.dcn.aaserver.domain.mapper;

import com.dcn.aaserver.domain.dto.RoleDto;
import com.dcn.aaserver.domain.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends EntityMapper<RoleDto, RoleEntity> {

}
