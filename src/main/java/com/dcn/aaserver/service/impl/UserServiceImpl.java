package com.dcn.aaserver.service.impl;

import com.dcn.aaserver.domain.dto.UserDto;
import com.dcn.aaserver.domain.entity.UserEntity;
import com.dcn.aaserver.domain.mapper.UserMapper;
import com.dcn.aaserver.repository.UserRepository;
import com.dcn.aaserver.service.UserService;
import com.dcn.aaserver.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Override
    public List<UserDto> getUsers() {
        List<UserEntity> list = repository.findAll();
        return mapper.toDto(list);
    }

    public Page<UserDto> getUsers(Pageable pageable) {
        Page<UserEntity> page = repository.findAllPageable(pageable);
        return page.map(mapper::toDto);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        Optional<UserEntity> user = repository.findOneByUsername(username);
        ValidationUtils.isNull(user, "User", "username", username);
        return mapper.toDto(user.get());
    }

    @Override
    public UserDto create(UserDto dto) {
        UserEntity userEntity = mapper.toEntity(dto);
        userEntity.setIsDeleted(0L);
        UserEntity entity = repository.save(userEntity);
        return mapper.toDto(entity);
    }

    @Override
    public void update(UserDto dto) {
        this.validateIdExists(dto.getId());
        UserEntity userToUpdate = mapper.toEntity(dto);
        userToUpdate.setIsDeleted(0L);
        repository.save(userToUpdate);
    }

    @Override
    public void delete(Long id) {
        this.validateIdExists(id);
        repository.deleteById(id);
    }

    void validateIdExists(Long id) {
        Optional<UserEntity> oldUser = repository.findOneById(id);
        ValidationUtils.isNull(oldUser, "User", "id", id);
    }
}
