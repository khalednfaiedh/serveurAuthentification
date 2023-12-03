package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.referential.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
