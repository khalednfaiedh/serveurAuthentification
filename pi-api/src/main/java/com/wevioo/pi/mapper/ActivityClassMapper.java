package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.referential.ActivityClass;
import com.wevioo.pi.domain.entity.request.referential.ActivityGroup;
import com.wevioo.pi.rest.dto.ActivityClassDto;
import com.wevioo.pi.rest.dto.ActivityGroupDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityClassMapper {

    /**
     * Converts a ActivityClass object to its corresponding ActivityClassDto object.
     *
     * @param activityClass The ActivityClass object to be converted to ActivityClassDto
     * @return ActivityClassDto representing the converted ActivityClass object
     */
    ActivityClassDto toDto(ActivityClass activityClass);

    /**
     * Converts a list of ActivityClass objects to a list of corresponding ActivityClassDto objects.
     *
     * @param activityClasses The list of ActivityClass objects to be converted to ActivityClassDto
     * @return List of ActivityClassDto representing the converted list of ActivityClass objects
     */
    List<ActivityClassDto> toDto(List<ActivityClass> activityClasses);
}
