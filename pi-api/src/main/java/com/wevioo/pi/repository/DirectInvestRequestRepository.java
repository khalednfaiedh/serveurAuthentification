package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectInvestRequestRepository extends JpaRepository<DirectInvestRequest, String> {
}
