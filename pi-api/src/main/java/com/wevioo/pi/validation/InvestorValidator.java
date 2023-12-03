package com.wevioo.pi.validation;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.enumeration.PersonTypeEnum;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.rest.dto.InvestorForPostDto;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class InvestorValidator implements Validator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return InvestorForPostDto.class.equals(aClass);
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
     * @param investorForPostDto InvestorForPostDto
     * @param errors             Errors
     */
    public static void validatePost(InvestorForPostDto investorForPostDto, Errors errors) {

        if (CommonUtilities.isNull(investorForPostDto.getLanguage())) {
            errors.rejectValue("language", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }

        // email validation
        CommonsValidation.emailValidation("email", errors, investorForPostDto.getEmail(), 150);
        // phoneNumber validation
        CommonsValidation.validatePhoneNumber("phoneNumber", errors, investorForPostDto.getPhoneNumber(), 15);
        // address validation
        validateAddress(investorForPostDto.getAddress(), errors);


        // ------------------------------------------
        // Specific validation for Physical Investor
        // ------------------------------------------
        if (CommonUtilities.compareTo(investorForPostDto.getInvestorType().name(),
                PersonTypeEnum.PP_NON_RESIDENT_TUNISIAN.name()) ||
                CommonUtilities.compareTo(investorForPostDto.getInvestorType().name(),
                        PersonTypeEnum.PP_NON_RESIDENT_FOREIGN.name())) {
            // firstname validation
            validateFirstAndLastName(investorForPostDto.getFirstName(), investorForPostDto.getLastName(), errors);
            if (CommonUtilities.isNullOrEmpty(investorForPostDto.getNationality())) {
                errors.rejectValue("nationality", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }
            if (CommonUtilities.isNullOrEmpty(investorForPostDto.getCountryOfResidency())) {
                errors.rejectValue("countryOfResidency", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }
        }

        // ------------------------------------------
        // Specific validation for Moral Investor
        // ------------------------------------------
        if (CommonUtilities.compareTo(investorForPostDto.getInvestorType().name(),
                PersonTypeEnum.PM_NON_RESIDENT_TUNISIAN.name()) ||
                CommonUtilities.compareTo(investorForPostDto.getInvestorType().name(),
                        PersonTypeEnum.PM_NON_RESIDENT_FOREIGN.name())) {
            // socialReason validation
            if (CommonUtilities.isNullOrEmpty(investorForPostDto.getSocialReason())) {
                errors.rejectValue(ApplicationConstants.SOCIAL_REASON, ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            } else if (!CommonUtilities.isValidSocialReason(investorForPostDto.getSocialReason())) {
                errors.rejectValue(ApplicationConstants.SOCIAL_REASON, ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_INVALID_SOCIAL_REASON_FORMAT);
            }
            if (CommonUtilities.isNullOrEmpty(investorForPostDto.getNationality())) {
                errors.rejectValue("nationality", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }

            if (CommonUtilities.isNullOrEmpty(investorForPostDto.getCountryOfResidency())) {
                errors.rejectValue("countryOfResidency", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }
        }
        CommonsValidation.validatePassword(investorForPostDto.getPassword(), "password", errors);

        if (errors.hasErrors()) {
            throw new BadRequestException("400", errors);
        }
    }


    private static void validateAddress(String address, Errors errors) {
        if (CommonUtilities.isNullOrEmpty(address)) {
            errors.rejectValue(ApplicationConstants.ADDRESS, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else if (!CommonUtilities.isValidLength(address, 150)) {
            errors.rejectValue(ApplicationConstants.ADDRESS, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }
    }

    public static void validateFirstAndLastName(String firstName, String lasName, Errors errors) {
        if (CommonUtilities.isNullOrEmpty(firstName)) {
            errors.rejectValue("firstName", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else if (!CommonUtilities.isValidLength(firstName, 50)) {
            errors.rejectValue("firstName", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }
        if (CommonUtilities.isNullOrEmpty(lasName)) {
            errors.rejectValue("lastName", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else if (!CommonUtilities.isValidLength(lasName, 50)) {
            errors.rejectValue("lastName", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }
    }
}
