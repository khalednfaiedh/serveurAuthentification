package com.wevioo.pi.service.imp;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import com.wevioo.pi.domain.entity.request.InvestIdentification;
import com.wevioo.pi.domain.entity.request.ParticipationIdentification;
import com.wevioo.pi.domain.entity.request.referential.LegalForm;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.exception.AlreadyExistException;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.mapper.ParticipationIdentificationMapper;
import com.wevioo.pi.repository.DirectInvestRequestRepository;
import com.wevioo.pi.repository.ParticipationIdentificationRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.request.ParticipationIdentificationStepTwoForPostDto;
import com.wevioo.pi.rest.dto.response.ParticipationIdentificationStepTwoForGetDto;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.ParticipationIdentificationService;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.validation.ParticipationIdentificationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import javax.transaction.Transactional;

/**
 * service  Participation Identification
 */
@Service
public class ParticipationIdentificationServiceImp  implements ParticipationIdentificationService {
    /**
     * @param requestStepTwo
     * @return
     */
    /**
     * Inject {@link DirectInvestRequestRepository} bean.
     */
    @Autowired
    private DirectInvestRequestRepository directInvestRequestRepository;

    /**
     * Inject {@link ParticipationIdentificationMapper} bean.
     */
    @Autowired
    private ParticipationIdentificationMapper  participationIdentificationMapper;

    /**
     * Inject {@link ParticipationIdentificationRepository} bean.
     */
    @Autowired
    private ParticipationIdentificationRepository participationIdentificationRepository;
    /**
     * {@link KeyGenService} bean.
     */
    @Autowired
    private KeyGenService keyGenService;

    /**
     * Injected bean {@link SecurityUtils}
     */
    @Autowired
    private SecurityUtils securityUtils;

    /**
     * Injected bean {@link UserRepository}
     */
    @Autowired
    private UserRepository userRepository;
    /**
     * Saves Participation Identification for Step Two.
     *
     * @param requestStepTwo The Step Two DTO containing information for saving Participation Identification.
     * @param result         The BindingResult for validation.
     * @return The DTO representing the saved Participation Identification.
     * @throws DataNotFoundException   if data is not found.
     * @throws AlreadyExistException   if the Participation Identification already exists.
     * @throws ValidationException     if validation fails.
     */
    @Override
    @Transactional
    public ParticipationIdentificationStepTwoForGetDto saveParticipationIdentification(ParticipationIdentificationStepTwoForPostDto requestStepTwo,     BindingResult result) {


        DirectInvestRequest directInvestRequest = directInvestRequestRepository.findById(requestStepTwo.getIdFicheDirectInvest())
                .orElseThrow(()-> new DataNotFoundException(ApplicationConstants.DIRECT_INVEST_REQUEST_NOT_FOUND , " fiche invest not found with id : " +requestStepTwo.getIdFicheDirectInvest() ));

        if(directInvestRequest.getParticipationIdentification() != null){
            throw  new AlreadyExistException(ApplicationConstants.PARTICIPATION_ALREADY_EXISTS ,"stepper two already created"  );
        }
        Boolean isLegalFormUsa = false;
        validateStepOne(directInvestRequest);
        LegalForm legalForm = directInvestRequest.getInvestIdentification().getLegalForm();
        if(ApplicationConstants.SA.equals(legalForm.getCode())){
            isLegalFormUsa = true;
        }
        ParticipationIdentificationValidator.validateStepTwoForPost(requestStepTwo,result, isLegalFormUsa);

        String userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));

        ParticipationIdentification participation = participationIdentificationMapper.toEntity(requestStepTwo);
        participation.setId(keyGenService.getNextKey(KeyGenType.DIRECT_INVEST_REQUEST_KEY , true , null));
        participation.setCreatedBy(user);

        participation = participationIdentificationRepository.save(participation);
        directInvestRequest.setParticipationIdentification(participation);

        directInvestRequestRepository.save(directInvestRequest);
        return  participationIdentificationMapper.toDto(participation);
    }

    /**
     * validateStepOne
     * @param directInvestRequest
     */
    private void validateStepOne( DirectInvestRequest directInvestRequest){
        if( directInvestRequest.getInvestIdentification() == null
                || directInvestRequest.getInvestIdentification().getLegalForm() ==  null
                || directInvestRequest.getInvestIdentification().getLegalForm().getCode() == null
                || directInvestRequest.getInvestIdentification().getLegalForm().getCode().isEmpty()){
            throw  new DataNotFoundException(ApplicationConstants.BAD_REQUEST_CODE ,"Step one not created or Legal Form is invalid");
        }
    }
}
