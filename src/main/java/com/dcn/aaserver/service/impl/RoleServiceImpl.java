package com.dcn.aaserver.service.impl;

import com.dcn.aaserver.domain.dto.PaginationDto;
import com.dcn.aaserver.domain.dto.RoleDto;
import com.dcn.aaserver.domain.dto.RoleSearchDto;
import com.dcn.aaserver.domain.entity.RoleEntity;
import com.dcn.aaserver.domain.mapper.RoleMapper;
import com.dcn.aaserver.repository.RoleRepository;
import com.dcn.aaserver.service.RoleService;
import com.dcn.aaserver.utils.PaginationUtils;
import com.dcn.aaserver.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private RoleMapper mapper;

    @Override
    public List<RoleDto> getRoles() {
        List<RoleEntity> list = repository.findAll();
        return mapper.toDto(list);
    }

    @Override
    public RoleDto getByCode(String code) {
        Optional<RoleEntity> user = repository.findOneByCode(code);
        ValidationUtils.isNull(user, "Role", "code", code);
        return mapper.toDto(user.get());
    }

    @Override
    public RoleDto create(RoleDto dto) {
        RoleEntity entity = mapper.toEntity(dto);
        entity.setIsDeleted(0L);
        RoleEntity createdEntity = repository.save(entity);
        return mapper.toDto(createdEntity);
    }

    @Override
    public Page<RoleDto> search(RoleSearchDto dto) {
        PaginationDto paginationDto = repository.filter(dto.getCode(), dto.getPageSize(), dto.getPageNumber());
        List<RoleEntity> listEntity = (List<RoleEntity>) paginationDto.getList();
        List<RoleDto> listDto = mapper.toDto(listEntity);
        Pageable pageable = PaginationUtils.buildPageable(paginationDto);
        return new PageImpl<>(listDto, pageable, paginationDto.getTotal());
    }

    @Override
    public void update(RoleDto dto) {
        RoleEntity oldRole = this.validateIdExists(dto.getId());
        RoleEntity userToUpdate = mapper.toEntity(dto);
        userToUpdate.setIsDeleted(0L);
        repository.save(userToUpdate);
    }

    @Override
    public void delete(Long id) {
        this.validateIdExists(id);
        repository.deleteById(id);
    }

    @Override
    public void deleteMultipleByIds(List<Long> ids) {
        repository.deleteMultipleByIds(ids);
    }

    @Override
    public List<RoleEntity> getByIds(List<Long> ids) {
        return repository.getByIds(ids);
    }

    RoleEntity validateIdExists(Long id) {
        Optional<RoleEntity> oldRole = repository.findOneById(id);
        ValidationUtils.isNull(oldRole, "Role", "id", id);
        return oldRole.get();
    }
}
