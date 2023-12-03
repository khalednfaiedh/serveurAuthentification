package com.wevioo.pi.validation;
import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.rest.dto.BankerForPostDto;
import com.wevioo.pi.rest.dto.BankerForPutDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import com.wevioo.pi.utility.CommonUtilities;
/**
 * Banker validation class
 *
 * @author kad
 *
 */
@Component
public class BankerValidation {

    private BankerValidation() {
        super();

    }

    /**
     * Validate BankerDto for method post
     *
     * @param bankerBaseDto BankerDto
     * @param errors        Errors
     * @param creator       User
     */
    public static void validateBankerForPost(BankerForPostDto bankerBaseDto, Errors errors, User creator) {

        if (CommonUtilities.isNull(bankerBaseDto.getLanguage())) {
            errors.rejectValue("language", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }

        if (CommonUtilities.isNull(bankerBaseDto.getIsAdmin())) {
            errors.rejectValue("isAdmin", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else if (Boolean.TRUE.equals(bankerBaseDto.getIsAdmin())) {
            // check for admin
            if (CommonUtilities.isNull(bankerBaseDto.getTypeAdministrator())) {
                errors.rejectValue("typeAdministrator", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }
            if (CommonUtilities.isNullOrEmpty(bankerBaseDto.getFunction())) {
                errors.rejectValue("function", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            } else if (!CommonUtilities.isValidLength(bankerBaseDto.getFunction(), 50)) {
                errors.rejectValue("function", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
            }
            if (CommonUtilities.isNullOrEmpty(bankerBaseDto.getDirection())) {
                errors.rejectValue("direction", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            } else if (!CommonUtilities.isValidLength(bankerBaseDto.getDirection(), 50)) {
                errors.rejectValue("direction", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
            }

            if (CommonUtilities.isNullOrEmpty(bankerBaseDto.getGrade())) {
                errors.rejectValue("grade", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            } else if (!CommonUtilities.isValidLength(bankerBaseDto.getGrade(), 50)) {
                errors.rejectValue("grade", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
            }
            if (CommonUtilities.isNull(bankerBaseDto.getIsActive())) {
                errors.rejectValue("isActive", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }

            if (CommonUtilities.isNullOrEmpty(bankerBaseDto.getBankId())) {
                errors.rejectValue("bankId", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }

        } else {

            // not admin


            if (CommonUtilities.isNull(bankerBaseDto.getSenioritySector())) {
                errors.rejectValue("senioritySector", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }
            if (CommonUtilities.isNull(bankerBaseDto.getInvestmentDirectAndRealEstate())) {
                errors.rejectValue("investmentDirectAndRealEstate", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }
            if (!CommonUtilities.isNullOrEmpty(bankerBaseDto.getFaxNumber())) {
                if (!CommonUtilities.isValidLength(bankerBaseDto.getFaxNumber(), 15)) {
                    errors.rejectValue("faxNumber", ApplicationConstants.BAD_REQUEST_CODE,
                            ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
                }
                if (!CommonUtilities.isValidPhoneNumber(bankerBaseDto.getFaxNumber())) {
                    errors.rejectValue("faxNumber", ApplicationConstants.BAD_REQUEST_CODE,
                            ApplicationConstants.ERROR_INVALID_PHONE_FORMAT);
                }
            }
        }
        if (CommonUtilities.isNullOrEmpty(bankerBaseDto.getUniversityDegree())) {
            errors.rejectValue("universityDegree", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);

        }
            if (CommonUtilities.isNullOrEmpty(bankerBaseDto.getFirstName())) {
                errors.rejectValue("firstName", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            } else if (!CommonUtilities.isValidLength(bankerBaseDto.getFirstName(), 50)) {
                errors.rejectValue("firstName", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
            }
            if (CommonUtilities.isNullOrEmpty(bankerBaseDto.getLastName())) {
                errors.rejectValue("lastName", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            } else if (!CommonUtilities.isValidLength(bankerBaseDto.getLastName(), 50)) {
                errors.rejectValue("lastName", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
            }


            if (!CommonUtilities.isNullOrEmpty(bankerBaseDto.getCellPhone())) {
                if (!CommonUtilities.isValidLength(bankerBaseDto.getCellPhone(), 15)) {
                    errors.rejectValue(ApplicationConstants.CELL_PHONE, ApplicationConstants.BAD_REQUEST_CODE,
                            ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
                }
                if (!CommonUtilities.isValidPhoneNumber(bankerBaseDto.getCellPhone())) {
                    errors.rejectValue(ApplicationConstants.CELL_PHONE, ApplicationConstants.BAD_REQUEST_CODE,
                            ApplicationConstants.ERROR_INVALID_PHONE_FORMAT);
                }
            } else {
                errors.rejectValue(ApplicationConstants.CELL_PHONE, ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants. ERROR_MISSING_REQUIRED_DATA);
            }

            if (!CommonUtilities.isNullOrEmpty(bankerBaseDto.getHomePhoneNumber())) {
                if (!CommonUtilities.isValidLength(bankerBaseDto.getHomePhoneNumber(), 15)) {
                    errors.rejectValue("homePhoneNumber", ApplicationConstants.BAD_REQUEST_CODE,
                            ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
                }
                if (!CommonUtilities.isValidPhoneNumber(bankerBaseDto.getHomePhoneNumber())) {
                    errors.rejectValue("homePhoneNumber", ApplicationConstants.BAD_REQUEST_CODE,
                            ApplicationConstants.ERROR_INVALID_PHONE_FORMAT);
                }
            } else {
                errors.rejectValue("homePhoneNumber", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants. ERROR_MISSING_REQUIRED_DATA);
            }


            if (CommonUtilities.isNullOrEmpty(bankerBaseDto.getPassword())) {
                errors.rejectValue("password", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }
            if (CommonUtilities.isNullOrEmpty(bankerBaseDto.getPasswordConfirmation())) {
                errors.rejectValue("passwordConfirmation", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }
            CommonsValidation.validatePassword(bankerBaseDto.getPasswordConfirmation(), "passwordConfirmation", errors);
            CommonsValidation.validatePassword(bankerBaseDto.getPassword(), "password", errors);
            CommonsValidation.emailValidation("email", errors, bankerBaseDto.getEmail(), 150);
            CommonsValidation.validatePhoneNumber("homePhoneNumber", errors, bankerBaseDto.getHomePhoneNumber(), 15);

            if (!(bankerBaseDto.getPassword() != null && bankerBaseDto.getPasswordConfirmation() != null &&
                    bankerBaseDto.getPassword().equals(bankerBaseDto.getPasswordConfirmation()))) {
                errors.rejectValue("password", ApplicationConstants.ERROR_MISSING_CONFIRMATION_PASSWORD,
                        ApplicationConstants.ERROR_MISSING_CONFIRMATION_PASSWORD);
            }
            if (errors.hasErrors()) {
                throw new BadRequestException("400", errors);
            }
        }
    /**
     * Validate BankerDto for method post
     *
     * @param bankerBaseDto BankerDto
     * @param errors        Errors
     */
    public static void validateBankerForUpdate (BankerForPutDto bankerBaseDto, Errors errors  , Boolean isAdmin  ){

        if(CommonUtilities.isNull(isAdmin)){
            errors.rejectValue("isAdmin", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }

        if( isAdmin) {
            if (CommonUtilities.isNullOrEmpty(bankerBaseDto.getBankId())) {
                errors.rejectValue("bankId", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }
            if (CommonUtilities.isNull(bankerBaseDto.getTypeAdministrator())) {
                errors.rejectValue("typeAdministrator", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }
            if (CommonUtilities.isNullOrEmpty(bankerBaseDto.getDirection())) {
                errors.rejectValue("direction", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            } else if (!CommonUtilities.isValidLength(bankerBaseDto.getDirection(), 50)) {
                errors.rejectValue("direction", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
            }
            if (CommonUtilities.isNullOrEmpty(bankerBaseDto.getFunction())) {
                errors.rejectValue("function", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            } else if (!CommonUtilities.isValidLength(bankerBaseDto.getFunction(), 50)) {
                errors.rejectValue("function", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
            }
        }else  {

            if (CommonUtilities.isNull(bankerBaseDto.getSenioritySector())) {
                errors.rejectValue("senioritySector", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }
            CommonsValidation.emailValidation("email", errors, bankerBaseDto.getEmail(), 150);

        }


        if (CommonUtilities.isNullOrEmpty(bankerBaseDto.getFirstName())) {
            errors.rejectValue("firstName", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else if (!CommonUtilities.isValidLength(bankerBaseDto.getFirstName(), 50)) {
            errors.rejectValue("firstName", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }
        if (CommonUtilities.isNullOrEmpty(bankerBaseDto.getLastName())) {
            errors.rejectValue("lastName", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        } else if (!CommonUtilities.isValidLength(bankerBaseDto.getLastName(), 50)) {
            errors.rejectValue("lastName", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
        }


        if (CommonUtilities.isNullOrEmpty(bankerBaseDto.getUniversityDegree())) {
            errors.rejectValue("universityDegree", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (!CommonUtilities.isNullOrEmpty(bankerBaseDto.getCellPhone())) {
            if (!CommonUtilities.isValidLength(bankerBaseDto.getCellPhone(), 15)) {
                errors.rejectValue(ApplicationConstants.CELL_PHONE, ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MAX_FIELD_LENGTH);
            }
            if (!CommonUtilities.isValidPhoneNumber(bankerBaseDto.getCellPhone())) {
                errors.rejectValue(ApplicationConstants.CELL_PHONE, ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_INVALID_PHONE_FORMAT);
            }
        }else {
            errors.rejectValue(ApplicationConstants.CELL_PHONE, ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        CommonsValidation.validatePhoneNumber("homePhoneNumber", errors, bankerBaseDto.getHomePhoneNumber(), 15);
        if (CommonUtilities.isNull(bankerBaseDto.getIsActive())) {
            errors.rejectValue("isActive", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (errors.hasErrors()) {
            throw new BadRequestException("400", errors);
        }
    }
}
