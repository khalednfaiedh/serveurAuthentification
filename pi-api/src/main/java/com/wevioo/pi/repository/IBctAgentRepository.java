package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.account.BctAgent;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * interface repository for  BctAgent entity
 */

@Repository
public interface IBctAgentRepository extends JpaRepository<BctAgent , String> {

   Page<BctAgent> findAllByUserType(UserTypeEnum userTypeEnum, Pageable pageable);
}
