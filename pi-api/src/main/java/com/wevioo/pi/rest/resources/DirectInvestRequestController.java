package com.wevioo.pi.rest.resources;

import com.wevioo.pi.domain.entity.request.ParticipationIdentification;
import com.wevioo.pi.rest.dto.request.DirectInvestRequestStepOneForPostDto;
import com.wevioo.pi.rest.dto.request.ParticipationIdentificationStepTwoForPostDto;
import com.wevioo.pi.rest.dto.response.ParticipationIdentificationStepTwoForGetDto;
import com.wevioo.pi.service.DirectInvestRequestService;
import com.wevioo.pi.service.ParticipationIdentificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/directInvestRequest")
@Slf4j
public class DirectInvestRequestController {

    @Autowired
    DirectInvestRequestService directInvestRequestService;

    @Autowired
    ParticipationIdentificationService  participationIdentificationService;

    /**
     * Endpoint to save a new direct invest request on step one.
     *
     * @param directInvestRequestStepOneForPostDto The DirectInvestRequestStepOneForPostDto object containing information for creating a new direct invest request
     * @param result      The BindingResult object to handle validation errors
     * @return   saved
     */
    @PostMapping("/stepOne")
    public DirectInvestRequestStepOneForPostDto saveDirectInvestRequestStepOne(@RequestBody DirectInvestRequestStepOneForPostDto directInvestRequestStepOneForPostDto, BindingResult result){
        log.debug(" Start adding  direct invest request  step one  ....");
        return directInvestRequestService.saveDirectInvestDraftMode(directInvestRequestStepOneForPostDto , result);
    }

    @PostMapping("/stepTwo")
    public ParticipationIdentificationStepTwoForGetDto saveDirectInvestRequestStepTwo (
            @RequestBody ParticipationIdentificationStepTwoForPostDto requestStepTwo ,
            BindingResult result){
        log.debug(" Start adding  direct invest request  step  two  ....");
        return participationIdentificationService.saveParticipationIdentification(requestStepTwo,result);
    }

}
