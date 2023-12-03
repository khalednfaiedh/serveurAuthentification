package com.wevioo.pi.service;

import com.wevioo.pi.rest.dto.request.ParticipationIdentificationStepTwoForPostDto;
import com.wevioo.pi.rest.dto.response.ParticipationIdentificationStepTwoForGetDto;
import org.springframework.validation.BindingResult;


public interface ParticipationIdentificationService {

  ParticipationIdentificationStepTwoForGetDto saveParticipationIdentification(
          ParticipationIdentificationStepTwoForPostDto requestStepTwo,
          BindingResult result
  );
}
