package com.dasan.aaserver.repository;

import com.dasan.aaserver.domain.dto.PaginationDto;

public interface UserRepositoryCustom {

    PaginationDto filter(String username, String fullName, String phoneNumber, String email, int pageSize, int pageNumber);
}
