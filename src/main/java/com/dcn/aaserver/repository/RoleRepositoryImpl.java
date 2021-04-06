package com.dcn.aaserver.repository;

import com.dcn.aaserver.domain.dto.PaginationDto;
import com.dcn.aaserver.domain.entity.RoleEntity;
import com.dcn.aaserver.domain.entity.UserEntity;
import com.dcn.aaserver.utils.CommonUtils;
import com.dcn.aaserver.utils.PaginationUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RoleRepositoryImpl implements RoleRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public PaginationDto filter(String code, int pageSize, int pageNumber) {
        List<Object> paramList = new ArrayList<>();
        String filterSql = this.createFilterSqlAndAddParam(paramList, code);
        String countSql = "SELECT COUNT(1) " + filterSql;
        String objectSql = "SELECT * " + filterSql;

        Query countQuery = entityManager.createNativeQuery(countSql);
        Query objectQuery = entityManager.createNativeQuery(objectSql, RoleEntity.class);

        return PaginationUtils.createPagination(paramList, countQuery, objectQuery, pageSize, pageNumber);

    }

    @Override
    @Transactional
    public void deleteMultipleByIds(List<Long> ids) {
        String sql = "UPDATE role SET is_deleted = 1 WHERE id IN :ids";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    private String createFilterSqlAndAddParam(List<Object> paramList, String code) {
        StringBuilder filterSql = new StringBuilder(" FROM role r " +
                " WHERE r.is_deleted = 0");
        if (!CommonUtils.isNullOrEmpty(code)) {
            filterSql.append(" AND r.code LIKE ?");
            paramList.add("%" + code + "%");
        }
        return filterSql.toString();
    }
}
