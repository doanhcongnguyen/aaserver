package com.dasan.aaserver.rest;

import com.dasan.aaserver.aspect.Log;
import com.dasan.aaserver.domain.dto.UserDto;
import com.dasan.aaserver.domain.dto.UserSearchDto;
import com.dasan.aaserver.exception.BadRequestException;
import com.dasan.aaserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserResource {

    @Autowired
    private UserService service;

    @Log("GET /users")
    @GetMapping("/users")
    public ResponseEntity getUsers(Pageable pageable) {
        // TODO: pageable is always { number: 0, size: 20 }
        return new ResponseEntity(service.getUsers(pageable), HttpStatus.OK);
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
        return new ResponseEntity(service.create(dto), HttpStatus.CREATED);
    }



    @Log("POST /user/search")
    @PostMapping(value = "/user/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity search(@RequestBody UserSearchDto dto) {
        this.validateBeforeSearch(dto);
        return new ResponseEntity(service.search(dto), HttpStatus.OK);
    }

    @Log("POST /user")
    @PutMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody @Validated UserDto dto) {
        this.validateBeforeUpdate(dto);
        service.update(dto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("DELETE /user")
    @DeleteMapping("/user/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    private void validateBeforeCreate(UserDto dto) {
        if (dto.getId() != null) {
            throw new BadRequestException("Cannot create User that having id");
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
}
