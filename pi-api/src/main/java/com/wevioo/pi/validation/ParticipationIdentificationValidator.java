package com.wevioo.pi.validation;


import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.rest.dto.request.ParticipationIdentificationStepTwoForPostDto;
import com.wevioo.pi.utility.CommonUtilities;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *    Participation Identification Validator class
 *
 * @author  knh
 *
 */

@Component
public class ParticipationIdentificationValidator {

    private ParticipationIdentificationValidator() {
        super();
    }

    /**
     * validate Step Two For Post
     *
     * @param requestStepTwo
     * @param errors
     */
    public static void validateStepTwoForPost(ParticipationIdentificationStepTwoForPostDto requestStepTwo, Errors errors, Boolean isLegalFormUsa) {


        if (CommonUtilities.isNullOrEmpty(requestStepTwo.getIdFicheDirectInvest())) {
            errors.rejectValue("idFicheDirectInvest", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }

        if (CommonUtilities.isNull(requestStepTwo.getSocialCapital())) {
            errors.rejectValue("socialCapital", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }

        if (CommonUtilities.isNull(requestStepTwo.getFreeCapital())) {
            errors.rejectValue("freeCapital", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }


        if (CommonUtilities.isNull(requestStepTwo.getNominalValue())) {
            errors.rejectValue("nominalValue", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }

        if (CommonUtilities.isNullOrEmptyDate(requestStepTwo.getImmatriculationDate())) {
            errors.rejectValue("immatriculationDate", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }

        if (CommonUtilities.isNull(requestStepTwo.getParticipationRate())) {
            errors.rejectValue("participationRate", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }

        if (CommonUtilities.isNull(requestStepTwo.getContributionAmount())) {
            errors.rejectValue("contributionAmount", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
        if (CommonUtilities.isNull(requestStepTwo.getNumberAction())) {
            errors.rejectValue("numberAction", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
    if(isLegalFormUsa)
        {
            if (CommonUtilities.isNull(requestStepTwo.getPaidCapitalByTranche())) {
                errors.rejectValue("paidCapitalByTranche", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }

            if (CommonUtilities.isNull(requestStepTwo.getNumberPart())) {
                errors.rejectValue("numberPart", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
            }
        }


        if (errors.hasErrors()) {
            throw new BadRequestException("400", errors);
        }
    }

}
