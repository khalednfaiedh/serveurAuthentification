package com.wevioo.pi.service.imp;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.request.referential.CategorySpecificReferential;
import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.mapper.CategorySpecificReferentialMapper;
import com.wevioo.pi.mapper.SpecificReferentialMapper;
import com.wevioo.pi.repository.SpecificReferentialRepository;
import com.wevioo.pi.rest.dto.CategorySpecificReferentialDto;
import com.wevioo.pi.rest.dto.SpecificReferentialDto;
import com.wevioo.pi.service.SpecificReferentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SpecificReferentialService
 */
@Service
public class SpecificReferentialServiceImp implements SpecificReferentialService {

      @Autowired
      private SpecificReferentialRepository specificReferentialRepository;

      @Autowired
      private SpecificReferentialMapper specificReferentialMapper;


    @Autowired
    private CategorySpecificReferentialMapper categorySpecificReferentialMapper;
    /**
     * findAll By  id Category
     *
     * @param id category
     * @return list  SpecificReferential dto
     */
    @Override
    public List<SpecificReferentialDto> findAllByCategorySpecificReferentialId(Long id) {
        return  specificReferentialMapper.toDto(
                specificReferentialRepository.findAllByCategorySpecificReferentialId(id)
        );
    }

    /**
     * find AllBy  Category Specific Referential Id In
     *
     * @param ids
     * @return list  SpecificReferential Dto
     */
    @Override
    public List<CategorySpecificReferentialDto> findAllByCategorySpecificReferentialIdIn(List<Long> ids) {
        return  categorySpecificReferentialMapper.toDto(
                specificReferentialRepository.findAllByCategorySpecificReferentialIdIn(ids)
        );
    }

    /**
     * findAllByParentId
     *
     * @param parentId id parent
     * @return list  of  SpecificReferential
     */
    @Override
    public List<SpecificReferentialDto> findAllByParentId(Long parentId) {
        return  specificReferentialMapper.toDto(
                specificReferentialRepository.findAllByParentId(parentId)
        );
    }

    /**
     * findById
     *
     * @param id
     * @return SpecificReferentialDto
     */
    @Override
    public SpecificReferentialDto findById(Long id) {
        SpecificReferential  specificReferential = specificReferentialRepository
                .findById(id).orElseThrow(
                        ()-> new DataNotFoundException(ApplicationConstants.SPECIFIC_REFERENTIAL_NOT_FOUND, "SPECIFIC REFERENTIAL NOT FOUND WITH ID : "+id)
                );
        return  specificReferentialMapper.toDto(specificReferential);
    }
}
