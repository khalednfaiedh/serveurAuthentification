package com.wevioo.pi.mapper;

import com.wevioo.pi.domain.entity.request.referential.LegalForm;
import com.wevioo.pi.rest.dto.LegalFormDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LegalFormMapper {

    /**
     * Converts a LegalForm object to its corresponding LegalFormDto object.
     *
     * @param legalForm The LegalForm object to be converted to LegalFormDto
     * @return LegalFormDto representing the converted LegalForm object
     */
    LegalFormDto toDto(LegalForm  legalForm);

    /**
     * Converts a list of LegalForm objects to a list of corresponding LegalFormDto objects.
     *
     * @param legalForms The list of LegalForm objects to be converted to LegalFormDto
     * @return List of LegalFormDto representing the converted list of LegalForm objects
     */
    List<LegalFormDto> toDto(List<LegalForm>  legalForms);
}
