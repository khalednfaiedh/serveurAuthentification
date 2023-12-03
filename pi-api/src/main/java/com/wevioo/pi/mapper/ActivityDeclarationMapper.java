package com.wevioo.pi.mapper;


import com.wevioo.pi.domain.entity.request.ActivityDeclaration;
import com.wevioo.pi.domain.entity.request.ActivitySupport;
import com.wevioo.pi.rest.dto.request.ActivityDeclarationForPostDto;
import com.wevioo.pi.rest.dto.request.ActivitySupportForPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActivityDeclarationMapper {

    /**
     * Maps an activityDeclarationForPostDto to an ActivitySupport entity.
     *
     * @param activityDeclarationForPostDto The activityDeclarationDto to map.
     * @return The ActivityDeclaration entity representing the mapped Activity Declaration.
     */
    @Mapping(target = "activitySector", ignore = true)
    @Mapping(target = "activitySubSector", ignore = true)
    @Mapping(target = "activityGroup", ignore = true)
    @Mapping(target = "activityClass", ignore = true)
    ActivityDeclaration postActivityDeclarationToActivityDeclaration(ActivityDeclarationForPostDto activityDeclarationForPostDto);
}
