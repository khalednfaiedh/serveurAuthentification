package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.referential.CategorySpecificReferential;
import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * repository for  SpecificReferential Entity
 */
@Repository
public interface SpecificReferentialRepository extends CrudRepository<SpecificReferential, Long> {
    /**
     * findAll By  id Category
     * @param id category
     * @return list  SpecificReferential
     */
    List<SpecificReferential> findAllByCategorySpecificReferentialId(Long  id);

    /**
     * find AllBy  Category Specific Referential Id In
     * @param ids
     * @return list  CategorySpecificReferential
     */

    @Query("SELECT c from  CategorySpecificReferential  c WHERE  c.id  IN :ids")
    List<CategorySpecificReferential> findAllByCategorySpecificReferentialIdIn(@Param("ids") List<Long>  ids);

    /**
     *  findAllByParentId
     * @param parentId id parent
     * @return list  of  SpecificReferential
     */
    List<SpecificReferential> findAllByParentId(Long parentId);
}
