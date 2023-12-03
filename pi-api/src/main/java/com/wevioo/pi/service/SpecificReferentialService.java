package com.wevioo.pi.service;

import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
import com.wevioo.pi.rest.dto.CategorySpecificReferentialDto;
import com.wevioo.pi.rest.dto.SpecificReferentialDto;

import java.util.List;

/**
 * service   SpecificReferential
 */
public interface SpecificReferentialService {

    /**
     * findAll By  id Category
     * @param id category
     * @return list  SpecificReferential
     */
    List<SpecificReferentialDto> findAllByCategorySpecificReferentialId(Long  id);

    /**
     * find AllBy  Category Specific Referential Id In
     * @param ids
     * @return list  SpecificReferential
     */
    List<CategorySpecificReferentialDto> findAllByCategorySpecificReferentialIdIn(List<Long>  ids);

    /**
     *  findAllByParentId
     * @param parentId id parent
     * @return list  of  SpecificReferential
     */
    List<SpecificReferentialDto> findAllByParentId(Long parentId);

    /**
     * findById
     * @param id
     * @return SpecificReferentialDto
     */
    SpecificReferentialDto findById(Long id);
}
