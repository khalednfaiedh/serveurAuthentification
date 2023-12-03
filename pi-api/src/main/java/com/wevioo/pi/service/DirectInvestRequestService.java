package com.wevioo.pi.service;

import com.wevioo.pi.rest.dto.BankerForGetDto;
import com.wevioo.pi.rest.dto.BankerForPostDto;
import com.wevioo.pi.rest.dto.request.DirectInvestRequestStepOneForPostDto;
import org.springframework.validation.BindingResult;

public interface DirectInvestRequestService {

    /**
     * Saves a direct invest request using the provided directInvestRequestForPostDto.
     *
     * @param directInvestRequestStepOneForPostDto The directInvestRequestDto object containing information for creating a new direct Invest Request
     * @param result    The BindingResult object to handle validation errors
     * @return DirectInvestRequestStepOneForPostDto representing the saved direct invest request if successful, null otherwise
     */
    DirectInvestRequestStepOneForPostDto saveDirectInvestDraftMode (DirectInvestRequestStepOneForPostDto directInvestRequestStepOneForPostDto ,
                                BindingResult result);
}
