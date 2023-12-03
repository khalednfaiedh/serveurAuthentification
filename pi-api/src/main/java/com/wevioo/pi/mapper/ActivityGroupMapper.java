package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.referential.ActivityGroup;
import com.wevioo.pi.domain.entity.request.referential.ActivitySector;
import com.wevioo.pi.rest.dto.ActivityGroupDto;
import com.wevioo.pi.rest.dto.ActivitySectorDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityGroupMapper {

    /**
     * Converts a ActivityGroup object to its corresponding ActivityGroupDto object.
     *
     * @param activityGroup The ActivityGroup object to be converted to ActivityGroupDto
     * @return ActivityGroupDto representing the converted ActivityGroup object
     */
    ActivityGroupDto toDto(ActivityGroup activityGroup);

    /**
     * Converts a list of ActivityGroup objects to a list of corresponding ActivityGroupDto objects.
     *
     * @param activityGroups The list of ActivityGroup objects to be converted to ActivityGroupDto
     * @return List of ActivityGroupDto representing the converted list of ActivityGroup objects
     */
    List<ActivityGroupDto> toDto(List<ActivityGroup>  activityGroups);
}
