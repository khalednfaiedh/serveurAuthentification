package com.wevioo.pi.service.imp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wevioo.pi.business.email.EmailDto;
import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.Attachment;
import com.wevioo.pi.domain.entity.account.Banker;
import com.wevioo.pi.domain.entity.account.BctAgent;
import com.wevioo.pi.domain.entity.account.Investor;
import com.wevioo.pi.domain.entity.account.Representative;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.referential.Country;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.domain.enumeration.LinkEnum;
import com.wevioo.pi.domain.enumeration.PersonTypeEnum;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.exception.AlreadyExistException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.exception.UnauthorizedException;
import com.wevioo.pi.mapper.InvestorMapper;
import com.wevioo.pi.mapper.RepresentativeMapper;
import com.wevioo.pi.repository.CountryRepository;
import com.wevioo.pi.repository.InvestorRepository;
import com.wevioo.pi.repository.AttachmentsRepository;
import com.wevioo.pi.repository.RepresentativeRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.BctAgentForGetAllDto;
import com.wevioo.pi.rest.dto.InvestorForGetDto;
import com.wevioo.pi.rest.dto.InvestorForGetListDto;
import com.wevioo.pi.rest.dto.InvestorForPostDto;
import com.wevioo.pi.repository.ActivationLinkRepository;
import com.wevioo.pi.domain.entity.account.ActivationLink;
import com.wevioo.pi.rest.dto.InvestorForPutDto;
import com.wevioo.pi.rest.dto.InvestorForSelfPutDto;
import com.wevioo.pi.rest.dto.RepresentativeForPostDto;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import com.wevioo.pi.service.EmailService;
import com.wevioo.pi.service.InvestorService;
import com.wevioo.pi.service.KeyGenService;
import com.wevioo.pi.service.ReferentialService;
import com.wevioo.pi.service.UtilityService;
import com.wevioo.pi.utility.CommonUtilities;
import com.wevioo.pi.utility.SecurityUtils;
import com.wevioo.pi.validation.CommonsValidation;
import com.wevioo.pi.validation.InvestorValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InvestorServiceImpl implements InvestorService {

    @Autowired
    ActivationLinkRepository activationLinkRepository;

    /**
     * Injected bean {@link InvestorRepository}
     */
    @Autowired
    InvestorRepository investorRepository;

    /**
     * Injected bean {@link RepresentativeRepository}
     */
    @Autowired
    RepresentativeRepository representativeRepository;

    /**
     * Injected bean {@link AttachmentsRepository}
     */
    @Autowired
    AttachmentsRepository attachmentsRepository;

    /**
     * Injected bean {@link UserService}
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Injected bean {@link EmailService}
     */
    @Autowired
    EmailService emailService;

    /**
     * Injected bean {@link CountryRepository}
     */
    @Autowired
    private CountryRepository countryRepository;

    /**
     * Injected bean {@link ReferentialService}
     */
    @Autowired
    ReferentialService referentialService;

    /**
     * Injected bean {@link InvestorMapper}
     */
    @Autowired
    InvestorMapper investorMapper;

    /**
     * Injected bean {@link KeyGenService}
     */
    @Autowired
    private KeyGenService keyGenService;

    /**
     * Inject {@link ObjectMapper} bean
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Injected bean {@link RepresentativeMapper}
     */
    @Autowired
    RepresentativeMapper representativeMapper;

    /**
     * Injected bean {@link SecurityUtils}
     */
    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private MessageSource messageSource;

    /**
     * Injected bean {@link UtilityService}
     */
    @Autowired
    private UtilityService utilityService;

    /**
     * Inject {@link CommonService} bean
     */
    @Autowired
    private CommonService commonService;

    @Value("${otp-code-expiration-time}")
    private int otpCodeExpiration;

    @Value("${account-activation-url}")
    private String accountActivationUrl;

    /**
     * Inject {@link PasswordEncoder} bean
     */
    @Autowired
    private PasswordEncoder encoder;



    @Override
    public InvestorForGetDto saveInvestor(String paramInvestorForPostDto, MultipartFile powerOfAttorney)
           throws IOException {
        log.info("Start saving investor");
        Investor investor;
        InvestorForPostDto investorForPostDto = objectMapper.readValue(paramInvestorForPostDto,
                InvestorForPostDto.class);
        BindingResult bindingResult = new BeanPropertyBindingResult(investorForPostDto, "investorForPostDto");

        countryRepository.findById(investorForPostDto.getCountryOfResidency())
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.COUNTRY_NOT_FOUND,
                        ApplicationConstants.NO_COUNTRY_FOUNDED_WITH_ID));

        InvestorValidator.validatePost(investorForPostDto, bindingResult);
       List<Country> countries = referentialService.findByIdIn(Arrays.asList(investorForPostDto.getNationality(), investorForPostDto.getCountryOfResidency()));

        validateCountries(investorForPostDto.getNationality(), investorForPostDto.getCountryOfResidency(), countries);

        validateEmailExist(investorForPostDto.getEmail(), bindingResult);

        investor = investorMapper.postInvestorToInvestor(investorForPostDto);
        investor.setId(keyGenService.getNextKey(KeyGenType.USER_KEY , true , null));
        investor.setCountryOfResidency(getCountryByIdFromList(countries, investorForPostDto.getCountryOfResidency()));
        investor.setNationality(getCountryByIdFromList(countries, investorForPostDto.getNationality()));
        investor.setExpirationDate(instantToDate());
        investor.setUserType(UserTypeEnum.INVESTOR);
        investor.setPassword(encoder.encode(investorForPostDto.getPassword()));
        investor.setIsActive(false);
        if (investorForPostDto.isHasRepresentative()) {
            log.info("Start saving representative");
            RepresentativeForPostDto representativeForPostDto = investorForPostDto.getRepresentative();
            if (representativeForPostDto != null) {
                countryRepository.findById(representativeForPostDto.getCountryOfResidency())
                        .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.COUNTRY_NOT_FOUND,
                                ApplicationConstants.NO_COUNTRY_FOUNDED_WITH_ID));

                InvestorValidator.validatePost(investorForPostDto, bindingResult);
                List<Country> repCountries = referentialService.findByIdIn(Arrays.asList(representativeForPostDto.getNationality(), representativeForPostDto.getCountryOfResidency()));

                validateCountries(representativeForPostDto.getNationality(), representativeForPostDto.getCountryOfResidency(), repCountries);

                validateEmailExist(representativeForPostDto.getEmail(), bindingResult);

                Representative representative = representativeMapper.postRepresentativeToRepresentative(representativeForPostDto);
                representative.setId(keyGenService.getNextKey(KeyGenType.USER_KEY , true , null));
                representative.setCountryOfResidency(getCountryByIdFromList(repCountries, representativeForPostDto.getCountryOfResidency()));
                representative.setNationality(getCountryByIdFromList(repCountries, representativeForPostDto.getNationality()));
                representative = representativeRepository.save(representative);
                investor.setRepresentative(representative);
                List<Attachment> attachments = commonService.createAttachmentList(powerOfAttorney,investor,UserTypeEnum.INVESTOR);
                investor.setAttachments(attachments);
            }
        }

        investor = investorRepository.save(investor);
        ActivationLink activationLink = generateActivationLink(investor, LinkEnum.ACCOUNT_CREATION);

        String emailSubject = messageSource.getMessage("link-activation-subject", null,
                new Locale(investorForPostDto.getLanguage().toString())) + " PI";
        String emailBody = messageSource.getMessage("link-activation-body", null,
                new Locale(investorForPostDto.getLanguage().toString())) + "\n\n" + "[ "
                + accountActivationUrl +"?key=" + activationLink.getActivationKey() + "]";

       emailService.sendHtmlMessage(new EmailDto(investorForPostDto.getEmail(), emailSubject, emailBody));
        return investorMapper.investorToInvestorForGet(investor);
    }

    @Override
    public InvestorForGetDto createInvestor(InvestorForPostDto investorForPostDto, BindingResult result) {
        String userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));
        utilityService.isAuthorized(ApplicationConstants.USER_TYPE_BANKER);
        Investor investor;
        countryRepository.findById(investorForPostDto.getCountryOfResidency())
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.COUNTRY_NOT_FOUND,
                        ApplicationConstants.NO_COUNTRY_FOUNDED_WITH_ID));

        InvestorValidator.validatePost(investorForPostDto, result);
        List<Country> countries = referentialService.findByIdIn(Arrays.asList(investorForPostDto.getNationality(), investorForPostDto.getCountryOfResidency()));

        validateCountries(investorForPostDto.getNationality(), investorForPostDto.getCountryOfResidency(), countries);

        validateEmailExist(investorForPostDto.getEmail(), result);

        investor = investorMapper.postInvestorToInvestor(investorForPostDto);
        investor.setId(keyGenService.getNextKey(KeyGenType.USER_KEY , true , null));
        investor.setCountryOfResidency(getCountryByIdFromList(countries, investorForPostDto.getCountryOfResidency()));
        investor.setNationality(getCountryByIdFromList(countries, investorForPostDto.getNationality()));
        investor.setExpirationDate(instantToDate());
        investor.setCreatedBy(user);
        investor.setUserType(UserTypeEnum.INVESTOR);
        investor.setPassword(encoder.encode(investorForPostDto.getPassword()));
        investor.setIsActive(true);
        investor = investorRepository.save(investor);
        ActivationLink activationLink = generateActivationLink(investor, LinkEnum.ACCOUNT_CREATION);

        String emailSubject = messageSource.getMessage("link-activation-subject", null,
                new Locale(investorForPostDto.getLanguage().toString())) + " PI";
        String emailBody = messageSource.getMessage("link-activation-body", null,
                new Locale(investorForPostDto.getLanguage().toString())) + "\n\n" + "[ "
                + "?key=" + activationLink.getActivationKey() + "]";

        emailService.sendHtmlMessage(new EmailDto(investorForPostDto.getEmail(), emailSubject, emailBody));
        return investorMapper.investorToInvestorForGet(investor);
    }

    @Override
    public InvestorForGetDto getInvestor() {
        String idUser = securityUtils.getCurrentUserId();

        Optional<Investor> investor = investorRepository.findById(idUser);
        if (!investor.isPresent()) {
            throw new DataNotFoundException(ApplicationConstants.INVESTOR_NOT_FOUND_WITH_ID,
                    ApplicationConstants.NO_INVESTOR_FOUNDED_WITH_ID + idUser);
        }
        return investorMapper.investorToInvestorForGet(investor.get());
    }

    @Override
    public PaginatedResponse<InvestorForGetListDto> getInvestorsList(Integer page, Integer size, Sort sort) {
        String userId = securityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        ApplicationConstants.USER_NOT_FOUND + userId));
        utilityService.isAuthorized(ApplicationConstants.USER_TYPE_ADMIN_BCT_BANKER);
        Sort sortingCriteria = utilityService.sortingCriteria(sort , Sort.Direction.DESC , ApplicationConstants.CREATION_DATE);
        Pageable pageable = utilityService.createPageable(page != null ? page : 1,  size != null ?  size : 10,
                sortingCriteria);
        PaginatedResponse<InvestorForGetListDto> response = new PaginatedResponse<>();
        Page<Investor> investorsPage;
        switch (user.getUserType()) {
            case BCT_ADMIN:
                investorsPage = investorRepository.findAll(pageable);
                break;
            case BANKER:
                investorsPage = investorRepository.findAllInvestorsForBankerBySearch(userId, pageable);
                break;
            default:
                throw new UnauthorizedException("403");

        }
        response.setTotalElement(investorsPage.getTotalElements());
        response.setTotalPage(investorsPage.getTotalPages());
        response.setPageSize(investorsPage.getSize());
        response.setPage(investorsPage.getNumber());
        response.setContent(investorsPage.getContent().stream().map(this::buildInvestorListDto).collect(Collectors.toList()));
        return  response;
    }

    @Override
    public InvestorForPutDto getInvestorById(String id) {
        utilityService.isAuthorized(ApplicationConstants.USER_TYPE_ADMIN_BCT_BANKER);
        Optional<Investor> investor = investorRepository.findById(id);
        if (investor.isEmpty()) {
            throw new DataNotFoundException(ApplicationConstants.INVESTOR_NOT_FOUND_WITH_ID,
                    "Investor not found  with id: " + id);
        }
        return investorMapper.investorToInvestorForPut(investor.get());
    }

    @Override
    public InvestorForGetDto updateInvestorForBct(String id, InvestorForPutDto investorForPutDto, BindingResult result) {
        utilityService.isAuthorized(ApplicationConstants.USER_TYPE_ADMIN_BCT_BANKER);
        Optional<Investor> existingInvestor = investorRepository.findById(id);
        if (!existingInvestor.isPresent()) {
            throw new DataNotFoundException(ApplicationConstants.INVESTOR_NOT_FOUND_WITH_ID,
                    ApplicationConstants.NO_INVESTOR_FOUNDED_WITH_ID + id);
        }
        Investor investor = existingInvestor.get();
        if (!investor.getLogin().equals(investorForPutDto.getEmail())) {
            validateEmailExist(investorForPutDto.getEmail(), result);
        }
        validatePutInvestor(investorForPutDto, result);
        if (PersonTypeEnum.PP_NON_RESIDENT_TUNISIAN == investor.getInvestorType() ||
                PersonTypeEnum.PP_NON_RESIDENT_FOREIGN == investor.getInvestorType()) {
            investor.setFirstName(investorForPutDto.getFirstName());
            investor.setLastName(investorForPutDto.getLastName());
        } else {
            investor.setSocialReason(investorForPutDto.getSocialReason());
        }
        investor.setInvestorType(investorForPutDto.getInvestorType());
        investor.setLogin(investorForPutDto.getEmail());
        investor.setIsActive(investorForPutDto.isActive());

        return investorMapper.investorToInvestorForGet(investorRepository.save(investor));
    }

    @Override
    public InvestorForGetDto updateInvestor(InvestorForSelfPutDto investorForSelfPutDto, BindingResult result) {
        String userId = securityUtils.getCurrentUserId();
        Optional<Investor> existingInvestor = investorRepository.findById(userId);
        if (!existingInvestor.isPresent()) {
            throw new DataNotFoundException(ApplicationConstants.INVESTOR_NOT_FOUND_WITH_ID,
                    ApplicationConstants.NO_INVESTOR_FOUNDED_WITH_ID + userId);
        }
        Investor investor = existingInvestor.get();
        if (!investor.getLogin().equals(investorForSelfPutDto.getEmail())) {
            validateEmailExist(investorForSelfPutDto.getEmail(), result);
        }

        List<Country> countries = referentialService.findByIdIn(Arrays.asList(investorForSelfPutDto.getNationality(), investorForSelfPutDto.getCountryOfResidency()));

        validateCountries(investorForSelfPutDto.getNationality(), investorForSelfPutDto.getCountryOfResidency(), countries);
        CommonsValidation.validatePhoneNumber("phoneNumber", result, investorForSelfPutDto.getPhoneNumber(), 15);

        investor.setCountryOfResidency(getCountryByIdFromList(countries, investorForSelfPutDto.getCountryOfResidency()));
        investor.setNationality(getCountryByIdFromList(countries, investorForSelfPutDto.getNationality()));
        investor.setCellPhone(investorForSelfPutDto.getPhoneNumber());
        investor.setAddress(investorForSelfPutDto.getAddress());
        investor.setPassportNumber(investorForSelfPutDto.getPassportNumber());
        investor.setPassportExpirationDate(investorForSelfPutDto.getPassportExpirationDate());

        return investorMapper.investorToInvestorForGet(investorRepository.save(investor));
    }

    private static void validatePutInvestor(InvestorForPutDto investorForPutDto, BindingResult result) {
        if ((CommonUtilities.compareTo(investorForPutDto.getInvestorType().name(),
                PersonTypeEnum.PP_NON_RESIDENT_TUNISIAN.name()) ||
                CommonUtilities.compareTo(investorForPutDto.getInvestorType().name(),
                        PersonTypeEnum.PP_NON_RESIDENT_FOREIGN.name()))) {
            InvestorValidator.validateFirstAndLastName(investorForPutDto.getFirstName(), investorForPutDto.getLastName(), result);
        }
        if ((CommonUtilities.compareTo(investorForPutDto.getInvestorType().name(),
                PersonTypeEnum.PM_NON_RESIDENT_TUNISIAN.name()) ||
                CommonUtilities.compareTo(investorForPutDto.getInvestorType().name(),
                        PersonTypeEnum.PM_NON_RESIDENT_FOREIGN.name())) &&
                CommonUtilities.isNullOrEmpty(investorForPutDto.getSocialReason())) {
            result.rejectValue(ApplicationConstants.SOCIAL_REASON, ApplicationConstants.BAD_REQUEST_CODE,
                        ApplicationConstants.ERROR_MISSING_REQUIRED_DATA);
        }
    }

    private InvestorForGetListDto buildInvestorListDto (Investor investor) {
        InvestorForGetListDto investorForGetListDto = new InvestorForGetListDto();
        investorForGetListDto.setId(investor.getId());
        investorForGetListDto.setCreationDate(investor.getCreationDate());
        if (investor.getInvestorType().equals(PersonTypeEnum.PP_NON_RESIDENT_TUNISIAN) ||
                investor.getInvestorType().equals(PersonTypeEnum.PP_NON_RESIDENT_FOREIGN)) {
            investorForGetListDto.setFullName(investor.getFirstName() + " " +investor.getLastName());
        }
        if (investor.getInvestorType().equals(PersonTypeEnum.PM_NON_RESIDENT_TUNISIAN) ||
                investor.getInvestorType().equals(PersonTypeEnum.PM_NON_RESIDENT_FOREIGN)) {
            investorForGetListDto.setFullName(investor.getSocialReason());
        }
        switch (investor.getInvestorType()) {
            case PP_NON_RESIDENT_TUNISIAN:
                investorForGetListDto.setInvestorType(ApplicationConstants.PP_NON_RESIDENT_TUNISIAN_LAB);
                break;
            case PP_NON_RESIDENT_FOREIGN:
                investorForGetListDto.setInvestorType(ApplicationConstants.PP_NON_RESIDENT_FOREIGN_LAB);
                break;
            case PM_NON_RESIDENT_TUNISIAN:
                investorForGetListDto.setInvestorType(ApplicationConstants.PM_NON_RESIDENT_TUNISIAN_LAB);
                break;
            case PM_NON_RESIDENT_FOREIGN:
                investorForGetListDto.setInvestorType(ApplicationConstants.PM_NON_RESIDENT_FOREIGN_LAB);
                break;
        }
        return investorForGetListDto;
    }

    public Country getCountryByIdFromList(List<Country> list, String id) {
        return list.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    public void validateEmailExist(String email, Errors errors) {
        if (investorExistByEmail(email)) {
            errors.rejectValue("email", ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS,
                    ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS);
        }
        if (errors.hasErrors()) {
            throw new AlreadyExistException(ApplicationConstants.ERROR_EMAIL_ALREADY_EXISTS, errors);
        }
    }

    public void validateCountries(String nat1, String countryOfResidency, List<Country> countries) {

        if (!existIdInCountries(countries, nat1)) {
            throw new DataNotFoundException(ApplicationConstants.COUNTRY_NOT_FOUND,
                    "No nationality founded with id: " + nat1);
        }

        if (!existIdInCountries(countries, countryOfResidency)) {
            throw new DataNotFoundException(ApplicationConstants.COUNTRY_NOT_FOUND,
                    "No countryOfResidency founded with id: " + countryOfResidency);
        }

    }

    @Override
    public boolean investorExistByEmail(String email) {
        return userRepository.existsByLoginIgnoreCase(email);
    }

    boolean existIdInCountries(List<Country> countries, String id) {
        return countries.stream().anyMatch(c -> Objects.equals(c.getId(), id));
    }


    /**
     * Generate ActivationLink
     */
    private ActivationLink generateActivationLink(User user, LinkEnum type) {

        // get activation link
        ActivationLink activationLink = activationLinkRepository.findFirstByUserAndType(user, type);
        if (activationLink == null) {
            activationLink = new ActivationLink();
            activationLink.setUser(user);
            activationLink.setType(type);
        }

        // generate activation key
        activationLink
                .setActivationKey(RandomStringUtils.randomAlphanumeric(ApplicationConstants.ACTIVATION_CODE_LENGTH));

        activationLink.setExpirationDate(instantToDate());
        activationLink.setNumberAttempts(0);
        activationLink = activationLinkRepository.save(activationLink);
        return activationLink;

    }

    public Date instantToDate() {
        Instant now = Instant.now();
        Instant expirationTime = now.plus(otpCodeExpiration, ChronoUnit.SECONDS);
        return Date.from(expirationTime);
    }
}
