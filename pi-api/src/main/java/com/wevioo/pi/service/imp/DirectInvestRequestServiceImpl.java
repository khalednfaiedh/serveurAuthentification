package com.wevioo.pi.service.imp;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.request.ActivityDeclaration;
import com.wevioo.pi.domain.entity.request.ActivitySupport;
import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import com.wevioo.pi.domain.entity.request.InvestIdentification;
import com.wevioo.pi.domain.entity.request.referential.ActivityClass;
import com.wevioo.pi.domain.entity.request.referential.ActivityGroup;
import com.wevioo.pi.domain.entity.request.referential.ActivitySector;
import com.wevioo.pi.domain.entity.request.referential.ActivitySubSector;
import com.wevioo.pi.domain.entity.request.referential.ActivitySupportType;
import com.wevioo.pi.domain.entity.request.referential.Authority;
import com.wevioo.pi.domain.entity.request.referential.LegalForm;
import com.wevioo.pi.domain.enumeration.ActivityTypeEnum;
import com.wevioo.pi.domain.enumeration.DirectInvestStatusEnum;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.mapper.ActivityDeclarationMapper;
import com.wevioo.pi.mapper.ActivitySupportMapper;
import com.wevioo.pi.mapper.Identifiable;
import com.wevioo.pi.mapper.InvestIdentificationMapper;
import com.wevioo.pi.repository.ActivityClassRepository;
import com.wevioo.pi.repository.ActivityDeclarationRepository;
import com.wevioo.pi.repository.ActivityGroupRepository;
import com.wevioo.pi.repository.ActivitySectorRepository;
import com.wevioo.pi.repository.ActivitySubSectorRepository;
import com.wevioo.pi.repository.ActivitySupportRepository;
import com.wevioo.pi.repository.ActivitySupportTypeRepository;
import com.wevioo.pi.repository.AuthorityRepository;
import com.wevioo.pi.repository.DirectInvestRequestRepository;
import com.wevioo.pi.repository.InvestIdentificationRepository;
import com.wevioo.pi.repository.LegalFormRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.request.ActivityDeclarationForPostDto;
import com.wevioo.pi.rest.dto.request.ActivitySupportForPostDto;
import com.wevioo.pi.rest.dto.request.DirectInvestRequestStepOneForPostDto;
import com.wevioo.pi.rest.dto.request.InvestIdentificationForPostDto;
import com.wevioo.pi.service.DirectInvestRequestService;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.UtilityService;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.validation.ActivityDeclarationValidator;
import com.wevioo.pi.validation.ActivitySupportValidator;
import com.wevioo.pi.validation.InvestIdentificationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class DirectInvestRequestServiceImpl implements DirectInvestRequestService {

    /**
     * Injected bean {@link DirectInvestRequestRepository}
     */
    @Autowired
    private DirectInvestRequestRepository directInvestRequestRepository;

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
     * Injected bean {@link InvestIdentificationRepository}
     */
    @Autowired
    private InvestIdentificationRepository investIdentificationRepository;

    /**
     * Injected bean {@link ActivitySupportRepository}
     */
    @Autowired
    private ActivitySupportRepository activitySupportRepository;

    /**
     * Injected bean {@link ActivityDeclarationRepository}
     */
    @Autowired
    private ActivityDeclarationRepository activityDeclarationRepository;

    /**
     * Injected bean {@link ActivitySectorRepository}
     */
    @Autowired
    private ActivitySectorRepository activitySectorRepository;

    /**
     * Injected bean {@link ActivitySubSectorRepository}
     */
    @Autowired
    private ActivitySubSectorRepository activitySubSectorRepository;

    /**
     * Injected bean {@link ActivityGroupRepository}
     */
    @Autowired
    private ActivityGroupRepository activityGroupRepository;

    /**
     * Injected bean {@link ActivityClassRepository}
     */
    @Autowired
    private ActivityClassRepository activityClassRepository;

    /**
     * Injected bean {@link ActivitySupportTypeRepository}
     */
    @Autowired
    private ActivitySupportTypeRepository activitySupportTypeRepository;

    /**
     * Injected bean {@link AuthorityRepository}
     */
    @Autowired
    private AuthorityRepository authorityRepository;

    /**
     * Injected bean {@link LegalFormRepository}
     */
    @Autowired
    private LegalFormRepository legalFormRepository;

    /**
     * Injected bean {@link UtilityService}
     */
    @Autowired
    private UtilityService utilityService;

    /**
     * Injected bean {@link InvestIdentificationMapper }
     */
    @Autowired
    InvestIdentificationMapper investIdentificationMapper;

    /**
     * Injected bean {@link ActivitySupportMapper }
     */
    @Autowired
    ActivitySupportMapper activitySupportMapper;

    /**
     * Injected bean {@link ActivityDeclarationMapper }
     */
    @Autowired
    ActivityDeclarationMapper activityDeclarationMapper;

    /**
     * Injected bean {@link KeyGenService}
     */
    @Autowired
    private KeyGenService keyGenService;

    @Override
    public DirectInvestRequestStepOneForPostDto saveDirectInvestDraftMode(DirectInvestRequestStepOneForPostDto directInvestRequestStepOneForPostDto, BindingResult result) {
        String userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));

        // Checking for Authorized User
        utilityService.isAuthorized(ApplicationConstants.USER_TYPE_BANKER_AND_INVESTOR);

        // getting the referential Activity values.
        List<ActivitySector> activitySectors = activitySectorRepository.findAll();
        List<ActivitySubSector> activitySubSectors = activitySubSectorRepository.findAll();
        List<ActivityGroup> activityGroups = activityGroupRepository.findAll();
        List<ActivityClass> activityClasses = activityClassRepository.findAll();
        List<ActivitySupportType> activitySupportTypes = activitySupportTypeRepository.findAll();
        List<Authority> authorities = authorityRepository.findAll();


        // initializing entities
        DirectInvestRequest directInvestRequest = new DirectInvestRequest();
        directInvestRequest.setId(keyGenService.getNextKey(KeyGenType.DIRECT_INVEST_REQUEST_KEY, true, null));
        directInvestRequest.setStatus(DirectInvestStatusEnum.DRAFT);
        directInvestRequest.setCreationDate(new Date());
        directInvestRequest.setCreatedBy(user);
        InvestIdentificationForPostDto investIdentificationForPostDto = directInvestRequestStepOneForPostDto.getInvestIdentificationForPostDto();

        // checking the invest identification information
        InvestIdentificationValidator.validatePost(investIdentificationForPostDto, result);
        // Mapping invest identification and saving...
        InvestIdentification investIdentification = investIdentificationMapper.postInvestIdentificationToInvestIdentification(investIdentificationForPostDto);
        Optional<LegalForm> legalForm = legalFormRepository.findById(investIdentificationForPostDto.getLegalForm());
        if (legalForm.isPresent()) {
            investIdentification.setLegalForm(legalForm.get());
        } else {
            throw new DataNotFoundException(ApplicationConstants.LEGAL_FORM_NOT_FOUND,
                    ApplicationConstants.NO_LEGAL_FORM_FOUNDED_WITH_ID + investIdentificationForPostDto.getLegalForm());
        }
        investIdentification.setId(keyGenService.getNextKey(KeyGenType.INVEST_IDENTIFICATION_KEY, true, null));
        investIdentification.setCreationDate(new Date());
        investIdentification.setCreatedBy(user);
        investIdentification = investIdentificationRepository.save(investIdentification);
        directInvestRequest.setInvestIdentification(investIdentification);

        // looping the principal Activities Declarations and saving...
        for (ActivityDeclarationForPostDto mainActivityDeclarationForPostDto
                : investIdentificationForPostDto.getMainActivityDeclarationList()
        ) {
            ActivityDeclarationValidator.validatePost(mainActivityDeclarationForPostDto, result);
            ActivityDeclaration mainActivityDeclaration = prepareActivityDeclaration(mainActivityDeclarationForPostDto, activitySectors,
                    activitySubSectors, activityGroups, activityClasses, investIdentification, user, ActivityTypeEnum.PRIMARY);
            activityDeclarationRepository.save(mainActivityDeclaration);
        }

        //checking if direct invest have secondary Activities Declarations
        if (investIdentificationForPostDto.isSecondaryActivityDeclaration()) {
            // looping the secondary Activities Declarations and saving...
            for (ActivityDeclarationForPostDto secondaryActivityDeclarationForPostDto
                    : investIdentificationForPostDto.getSecondaryActivityDeclarationList()
            ) {
                ActivityDeclarationValidator.validatePost(secondaryActivityDeclarationForPostDto, result);
                ActivityDeclaration secondaryActivityDeclaration = prepareActivityDeclaration(secondaryActivityDeclarationForPostDto, activitySectors,
                        activitySubSectors, activityGroups, activityClasses, investIdentification, user, ActivityTypeEnum.SECONDARY);
                activityDeclarationRepository.save(secondaryActivityDeclaration);
            }

        }

        // Checking then looping the principal Activities Supports and saving...
        if (investIdentificationForPostDto.isMainActivitySupport()) {
            for (ActivitySupportForPostDto mainActivitySupportForPostDto
                    : investIdentificationForPostDto.getMainActivitySupportList()) {
                ActivitySupportValidator.validatePost(mainActivitySupportForPostDto, result);
                ActivitySupport secondaryActivitySupport = prepareActivitySupport(mainActivitySupportForPostDto,
                        activitySupportTypes, authorities, investIdentification, user, ActivityTypeEnum.PRIMARY);
                activitySupportRepository.save(secondaryActivitySupport);
            }
        }

        // Checking then looping the secondary Activities Supports and saving...
        if (investIdentificationForPostDto.isSecondaryActivitySupport()) {
            for (ActivitySupportForPostDto secondaryActivitySupportForPostDto
                    : investIdentificationForPostDto.getSecondaryActivitySupportList()) {
                ActivitySupportValidator.validatePost(secondaryActivitySupportForPostDto, result);
                ActivitySupport secondaryActivitySupport = prepareActivitySupport(secondaryActivitySupportForPostDto,
                        activitySupportTypes, authorities, investIdentification, user, ActivityTypeEnum.SECONDARY);
                activitySupportRepository.save(secondaryActivitySupport);

            }
        }
        directInvestRequest = directInvestRequestRepository.save(directInvestRequest);
        directInvestRequestStepOneForPostDto.setId(directInvestRequest.getId());
        return directInvestRequestStepOneForPostDto;
    }

    private ActivityDeclaration prepareActivityDeclaration(ActivityDeclarationForPostDto activityDeclarationForPostDto,
                                                           List<ActivitySector> activitySectors, List<ActivitySubSector> activitySubSectors,
                                                           List<ActivityGroup> activityGroups, List<ActivityClass> activityClasses,
                                                           InvestIdentification investIdentification, User user, ActivityTypeEnum activityTypeEnum) {
        ActivityDeclaration activityDeclaration = new ActivityDeclaration();
        setEntityPropertyById(activityDeclarationForPostDto.getActivitySector(), activitySectors,
                activityDeclaration::setActivitySector, ApplicationConstants.ACTIVITY_SECTOR_NOT_FOUND,
                ApplicationConstants.NO_ACTIVITY_SECTOR_FOUNDED_WITH_ID);

        setEntityPropertyById(activityDeclarationForPostDto.getActivitySubSector(), activitySubSectors,
                activityDeclaration::setActivitySubSector, ApplicationConstants.ACTIVITY_SUB_SECTOR_NOT_FOUND,
                ApplicationConstants.NO_ACTIVITY_SUB_SECTOR_FOUNDED_WITH_ID);

        setEntityPropertyById(activityDeclarationForPostDto.getActivityGroup(), activityGroups,
                activityDeclaration::setActivityGroup, ApplicationConstants.ACTIVITY_GROUP_NOT_FOUND,
                ApplicationConstants.NO_ACTIVITY_GROUP_FOUNDED_WITH_ID);

        setEntityPropertyById(activityDeclarationForPostDto.getActivityClass(), activityClasses,
                activityDeclaration::setActivityClass, ApplicationConstants.ACTIVITY_CLASS_NOT_FOUND,
                ApplicationConstants.NO_ACTIVITY_CLASS_FOUNDED_WITH_ID);
        activityDeclaration.setId(keyGenService.getNextKey(KeyGenType.ACTIVITY_DECLARATION_KEY, true, null));
        activityDeclaration.setInvestIdentification(investIdentification);
        activityDeclaration.setType(activityTypeEnum);
        activityDeclaration.setCreationDate(new Date());
        activityDeclaration.setCreatedBy(user);
        return activityDeclaration;
    }

    private ActivitySupport prepareActivitySupport(ActivitySupportForPostDto activitySupportForPostDto, List<ActivitySupportType> activitySupportTypes,
                                                   List<Authority> authorities, InvestIdentification investIdentification, User user,
                                                   ActivityTypeEnum activityTypeEnum) {
        ActivitySupport activitySupport = new ActivitySupport();
        setEntityPropertyById(activitySupportForPostDto.getTypeActivitySupport(), activitySupportTypes,
                activitySupport::setTypeActivitySupport, ApplicationConstants.ACTIVITY_SUPPORT_TYPE_NOT_FOUND,
                ApplicationConstants.NO_ACTIVITY_SUPPORT_TYPE_FOUNDED_WITH_ID);

        setEntityPropertyById(activitySupportForPostDto.getIssuingAuthority(), authorities,
                activitySupport::setIssuingAuthority, ApplicationConstants.AUTHORITY_NOT_FOUND,
                ApplicationConstants.NO_AUTHORITY_FOUNDED_WITH_ID);
        activitySupport.setId(keyGenService.getNextKey(KeyGenType.ACTIVITY_SUPPORT_KEY, true, null));
        activitySupport.setInvestIdentification(investIdentification);
        activitySupport.setActivityType(activityTypeEnum);
        activitySupport.setCreationDate(new Date());
        activitySupport.setCreatedBy(user);
        return activitySupport;
    }

    private <T extends Identifiable> void setEntityPropertyById(String entityId,
                                                                List<T> entities,
                                                                Consumer<T> propertySetter,
                                                                String msgHeader,
                                                                String msgBody) {
        Optional<T> entity = entities.stream()
                .filter(e -> e.getId().equals(entityId))
                .findFirst();
        if (entity.isPresent()) {
            propertySetter.accept(entity.get());
        } else {
            throw new DataNotFoundException(msgHeader, msgBody + entityId);
        }
    }

}
