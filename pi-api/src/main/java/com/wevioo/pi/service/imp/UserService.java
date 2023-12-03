package com.wevioo.pi.service.imp;

import com.wevioo.pi.business.email.EmailDto;
import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.ActivationLink;
import com.wevioo.pi.domain.entity.account.Banker;
import com.wevioo.pi.domain.entity.account.BctAgent;
import com.wevioo.pi.domain.entity.account.Investor;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.enumeration.LinkEnum;
import com.wevioo.pi.domain.enumeration.ProfileEnum;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.mapper.IUserMapper;
import com.wevioo.pi.repository.ActivationLinkRepository;
import com.wevioo.pi.repository.BankRepository;
import com.wevioo.pi.repository.IBankerRepository;
import com.wevioo.pi.repository.IBctAgentRepository;
import com.wevioo.pi.repository.InvestorRepository;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.rest.dto.AccountSetPasswordDto;
import com.wevioo.pi.rest.dto.ForgetPasswordDto;
import com.wevioo.pi.rest.dto.ModifyPasswordDto;
import com.wevioo.pi.rest.dto.MyProfileDto;
import com.wevioo.pi.rest.dto.UserDto;
import com.wevioo.pi.service.EmailService;
import com.wevioo.pi.service.IUserService;
import com.wevioo.pi.utility.CommonUtilities;
import com.wevioo.pi.utility.SecurityUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Service
public class UserService implements IUserService {

    /**
     * Inject {@link UserRepository}
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Inject {@link MessageSource}
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Inject {@link EmailService}
     */
    @Autowired
    EmailService emailService;

    /**
     * Inject {@link ActivationLinkRepository}
     */
    @Autowired
    ActivationLinkRepository activationLinkRepository;

    /**
     * Inject {@link PasswordEncoder} bean
     */
    @Autowired
    private PasswordEncoder encoder;

    /**
     * Inject {@link SecurityUtils} bean
     */
    @Autowired
    private SecurityUtils securityUtils;

    @Value("${reset-password-url}")
    private String resetPasswordUrl;

    @Value("${reset-password-url-back}")
    private String resetPasswordUrlBack;

    @Value("${otp-code-expiration-time}")
    private int otpCodeExpiration;
    /**
     *   Inject {@link IUserMapper} bean
     */
    @Autowired
    private IUserMapper iUserMapper;



    /**
     * Inject {@link BankRepository} bean
     */
    @Autowired
    IBankerRepository bankerRepository;
    /**
     * Inject {@link IBctAgentRepository} bean
     */
    @Autowired
    IBctAgentRepository  bctAgentRepository;
    /**
     * Inject {@link InvestorRepository} bean
     */
    @Autowired
    InvestorRepository  investorRepository;

    /**
     *
     * @param forgetPasswordDto
     */
    public void sendResetMail(ForgetPasswordDto forgetPasswordDto) {

        User user = userRepository.findByLoginIgnoreCaseAndIsActive(forgetPasswordDto.getEmail(), Boolean.TRUE);
        if (user == null) {
            throw new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                    "No user found with email : " + forgetPasswordDto.getEmail());
        }


        ActivationLink activationLink = generateActivationLink(user, LinkEnum.PASSWORD_FORGOT);


        String url = user.getUserType() == UserTypeEnum.BCT_AGENT ? resetPasswordUrlBack: resetPasswordUrl;
        String emailSubject = messageSource.getMessage("forget-password-subject", null, Locale.getDefault());
        String emailBody = messageSource.getMessage("forget-password-body", null, Locale.getDefault()) + "\n\n" + "[ "
                + url + "?key=" + activationLink.getActivationKey() + "]";

