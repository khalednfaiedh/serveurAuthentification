package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.ActivitySupport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * This interface represents a repository for the ActivitySupport entity, providing data access and persistence operations.
 *
 */
@Repository
public interface ActivitySupportRepository  extends CrudRepository<ActivitySupport, String> {
}
