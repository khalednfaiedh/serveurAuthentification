package com.wevioo.pi.validation;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.rest.dto.request.InvestIdentificationForPostDto;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class InvestIdentificationValidator implements Validator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return InvestIdentificationForPostDto.class.equals(aClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object o, Errors errors) {
        /**
         * overrided method
         */
    }

    /**
     * Validate Operator for post method
     *
     * @param investIdentificationForPostDto ActivityDeclarationForPostDto
     * @param errors             Errors
     */
    public static void validatePost(InvestIdentificationForPostDto investIdentificationForPostDto, Errors errors) {
        if (CommonUtilities.isNullOrEmpty(investIdentificationForPostDto.getUniqueIdentifier())) {
            errors.rejectValue("uniqueId", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNullOrEmpty(investIdentificationForPostDto.getCompanyName())) {
            errors.rejectValue("companyName", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNullOrEmpty(investIdentificationForPostDto.getLegalForm())) {
            errors.rejectValue("legalForm", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }

        if (investIdentificationForPostDto.isMainActivitySupport() &&
                (investIdentificationForPostDto.getMainActivitySupportList() == null ||
                        investIdentificationForPostDto.getMainActivitySupportList().isEmpty())) {
            errors.rejectValue("mainActivitySupportItems", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (investIdentificationForPostDto.isSecondaryActivityDeclaration() &&
                (investIdentificationForPostDto.getSecondaryActivityDeclarationList() == null ||
        investIdentificationForPostDto.getSecondaryActivityDeclarationList().isEmpty())) {
            errors.rejectValue("secondaryActivityDeclarationItems", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (investIdentificationForPostDto.isSecondaryActivitySupport() &&
                (investIdentificationForPostDto.getSecondaryActivitySupportList() == null ||
                        investIdentificationForPostDto.getSecondaryActivitySupportList().isEmpty())) {
            errors.rejectValue("secondaryActivitySupportItems", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
    }
}
