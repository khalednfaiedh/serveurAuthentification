package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.referential.ActivitySubSector;
import com.wevioo.pi.rest.dto.ActivitySubSectorDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivitySubSectorMapper {

    /**
     * Converts a ActivitySubSector object to its corresponding ActivitySubSectorDto object.
     *
     * @param activitySubSector The ActivitySubSector object to be converted to ActivitySubSectorDto
     * @return ActivitySubSectorDto representing the converted ActivitySubSector object
     */
    ActivitySubSectorDto toDto(ActivitySubSector activitySubSector);

    /**
     * Converts a list of ActivitySubSector objects to a list of corresponding ActivitySubSectorDto objects.
     *
     * @param activitySubSectors The list of ActivitySubSector objects to be converted to ActivitySubSectorDto
     * @return List of ActivitySubSectorDto representing the converted list of ActivitySubSector objects
     */
    List<ActivitySubSectorDto> toDto(List<ActivitySubSector>  activitySubSectors);
}
