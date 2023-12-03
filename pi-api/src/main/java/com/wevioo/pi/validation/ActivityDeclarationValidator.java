package com.wevioo.pi.validation;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.rest.dto.request.ActivityDeclarationForPostDto;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ActivityDeclarationValidator implements Validator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return ActivityDeclarationForPostDto.class.equals(aClass);
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
     * @param activityDeclarationForPostDto ActivityDeclarationForPostDto
     * @param errors             Errors
     */
    public static void validatePost(ActivityDeclarationForPostDto activityDeclarationForPostDto, Errors errors) {
        if (CommonUtilities.isNullOrEmpty(activityDeclarationForPostDto.getActivitySector())) {
            errors.rejectValue("activitySector", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
    }

}
