package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.referential.LegalForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegalFormRepository extends JpaRepository<LegalForm, String> {
}
