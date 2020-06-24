package com.dasan.aaserver.service.impl;

import com.dasan.aaserver.domain.dto.PaginationDto;
import com.dasan.aaserver.domain.dto.UserDto;
import com.dasan.aaserver.domain.dto.UserSearchDto;
import com.dasan.aaserver.domain.entity.UserEntity;
import com.dasan.aaserver.domain.mapper.UserMapper;
import com.dasan.aaserver.repository.UserRepository;
import com.dasan.aaserver.service.UserService;
import com.dasan.aaserver.utils.PaginationUtils;
import com.dasan.aaserver.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    public Page<UserDto> search(UserSearchDto dto) {
        PaginationDto paginationDto = repository.filter(dto.getUsername(), dto.getFullName(),
                dto.getTelephone(), dto.getEmail(), dto.getPageSize(), dto.getPageNumber());
        List<UserEntity> listEntity = (List<UserEntity>) paginationDto.getList();
        List<UserDto> listDto = mapper.toDto(listEntity);
        Pageable pageable = PaginationUtils.buildPageable(paginationDto);
        return new PageImpl<>(listDto, pageable, paginationDto.getTotal());
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
