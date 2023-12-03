package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.ParticipationIdentification;
import com.wevioo.pi.rest.dto.request.ParticipationIdentificationStepTwoForPostDto;
import com.wevioo.pi.rest.dto.response.ParticipationIdentificationStepTwoForGetDto;
import org.mapstruct.Mapper;

/**
 * Participation Identification Mapper
 */
@Mapper(componentModel = "spring" , uses = {})
public interface ParticipationIdentificationMapper {

    /**
     * Maps a ParticipationIdentificationStepTwoForPostDto to a ParticipationIdentification entity.
     *
     * @param dto The ParticipationIdentificationStepTwoForPostDto to map.
     * @return The ParticipationIdentification entity representing the mapped ParticipationIdentification.
     */
    ParticipationIdentification toEntity(ParticipationIdentificationStepTwoForPostDto dto);

    /**
     * Converts a ParticipationIdentification object to a ParticipationIdentificationStepTwoForGetDto object.
     *
     * @param participationIdentification The ParticipationIdentification object to be converted.
     * @return The converted ParticipationIdentificationStepTwoForGetDto object.
     */
    ParticipationIdentificationStepTwoForGetDto toDto(ParticipationIdentification participationIdentification);
}
