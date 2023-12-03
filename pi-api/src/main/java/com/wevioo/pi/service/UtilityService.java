package com.wevioo.pi.service;

import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Utility class
 *
 *
 *
 */
public interface UtilityService {

    /**
     * creates a pageable object if no sort attribute is specified no sort is
     * applied
     *
     * @param page
     * @param pageSize
     * @param sort
     * @return Pageable
     */
    Pageable createPageable(Integer page, Integer pageSize, Sort sort);

    Sort sortingCriteria(Sort sort , Sort.Direction direction , String  defaultProperties);

    void isAuthorized(List<UserTypeEnum> userTypeList);
}
