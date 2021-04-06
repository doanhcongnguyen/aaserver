package com.dcn.aaserver.rest;

import com.dcn.aaserver.aspect.Log;
import com.dcn.aaserver.domain.dto.RoleDto;
import com.dcn.aaserver.domain.dto.RoleSearchDto;
import com.dcn.aaserver.exception.BadRequestException;
import com.dcn.aaserver.service.RoleService;
import com.dcn.aaserver.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class RoleResource {

    @Autowired
    private RoleService service;

    @Log("GET /roles")
    @GetMapping("/roles")
    public ResponseEntity getRoles() {
        return new ResponseEntity(service.getRoles(), HttpStatus.OK);
    }

    @Log("GET /role/{code}")
    @GetMapping("/role/{code}")
    public ResponseEntity getRole(@PathVariable String code) {
        return new ResponseEntity(service.getByCode(code), HttpStatus.OK);
    }

    @Log("POST /role")
    @PostMapping(value = "/role", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody @Validated RoleDto dto) {
        this.validateBeforeCreate(dto);
        return new ResponseEntity(service.create(dto), HttpStatus.CREATED);
    }

    @Log("POST /role/search")
    @PostMapping(value = "/role/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity search(@RequestBody RoleSearchDto dto) {
        this.validateBeforeSearch(dto);
        return new ResponseEntity(service.search(dto), HttpStatus.OK);
    }

    @Log("PUT /role")
    @PutMapping(value = "/role", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody @Validated RoleDto dto) {
        this.validateBeforeUpdate(dto);
        service.update(dto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("DELETE /role")
    @DeleteMapping("/role/{ids}")
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

    private void validateBeforeCreate(RoleDto dto) {
        if (dto.getId() != null) {
            throw new BadRequestException("Cannot create Role that having id");
        }
    }

    private void validateBeforeUpdate(RoleDto dto) {
        if (dto.getId() == null) {
            throw new BadRequestException("Id is null");
        }
    }

    private void validateBeforeSearch(RoleSearchDto dto) {
        if (dto.getPageNumber() <= 0 || dto.getPageSize() <= 0) {
            throw new BadRequestException("Page number and page size must be greater than 0");
        }
    }
}
