package com.dasan.aaserver.service;

import com.dasan.aaserver.domain.dto.UserDto;
import com.dasan.aaserver.domain.dto.UserSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers();

    Page<UserDto> search(UserSearchDto dto);

    Page<UserDto> getUsers(Pageable pageable);

    UserDto getUserByUsername(String username);

    UserDto create(UserDto dto);

    void update(UserDto dto);

    void delete(Long id);
}
