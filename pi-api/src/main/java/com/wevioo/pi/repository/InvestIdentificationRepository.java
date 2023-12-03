package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.InvestIdentification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * This interface represents a repository for the InvestIdentification entity, providing data access and persistence operations.
 *
 */
@Repository
public interface InvestIdentificationRepository extends CrudRepository<InvestIdentification , String> {
}
