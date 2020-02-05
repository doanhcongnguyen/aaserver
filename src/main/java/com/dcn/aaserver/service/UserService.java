package com.dcn.aaserver.service;

import com.dcn.aaserver.domain.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers();

    Page<UserDto> getUsers(Pageable pageable);

    UserDto getUserByUsername(String username);

    UserDto create(UserDto dto);

    void update(UserDto dto);

    void delete(Long id);
}
