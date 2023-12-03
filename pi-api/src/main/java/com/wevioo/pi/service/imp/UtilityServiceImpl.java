package com.wevioo.pi.service.imp;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.exception.BadRequestException;
import com.wevioo.pi.exception.UnauthorizedException;
import com.wevioo.pi.repository.UserRepository;
import com.wevioo.pi.service.UtilityService;
import com.wevioo.pi.utility.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *  UtilityServiceImpl
 */

@Service
public class UtilityServiceImpl  implements UtilityService {

    /**
     * Injected bean {@link SecurityUtils}
     */
    @Autowired
    private SecurityUtils securityUtils;

    /**
     * Injected bean {@link UserService}
     */
    @Autowired
    private UserRepository userRepository;


    /**
     * createPageable
     * @param page  page
     * @param pageSize pageSize
     * @param sort sort
     * @return Pageable
     */
    @Override
    public Pageable createPageable(Integer page, Integer pageSize, Sort sort) {
        Pageable pageable = null;
        if (page != null && pageSize != null) {
            if (pageSize < 1 || page < 1)
                throw new BadRequestException(ApplicationConstants.ERROR_INVALID_PAGE_OR_PAGE_CAPACITY_NUMBER);
            pageable = PageRequest.of(page - 1, pageSize, sort);
        } else {
            pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);
        }
        return pageable;
    }

    @Override
    public void isAuthorized(List<UserTypeEnum> userTypeList) {
        String userId = securityUtils.getCurrentUserId();
        Optional<User> user = userRepository.findById(userId);
        if(!(user.isPresent() && userTypeList.contains(user.get().getUserType()))) {
            throw new UnauthorizedException(ApplicationConstants.ERROR_UNAUTHORIZED_REQUEST);
        }
    }


    /**
     * @param sort sort
     * @param  direction  direction
     * @param defaultProperties defaultProperties
     * @return Sort
     */
    @Override
    public  Sort sortingCriteria(Sort sort , Sort.Direction direction , String  defaultProperties){
        return Sort.unsorted().equals(sort) || sort == null
                ? Sort.by(direction, defaultProperties)
                : sort;
    }
}
