package com.dcn.aaserver.service;

import com.dcn.aaserver.domain.dto.UserDto;
import com.dcn.aaserver.domain.dto.UserSearchDto;
import com.dcn.aaserver.domain.entity.RoleEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers();

    Page<UserDto> search(UserSearchDto dto);

    UserDto getUserByUsername(String username);

    UserDto create(UserDto dto, List<RoleEntity> roles);

    void update(UserDto dto, List<RoleEntity> roles);

    void delete(Long id);

    void changePass(String username, String newPassword);

    void deleteMultipleByIds(List<Long> ids);
}
