package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.referential.ActivityGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityGroupRepository extends JpaRepository<ActivityGroup, String> {
}
