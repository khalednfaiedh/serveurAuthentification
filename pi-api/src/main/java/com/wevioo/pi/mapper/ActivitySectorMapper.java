package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.referential.ActivitySector;
import com.wevioo.pi.rest.dto.ActivitySectorDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivitySectorMapper {

    /**
     * Converts a ActivitySector object to its corresponding ActivitySectorDto object.
     *
     * @param activitySector The ActivitySector object to be converted to ActivitySectorDto
     * @return ActivitySectorDto representing the converted ActivitySector object
     */
    ActivitySectorDto toDto(ActivitySector activitySector);

    /**
     * Converts a list of ActivitySector objects to a list of corresponding ActivitySectorDto objects.
     *
     * @param activitySectors The list of ActivitySector objects to be converted to ActivitySectorDto
     * @return List of ActivitySectorDto representing the converted list of ActivitySector objects
     */
    List<ActivitySectorDto> toDto(List<ActivitySector>  activitySectors);
}
