package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.ActivitySupport;
import com.wevioo.pi.rest.dto.request.ActivitySupportForPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActivitySupportMapper {

    /**
     * Maps an activitySupportForPostDto to an ActivitySupport entity.
     *
     * @param activitySupportForPostDto The activitySupportDto to map.
     * @return The ActivitySupport entity representing the mapped Activity Support.
     */
    @Mapping(target = "typeActivitySupport", ignore = true)
    @Mapping(target = "issuingAuthority", ignore = true)
    ActivitySupport postActivitySupportToActivitySupport(ActivitySupportForPostDto activitySupportForPostDto);
}
