package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.referential.ActivityClass;
import com.wevioo.pi.domain.entity.request.referential.Authority;
import com.wevioo.pi.rest.dto.ActivityClassDto;
import com.wevioo.pi.rest.dto.AuthorityDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {

    /**
     * Converts a Authority object to its corresponding AuthorityDto object.
     *
     * @param authority The Authority object to be converted to AuthorityDto
     * @return AuthorityDto representing the converted Authority object
     */
    AuthorityDto toDto(Authority authority);

    /**
     * Converts a list of Authority objects to a list of corresponding AuthorityDto objects.
     *
     * @param authorities The list of Authority objects to be converted to AuthorityDto
     * @return List of AuthorityDto representing the converted list of Authority objects
     */
    List<AuthorityDto> toDto(List<Authority> authorities);
}
