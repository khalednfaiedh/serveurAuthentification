package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.referential.Country;
import com.wevioo.pi.rest.dto.CountryDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * ReferentialMapper
 */
@Mapper(componentModel = "spring")
public interface ReferentialMapper {

    /**
     *
     * @param country
     * @return CountryDto
     */
    CountryDto toCountryDto(Country country);

    /**
     * convert Country to CountryDto.
     *
     * @param countries the Country list.
     * @return list of {@link CountryDto}
     */
    List<CountryDto> toCountryDtoList(List<Country> countries);

    Country toCountry(CountryDto countryDto);
}
