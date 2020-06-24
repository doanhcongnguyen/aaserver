package com.dasan.aaserver.utils;

import com.dasan.aaserver.domain.dto.PaginationDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

public class PaginationUtils {

    public static PaginationDto createPagination(List<Object> paramList, Query countQuery, Query objectQuery, int pageSize, int pageNumber) {
        addFilterParameter(paramList, countQuery, objectQuery);
        BigInteger total = (BigInteger) countQuery.getSingleResult();
        addPageSizeAndNumber(objectQuery, pageSize, pageNumber);
        return buildFilterQueryResult(objectQuery, pageNumber, pageSize, total);
    }

    private static PaginationDto buildFilterQueryResult(Query objectQuery, int pageNumber, int pageSize, BigInteger total) {
        return PaginationDto.builder()
                .list(objectQuery.getResultList())
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .total(total.intValue())
                .build();
    }

    private static void addPageSizeAndNumber(Query objectQuery, int pageSize, int pageNumber) {
        objectQuery.setMaxResults(pageSize);
        objectQuery.setFirstResult(pageSize * (pageNumber - 1));
    }

    private static void addFilterParameter(List<Object> listParam, Query countQuery, Query objectQuery) {
        for (int i = 0, length = listParam.size(); i < length; i++) {
            countQuery.setParameter(i + 1, listParam.get(i));
            objectQuery.setParameter(i + 1, listParam.get(i));
        }
    }

    public static Pageable buildPageable(PaginationDto paginationDto) {
        int rawPageSize = paginationDto.getPageSize();
        int rawTotal = paginationDto.getTotal();
        int temTotalPage = rawTotal / rawPageSize;
        int totalPage = rawTotal % rawPageSize == 0 ? temTotalPage : temTotalPage + 1;
        int currentPage = Math.min(paginationDto.getPageNumber(), totalPage);
        return PageRequest.of(currentPage == 0 ? 0 : currentPage - 1, rawPageSize);
    }
}
