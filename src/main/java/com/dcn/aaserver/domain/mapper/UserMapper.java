package com.dcn.aaserver.domain.mapper;

import com.dcn.aaserver.domain.dto.UserDto;
import com.dcn.aaserver.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<UserDto, UserEntity> {

    @Mapping(target = "fullName", expression = "java(fullNameConverter(entity.getFullName()))")
    @Mapping(target = "password", ignore = true)
    UserDto toDto(UserEntity entity);

    @Named("fullNameConverter")
    default String fullNameConverter(String fullName) {
        return fullName == null ? null : fullName.toUpperCase();
    }
}
