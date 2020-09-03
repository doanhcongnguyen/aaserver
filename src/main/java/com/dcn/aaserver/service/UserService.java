package com.dcn.aaserver.service;

import com.dcn.aaserver.domain.dto.UserDto;
import com.dcn.aaserver.domain.dto.UserSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers();

    Page<UserDto> search(UserSearchDto dto);

    UserDto getUserByUsername(String username);

    UserDto create(UserDto dto);

    void update(UserDto dto);

    void delete(Long id);
}
