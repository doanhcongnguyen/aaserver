package com.dcn.aaserver.repository;

import com.dcn.aaserver.domain.dto.PaginationDto;

public interface UserRepositoryCustom {

    PaginationDto filter(String username, String fullName, String phoneNumber, String email, int pageSize, int pageNumber);
}
