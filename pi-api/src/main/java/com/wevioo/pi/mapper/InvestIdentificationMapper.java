package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.InvestIdentification;
import com.wevioo.pi.rest.dto.request.InvestIdentificationForPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvestIdentificationMapper {

    /**
     * Maps an investIdentificationForPostDto to an InvestIdentification entity.
     *
     * @param investIdentificationForPostDto The investIdentificationDto to map.
     * @return The Invest identification entity representing the mapped InvestIdentification.
     */
    @Mapping(target = "legalForm", ignore = true)
    InvestIdentification postInvestIdentificationToInvestIdentification(InvestIdentificationForPostDto investIdentificationForPostDto);
}
