package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.Attachment;
import org.springframework.data.repository.CrudRepository;

public interface AttachmentsRepository extends CrudRepository<Attachment, Long> {
}
