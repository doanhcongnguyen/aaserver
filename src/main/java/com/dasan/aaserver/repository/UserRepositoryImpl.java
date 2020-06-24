package com.dasan.aaserver.repository;

import com.dasan.aaserver.domain.dto.PaginationDto;
import com.dasan.aaserver.domain.entity.UserEntity;
import com.dasan.aaserver.utils.CommonUtils;
import com.dasan.aaserver.utils.PaginationUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public PaginationDto filter(String username, String fullName, String telephone, String email, int pageSize, int pageNumber) {
        List<Object> paramList = new ArrayList<>();
        String filterSql = this.createFilterSqlAndAddParam(paramList, username, fullName, telephone, email);
        String countSql = "SELECT COUNT(*) " + filterSql;
        String objectSql = "SELECT u.* " + filterSql;

        Query countQuery = entityManager.createNativeQuery(countSql);
        Query objectQuery = entityManager.createNativeQuery(objectSql, UserEntity.class);

        return PaginationUtils.createPagination(paramList, countQuery, objectQuery, pageSize, pageNumber);

    }

    private String createFilterSqlAndAddParam(List<Object> paramList, String username, String fullName, String telephone, String email) {
        StringBuilder filterSql = new StringBuilder(" FROM User u " +
                " WHERE is_deleted = 0");
        if (!CommonUtils.isNullOrEmpty(username)) {
            filterSql.append(" AND username LIKE ?");
            paramList.add("%" + username + "%");
        }
        if (!CommonUtils.isNullOrEmpty(fullName)) {
            filterSql.append(" AND full_name LIKE ?");
            paramList.add("%" + fullName + "%");
        }
        if (!CommonUtils.isNullOrEmpty(telephone)) {
            filterSql.append(" AND telephone LIKE ?");
            paramList.add("%" + telephone + "%");
        }
        if (!CommonUtils.isNullOrEmpty(email)) {
            filterSql.append(" AND email LIKE ?");
            paramList.add("%" + email + "%");
        }
        return filterSql.toString();
    }
}
