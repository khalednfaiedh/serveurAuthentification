package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.referential.ActivityClass;
import com.wevioo.pi.domain.entity.request.referential.ActivitySupportType;
import com.wevioo.pi.rest.dto.ActivityClassDto;
import com.wevioo.pi.rest.dto.ActivitySupportTypeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivitySupportTypeMapper {

    /**
     * Converts a ActivitySupportType object to its corresponding ActivityClassDto object.
     *
     * @param activitySupportType The ActivitySupportType object to be converted to ActivityClassDto
     * @return ActivityClassDto representing the converted ActivitySupportType object
     */
    ActivitySupportTypeDto toDto(ActivitySupportType activitySupportType);

    /**
     * Converts a list of ActivitySupportType objects to a list of corresponding ActivityClassDto objects.
     *
     * @param activitySupportTypes The list of ActivitySupportType objects to be converted to ActivityClassDto
     * @return List of ActivityClassDto representing the converted list of ActivitySupportType objects
     */
    List<ActivitySupportTypeDto> toDto(List<ActivitySupportType> activitySupportTypes);
}
