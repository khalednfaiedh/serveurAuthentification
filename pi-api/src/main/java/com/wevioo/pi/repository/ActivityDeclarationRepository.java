package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.ActivityDeclaration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * This interface represents a repository for the ActivityDeclaration entity, providing data access and persistence operations.
 *
 */
@Repository
public interface ActivityDeclarationRepository extends CrudRepository<ActivityDeclaration, String> {
}
