package com.wevioo.pi.service.imp;


import com.wevioo.pi.business.email.EmailDto;
import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.Banker;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.referential.Bank;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.exception.UnauthorizedException;
import com.wevioo.pi.mapper.IBankerMapper;
import com.wevioo.pi.repository.BankRepository;
import com.wevioo.pi.repository.IBankerRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.BankerDto;
import com.wevioo.pi.rest.dto.BankerForGetAllDto;
import com.wevioo.pi.rest.dto.BankerForGetDto;
import com.wevioo.pi.rest.dto.BankerForPostDto;
import com.wevioo.pi.rest.dto.BankerForPutDto;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import com.wevioo.pi.service.EmailService;
import com.wevioo.pi.service.IBankerService;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.UtilityService;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.validation.BankerValidation;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class BankerServiceImp implements IBankerService {

    /**
     * Injected bean {@link IBankerMapper }
     */
    @Autowired
    IBankerMapper iBankerMapper;
    /**
     * Injected bean {@link IBankerRepository }
     */
    @Autowired
    IBankerRepository iBankerRepository;

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
     * Injected bean {@link PasswordEncoder}
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * Injected bean {@link MessageSource}
     */
    @Autowired
    private MessageSource messageSource;
    /**
     * Injected bean {@link EmailService}
     */
    @Autowired
    private EmailService emailService;
    /**
     * Injected bean {@link UtilityService}
     */
    @Autowired
    private UtilityService utilityService;
    /**
     *  Injected bean {@link KeyGenService}
     */
    @Autowired
    private KeyGenService keyGenService;

    /**
     *  Injected bean {@link BankRepository}
     */
    @Autowired
    private BankRepository  bankRepository;

    /**
     * Saves a new banker after performing various validations and processing.
     *
     * @param bankerDto The DTO (Data Transfer Object) containing banker information to be saved.
     * @param result    The binding result for validation.
     * @return BankerDto The DTO of the saved banker.
     */
    @Override
    public BankerForGetDto saveBanker(BankerForPostDto bankerDto, BindingResult result) {
        log.info("start addBanker ...... ");
        String userId = securityUtils.getCurrentUserId();
         User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));
        BankerValidation.validateBankerForPost(bankerDto, result, null);
        checkAuthority(user,bankerDto.getIsAdmin());
        validateEmailExist(bankerDto, result);
        Optional<Banker> userDeactivate = validateNumberAdminBankerAndDeactivate(bankerDto, result);
        Banker banker = iBankerMapper.toEntity(bankerDto);
        if(banker.getIsAdmin()) {
            validateBank(banker, bankerDto.getBankId());
        }
        banker.setPassword(passwordEncoder.encode(banker.getPassword()));
        String emailSubject = messageSource.getMessage("account-created", null, new Locale(bankerDto.getLanguage().toString()));
        String emailBody = messageSource.getMessage("account-credentials", null, new Locale(bankerDto.getLanguage().toString()))
                + "<h3> Email : <span style='color:black'>" +  bankerDto.getEmail() + "</span> </h3> </br>"
                + "<h3> Password : <span>" +  bankerDto.getPassword() + "</span></h3> ";
        String emailFooter = messageSource.getMessage("account-footer" , null , new Locale(bankerDto.getLanguage().toString()));
        emailBody = emailBody.concat(emailFooter);
        banker.setUserType(banker.getIsAdmin() != null && banker.getIsAdmin() ? UserTypeEnum.ADMIN_BANKER : UserTypeEnum.BANKER);
        banker.setParent(bankerDto.getIsAdmin() ? null : (Banker) user);
        banker.setCreatedBy(user);
        banker.setId(keyGenService.getNextKey(KeyGenType.USER_KEY , true , null));
        banker =  iBankerRepository.save(banker);
        updateParentBanker(userDeactivate , banker);
        emailService.sendHtmlMessage(new EmailDto( banker.getLogin(), emailSubject, emailBody));
        return iBankerMapper.toBankerForGetDto(
            banker
        );
    }

    /**
     * Retrieves a page of BankerDto objects based on search criteria, page number, size, and sorting options.
     *
     * @param page      The page number to retrieve (default is 1).
     * @param size      The size of each page (default is 10).
     * @param sort      The sorting order for the results.
     * @param bankerId  The ID of the banker to search for.
     * @param bankId    The ID of the bank associated with the banker.
     * @return PaginatedResponse<BankerForGetAllDto> A paginated list of BankerDto objects based on the search criteria.
     */
    @Override
    public PaginatedResponse<BankerForGetAllDto> findAllBySearch(Integer page, Integer size, Sort sort , String bankerId , String bankId ) {
        Sort sortingCriteria = utilityService.sortingCriteria(sort , Sort.Direction.DESC , ApplicationConstants.CREATION_DATE);
        Pageable pageable = utilityService.createPageable(page != null ? page : 1,  size != null ?  size : 10,
                sortingCriteria);
        String userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));
        utilityService.isAuthorized(ApplicationConstants.USER_TYPE_BCT_ADMIN_AND_ADMIN_BANKER);

        PaginatedResponse<BankerForGetAllDto> response = new PaginatedResponse<>();
        Page<Banker> bankersPage;

        switch (user.getUserType()) {
            case BCT_ADMIN:
                bankersPage = iBankerRepository.findAllBySearch(bankerId, bankId, Boolean.TRUE, pageable);
                break;
            case ADMIN_BANKER:
                bankersPage = iBankerRepository.findAllBySearch(bankerId, bankId,  Boolean.FALSE, pageable);
                break;
            default:
                throw new UnauthorizedException("403");
        }

        response.setTotalElement(bankersPage.getTotalElements());
        response.setTotalPage(bankersPage.getTotalPages());
        response.setPageSize(bankersPage.getSize());
        response.setPage(bankersPage.getNumber());
        response.setContent(iBankerMapper.toBankerForGetAllDto(bankersPage.getContent()));
        return response;

    }

    /**
     * Updates banker information based on the provided ID and DTO, subject to authorization.
     *
     * @param id         The ID of the banker to be updated.
     * @param bankerDto  The DTO containing updated banker information.
     * @param result     The binding result for validation.
     * @return BankerDto The DTO of the updated banker.
     */
    @Override
    public  BankerForGetDto updateBanker(String id, BankerForPutDto bankerDto, BindingResult result) {
        log.info("start update Banker ...... ");
        String userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));

        Banker  banker =  iBankerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + id));
        BankerValidation.validateBankerForUpdate(bankerDto, result ,banker.getIsAdmin());
        checkAuthority(user ,banker.getIsAdmin());
        banker.setModifiedBy(user);
        if( banker.getIsAdmin()){
            validateBank(banker, bankerDto.getBankId());
            banker.setTypeAdministrator(bankerDto.getTypeAdministrator());
            banker.setDirection(bankerDto.getDirection());
            banker.setFunction(bankerDto.getFunction());
        }else {
            banker.setSenioritySector(bankerDto.getSenioritySector());
            banker.setLogin(bankerDto.getEmail());
        }
        banker.setFirstName(bankerDto.getFirstName());
        banker.setLastName(bankerDto.getLastName());
        banker.setUniversityDegree(bankerDto.getUniversityDegree());
        banker.setCellPhone(bankerDto.getCellPhone());
        banker.setHomePhoneNumber(bankerDto.getHomePhoneNumber());
        banker.setIsActive(bankerDto.getIsActive());
        return  iBankerMapper. toBankerForGetDto(
                iBankerRepository.save(banker)
        );
    }

    /**
     * Retrieves a banker based on the provided ID.
     *
     * @param id The ID of the banker to retrieve.
     * @return BankerDto The DTO of the retrieved banker.
     * @throws DataNotFoundException If the banker with the provided ID is not found.
     */
    @Override
    public BankerForGetDto findBankerById(String id) {
        utilityService.isAuthorized(ApplicationConstants.USER_TYPE_BCT_ADMIN_AND_ADMIN_BANKER);
        Banker banker   =  iBankerRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + id));
        return   iBankerMapper.toBankerForGetDto(banker);
    }

    /**
     * Validate the existence of login
     *
     * @param bankerDto BankerBaseDto
     * @param errors    Errors
     */
    private void validateEmailExist(BankerForPostDto bankerDto, Errors errors) {
        if (userRepository.existsByLoginIgnoreCase(bankerDto.getEmail())) {
            errors.rejectValue("email", ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS,
                    ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS);
            throw new BadRequestException("400", errors);
        }

    }

    /**
     * Validate valid number   bankers in bd
     *
     * @param bankerDto BankerBaseDto
     * @param errors    Errors
     */
    private Optional< Banker> validateNumberAdminBankerAndDeactivate(BankerForPostDto bankerDto, Errors errors) {
        Optional< Banker> optionalBanker = Optional.empty();
        if ( Boolean.TRUE.equals(bankerDto.getIsAdmin())) {
            List<Banker>  bankers =  iBankerRepository.findAllByUserTypeAndAndApprovedIntermediaryId(UserTypeEnum.ADMIN_BANKER, bankerDto.getBankId());
            if (bankers.size() >= ApplicationConstants.NUMBER_USER_OF_TYPE_BANKER) {
                errors.rejectValue("isAdmin", ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.NUMBER_USER_OF_TYPE_ADMIN_BANKER_SUPERIOR);
                throw new BadRequestException("400", errors);
            }

            optionalBanker = bankers.stream().filter(p -> Boolean.TRUE.equals(p.getIsActive())).findFirst();
            if (optionalBanker.isPresent() && Boolean.TRUE.equals(bankerDto.getIsActive())) {
                optionalBanker.get().setIsActive(Boolean.FALSE);
                return Optional.of(iBankerRepository.save(optionalBanker.get()));
            }

        }

        return  optionalBanker;

    }

    /**
     *
     * @param bankerDeactivate optional of  banker Deactivate
     * @param banker banker
     */
    private void updateParentBanker(Optional<Banker>  bankerDeactivate , Banker banker) {
        if(bankerDeactivate.isPresent()) {
            List<Banker>  bankers =  iBankerRepository.findAllByParentId(bankerDeactivate.get().getId());
            if(!bankers.isEmpty()) {
                for (Banker item : bankers){
                    item.setParent(banker);
                }

            }
            iBankerRepository.saveAll(bankers);
        }
    }

    /**
     * VAlidate the existence of Bank
     *
     * @param banker Banker
     * @param id    Bank's code
     */
    private  Banker validateBank(Banker banker, String  id) {
        Bank bank = bankRepository.findById(id).orElseThrow(
                ()->    new DataNotFoundException(ApplicationConstants.BANK_NOT_FOUND, "Bank not found with id " + id)
        );
        banker.setApprovedIntermediary(bank);
        return banker;
    }

    /**
     * check Authority
     * @param user
     * @param isAdmin
     */
    private  void checkAuthority(User user ,  Boolean isAdmin ){
        switch (user.getUserType()){
            case BCT_ADMIN:
                if(Boolean.FALSE.equals(isAdmin)){
                    throw  new UnauthorizedException("403");
                }
                break;
            case ADMIN_BANKER:
                if(Boolean.TRUE.equals(isAdmin)){
                    throw  new UnauthorizedException("403");
                }
                break;
            default:   new UnauthorizedException("403");
        }
    }

}
