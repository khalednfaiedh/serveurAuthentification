package com.wevioo.pi.validation;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.rest.dto.request.ActivitySupportForPostDto;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ActivitySupportValidator implements Validator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return ActivitySupportForPostDto.class.equals(aClass);
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
     * @param activitySupportForPostDto ActivityDeclarationForPostDto
     * @param errors             Errors
     */
    public static void validatePost(ActivitySupportForPostDto activitySupportForPostDto, Errors errors) {
        if (CommonUtilities.isNullOrEmpty(activitySupportForPostDto.getTypeActivitySupport())) {
            errors.rejectValue("activitySupportType", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
    }
}
