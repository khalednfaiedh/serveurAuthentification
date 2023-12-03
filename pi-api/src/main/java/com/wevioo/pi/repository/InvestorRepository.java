package com.wevioo.pi.repository;

import com.wevioo.pi.business.investor.InvestorListProjection;
import com.wevioo.pi.domain.entity.account.Banker;
import com.wevioo.pi.domain.entity.account.Investor;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface represents a repository for the Investor entity, providing data access and persistence operations.
 * @author  shl
 */
@Repository
public interface InvestorRepository extends JpaRepository<Investor, String> {

    @Query("SELECT i.id AS id, i.investorType AS investorType, i.firstName AS firstName, " +
            "i.lastName AS lastName, i.socialReason AS socialReason, i.creationDate AS creationDate FROM Investor i")
    List<InvestorListProjection> findAllInvestorsList();

    @Query("SELECT  investor FROM  Investor  investor " +
            "WHERE " +
            "(investor.createdBy.id =:bankerId)")
    Page<Investor> findAllInvestorsForBankerBySearch(
            @Param("bankerId") String bankerId,
            Pageable pageable
    );
}