        emailService.sendHtmlMessage(new EmailDto(user.getLogin(), emailSubject, emailBody));
    }

    /**
     *
     * @param accountSetPasswordDto
     */
    public void resetPassword(AccountSetPasswordDto accountSetPasswordDto, BindingResult result) {

        if (!CommonUtilities.isValidPassword(accountSetPasswordDto.getPassword())) {
            result.rejectValue("password", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_INVALID_PASSWORD_FORMAT);
            throw new BadRequestException("400", result);
        }

        ActivationLink activationLink = activationLinkRepository
                .findFirstByActivationKey(accountSetPasswordDto.getActivationKey());
        if (activationLink == null) {
            throw new DataNotFoundException(ApplicationConstants.ERROR_LINK_NOT_FOUND,
                    "No Link found with id: " + accountSetPasswordDto.getActivationKey());
        }
        User user = activationLink.getUser();

        user.setPassword(encoder.encode(accountSetPasswordDto.getPassword()));
        userRepository.save(user);
        activationLinkRepository.delete(activationLink);

    }


    /**
     *
     * @param activationKey
     */
    @Transactional
    public void checkActivationLink(String activationKey) {
        ActivationLink activationLink = activationLinkRepository.findFirstByActivationKey(activationKey);
        if (activationLink == null) {
            throw new DataNotFoundException(ApplicationConstants.ERROR_LINK_NOT_FOUND,
                    "No Link found with id: " + activationKey);
        }
        User user = activationLink.getUser();
        userRepository.setEnabled(user.getId());
        activationLinkRepository.delete(activationLink);
    }

    /**
     *
     * @params modifyPasswordDto, result
     */
    public void modifyPassword(ModifyPasswordDto modifyPasswordDto, BindingResult result) {
        String idUser = securityUtils.getCurrentUserId();
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new DataNotFoundException(ApplicationConstants.ERROR_USER_NOT_FOUND,
                        "No user found with id: " + idUser));
        if (!CommonUtilities.isValidPassword(modifyPasswordDto.getNewPassword())) {
            result.rejectValue("newPassword", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_INVALID_PASSWORD_FORMAT);
            throw new BadRequestException("400", result);
        }

        if (!encoder.matches(modifyPasswordDto.getOldPassword(), user.getPassword())) {
            result.rejectValue("oldPassword", ApplicationConstants.BAD_REQUEST_CODE,
                    ApplicationConstants.ERROR_MISMATCHED_PASSWORDS);
            throw new BadRequestException("400", result);
        }
        user.setPassword(encoder.encode(modifyPasswordDto.getNewPassword()));
        userRepository.save(user);
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
        if (type == LinkEnum.OTP_ACTIVATION) {
            if (UserTypeEnum.INVESTOR == user.getUserType()) {
                activationLink.setActivationKey(
                        RandomStringUtils.randomNumeric(ApplicationConstants.SECURITY_CODE_CITIZEN_LENGTH));
            } else {
                activationLink
                        .setActivationKey(RandomStringUtils.randomNumeric(ApplicationConstants.SECURITY_CODE_LENGTH));
            }

        } else {
            activationLink.setActivationKey(
                    RandomStringUtils.randomAlphanumeric(ApplicationConstants.ACTIVATION_CODE_LENGTH));
        }
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

    /**
     * find User By Login And Is Active
     * @param username  username
     * @param isActive   is active
     * @return UserDto
     */
    @Override
    public UserDto findUserByLoginAndIsActive(String username, Boolean isActive) {

        User user = userRepository.findByLoginIgnoreCaseAndIsActive(username, isActive);

        if(user == null){
            throw  new DataNotFoundException(ApplicationConstants.USER_NOT_FOUND , "user not found with username :" +username);
        }
        switch (user.getUserType()) {
            case BANKER:
            case ADMIN_BANKER:
                user.setProfile(ProfileEnum.BANKER);
            return iUserMapper.toDto(user);

            case BCT_AGENT:
            case BCT_ADMIN:
                user.setProfile(ProfileEnum.BCT);
                return iUserMapper.toDto(user);

            case INVESTOR:
                user.setProfile(ProfileEnum.INVESTOR);
                return  iUserMapper.toDto(user);
            default:
                return  iUserMapper.toDto(user);
        }
    }
    /**
     * @return MyProfileDto   my user profile
     */
    @Override
    public MyProfileDto getProfile() {
        Map<String, Object> userToken = securityUtils.getCurrentUserInfos();
        if (userToken != null) {
            String idUser = userToken.get("id").toString();
            String type = (String) userToken.get("userType");
            switch (UserTypeEnum.valueOf(type)) {

                case BANKER:
                case ADMIN_BANKER:
                    Banker banker = bankerRepository.findById(idUser).orElseThrow(
                            ()-> new DataNotFoundException(ApplicationConstants.BANK_NOT_FOUND , "Banker not found with id : "+idUser)
                    );
                    return  new MyProfileDto(banker.getId(), banker.getFirstName(), banker.getLastName(), banker.getLogin(),
                            banker.getUserType() , banker.getIsAdmin());

                case BCT_ADMIN:
                case BCT_AGENT:
                    BctAgent bctAgent =  bctAgentRepository.findById(idUser).orElseThrow(
                            ()-> new DataNotFoundException(ApplicationConstants. USER_NOT_FOUND , " BCT  not found with id : "+idUser)
                    );

                    return  new MyProfileDto(bctAgent.getId(), bctAgent.getFirstName(), bctAgent.getLastName(), bctAgent.getLogin(),
                            bctAgent.getUserType() , bctAgent.getIsAdmin());

                case INVESTOR:
                    Investor investor =   investorRepository.findById(idUser).orElseThrow(
                            ()-> new DataNotFoundException(ApplicationConstants. USER_NOT_FOUND , "  Investor   not found with id : "+idUser)
                    );

                    return  new MyProfileDto(investor.getId(), investor.getFirstName(), investor.getLastName(), investor.getLogin(),
                            investor.getUserType() , investor.getSocialReason(), investor.getInvestorType() );

                default:
                    break;
            }
        }
        return null;
    }
}
