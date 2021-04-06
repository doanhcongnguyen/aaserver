package com.dcn.aaserver.rest;

import com.dcn.aaserver.aspect.Log;
import com.dcn.aaserver.domain.dto.RoleDto;
import com.dcn.aaserver.domain.dto.UserDto;
import com.dcn.aaserver.domain.dto.UserSearchDto;
import com.dcn.aaserver.domain.entity.RoleEntity;
import com.dcn.aaserver.exception.BadRequestException;
import com.dcn.aaserver.service.RoleService;
import com.dcn.aaserver.service.UserService;
import com.dcn.aaserver.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserResource {

    @Autowired
    private UserService service;

    @Autowired
    private RoleService roleService;

    @Log("GET /users")
    @GetMapping("/users")
    public ResponseEntity getUsers() {
        return new ResponseEntity(service.getUsers(), HttpStatus.OK);
    }

    @Log("GET /user/{username}")
    @GetMapping("/user/{username}")
    public ResponseEntity getUser(@PathVariable String username) {
        return new ResponseEntity(service.getUserByUsername(username), HttpStatus.OK);
    }

    @Log("POST /user")
    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody @Validated UserDto dto) {
        this.validateBeforeCreate(dto);
        List<Long> roleIds = this.getRoleIds(dto.getRoles());
        List<RoleEntity> roles = roleService.getByIds(roleIds);
        return new ResponseEntity(service.create(dto, roles), HttpStatus.CREATED);
    }

    @Log("POST /user/search")
    @PostMapping(value = "/user/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity search(@RequestBody UserSearchDto dto) {
        this.validateBeforeSearch(dto);
        return new ResponseEntity(service.search(dto), HttpStatus.OK);
    }

    @Log("PUT /user")
    @PutMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody @Validated UserDto dto) {
        this.validateBeforeUpdate(dto);
        List<Long> roleIds = this.getRoleIds(dto.getRoles());
        List<RoleEntity> roles = roleService.getByIds(roleIds);
        service.update(dto, roles);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("DELETE /user")
    @DeleteMapping("/user/{ids}")
    public ResponseEntity delete(@PathVariable List<Long> ids) {
        this.validateBeforeDeleteMultiple(ids);
        service.deleteMultipleByIds(ids);
        return new ResponseEntity(HttpStatus.OK);
    }

    private void validateBeforeDeleteMultiple(List<Long> ids) {
        if (CommonUtils.isNullOrEmpty(ids)) {
            throw new BadRequestException("Ids cannot be empty or null");
        }
    }

    private void validateBeforeCreate(UserDto dto) {
        if (dto.getId() != null) {
            throw new BadRequestException("Cannot create User that having id");
        }
        if (CommonUtils.isNullOrEmpty(dto.getPassword())) {
            throw new BadRequestException("Password is required");
        }
    }

    private void validateBeforeUpdate(UserDto dto) {
        if (dto.getId() == null) {
            throw new BadRequestException("Id is null");
        }
    }

    private void validateBeforeSearch(UserSearchDto dto) {
        if (dto.getPageNumber() <= 0 || dto.getPageSize() <= 0) {
            throw new BadRequestException("Page number and page size must be greater than 0");
        }
    }

    private List<Long> getRoleIds(List<RoleDto> roleDtos) {
        return roleDtos.stream().map(r -> r.getId()).collect(Collectors.toList());
    }
}
