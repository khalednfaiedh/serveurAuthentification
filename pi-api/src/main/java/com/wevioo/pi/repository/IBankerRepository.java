package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.account.Banker;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface represents a repository for the Banker entity, providing data access and persistence operations.
 *
 * @param <Banker> The type of entity being managed, in this case, the Banker entity.
 * @param < String>   The type of the primary key for the entity.
 *
 * @author  knh
 */
@Repository
public interface IBankerRepository extends JpaRepository<Banker ,  String> {

    List<Banker> findAllByUserTypeAndAndApprovedIntermediaryId(UserTypeEnum userTypeEnum , String bankId);
    List<Banker> findAllByParentId(String parentId);

    @Query("SELECT  banker FROM  Banker  banker " +
            "WHERE " +
            "(:bankerId IS NULL  OR   banker.id =:bankerId) " +
            "AND (:bankId IS NULL  OR  banker.approvedIntermediary.id =:bankId) " +
            "AND (banker.isAdmin =:isAdmin)")
    Page<Banker> findAllBySearch(
            @Param("bankerId") String bankerId,
            @Param("bankId") String bankId,
            @Param("isAdmin")  Boolean isAdmin,
            Pageable pageable
    );
}
