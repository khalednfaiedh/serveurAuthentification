package com.wevioo.pi.service.imp;

import com.wevioo.pi.domain.entity.referential.Agency;
import com.wevioo.pi.domain.entity.referential.Country;
import com.wevioo.pi.mapper.ActivityClassMapper;
import com.wevioo.pi.mapper.ActivityGroupMapper;
import com.wevioo.pi.mapper.ActivitySectorMapper;
import com.wevioo.pi.mapper.ActivitySubSectorMapper;
import com.wevioo.pi.mapper.ActivitySupportTypeMapper;
import com.wevioo.pi.mapper.AgencyMapper;
import com.wevioo.pi.mapper.AuthorityMapper;
import com.wevioo.pi.mapper.BankMapper;
import com.wevioo.pi.mapper.LegalFormMapper;
import com.wevioo.pi.mapper.ReferentialMapper;
import com.wevioo.pi.repository.ActivityClassRepository;
import com.wevioo.pi.repository.ActivityGroupRepository;
import com.wevioo.pi.repository.ActivitySectorRepository;
import com.wevioo.pi.repository.ActivitySubSectorRepository;
import com.wevioo.pi.repository.ActivitySupportTypeRepository;
import com.wevioo.pi.repository.AgencyRepository;
import com.wevioo.pi.repository.AuthorityRepository;
import com.wevioo.pi.repository.BankRepository;
import com.wevioo.pi.repository.CountryRepository;
import com.wevioo.pi.repository.LegalFormRepository;
import com.wevioo.pi.rest.dto.ActivityClassDto;
import com.wevioo.pi.rest.dto.ActivityGroupDto;
import com.wevioo.pi.rest.dto.ActivitySectorDto;
import com.wevioo.pi.rest.dto.ActivitySubSectorDto;
import com.wevioo.pi.rest.dto.ActivitySupportTypeDto;
import com.wevioo.pi.rest.dto.AgencyDto;
import com.wevioo.pi.rest.dto.AuthorityDto;
import com.wevioo.pi.rest.dto.BankDto;
import com.wevioo.pi.rest.dto.CountryDto;
import com.wevioo.pi.rest.dto.LegalFormDto;
import com.wevioo.pi.service.ReferentialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class ReferentialServiceImpl implements ReferentialService {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private ReferentialMapper referentialMapper;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private AgencyRepository agencyRepository;
    @Autowired
    private LegalFormRepository legalFormRepository;
    @Autowired
    private ActivitySectorRepository activitySectorRepository;
    @Autowired
    private ActivitySubSectorRepository activitySubSectorRepository;
    @Autowired
    private ActivityGroupRepository activityGroupRepository;
    @Autowired
    private ActivityClassRepository activityClassRepository;
    @Autowired
    private ActivitySupportTypeRepository activitySupportTypeRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    BankMapper bankMapper;
    @Autowired
    AgencyMapper agencyMapper;
    @Autowired
    LegalFormMapper legalFormMapper;
    @Autowired
    ActivitySectorMapper activitySectorMapper;
    @Autowired
    ActivitySubSectorMapper activitySubSectorMapper;
    @Autowired
    ActivityGroupMapper activityGroupMapper;
    @Autowired
    ActivityClassMapper activityClassMapper;
    @Autowired
    ActivitySupportTypeMapper activitySupportTypeMapper;
    @Autowired
    AuthorityMapper authorityMapper;


    /**
     * {@inheritDoc}
     */
    @Override
    public List<CountryDto> getAllCountries() {
        log.info("get All Countries");
        return referentialMapper.toCountryDtoList(countryRepository.findByOrderByLabelAsc());
    }

    @Override
    public List<Country> findByIdIn(List<String> ids) {
        return countryRepository.findByIdIn(ids);
    }

    /**
     * Retrieves a list of AgencyDto objects based on the provided bankId.
     *
     * @param bankId The unique identifier of the bank
     * @return List of AgencyDto objects associated with the specified bankId
     */
    @Override
    public List<AgencyDto> getAgenciesByBankId(String bankId) {
        return agencyMapper.toDto(agencyRepository.findByBankIdOrderByLabelAsc(bankId))  ;
    }
    /**
     * Retrieves a list of all banks and converts them to BankDto objects.
     *
     * @return List of BankDto objects representing all available banks
     */
    @Override
    public List<BankDto> getAllBanks() {
        return  bankMapper.toDto(bankRepository.findAll()) ;
    }

    @Override
    public List<LegalFormDto> getAllLegalForms() {
        return legalFormMapper.toDto(legalFormRepository.findAll());
    }

    @Override
    public List<ActivitySectorDto> getAlActivitySectors() {
        return activitySectorMapper.toDto(activitySectorRepository.findAll());
    }

    @Override
    public List<ActivitySubSectorDto> getAllActivitySubSectors() {
        return activitySubSectorMapper.toDto(activitySubSectorRepository.findAll());
    }

    @Override
    public List<ActivityGroupDto> getAllActivityGroups() {
        return activityGroupMapper.toDto(activityGroupRepository.findAll());
    }

    @Override
    public List<ActivityClassDto> getAllActivityClasses() {
        return activityClassMapper.toDto(activityClassRepository.findAll());
    }

    @Override
    public List<ActivitySupportTypeDto> getAllActivitySupportTypes() {
        return activitySupportTypeMapper.toDto(activitySupportTypeRepository.findAll());
    }

    @Override
    public List<AuthorityDto> getAllAuthorities() {
        return authorityMapper.toDto(authorityRepository.findAll());
    }

}
