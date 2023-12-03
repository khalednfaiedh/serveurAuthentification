package com.wevioo.pi.service;

import com.wevioo.pi.domain.entity.referential.Country;
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

import java.util.List;

public interface ReferentialService {

    /**
     * Load all countries
     *
     * @return list of CountryDto
     */
    List<CountryDto> getAllCountries();
    /**
     * Retrieves a list of countries by their IDs.
     *
     * @param ids The list of country IDs to retrieve
     * @return List of Country objects corresponding to the provided IDs
     */
    List<Country> findByIdIn(List<String> ids);
    /**
     * Retrieves a list of agencies associated with a specific bank.
     *
     * @param bankId The unique identifier of the bank
     * @return List of AgencyDto objects associated with the specified bankId
     */
    List<AgencyDto> getAgenciesByBankId(String bankId);
    /**
     * Retrieves a list of all banks.
     *
     * @return List of BankDto objects representing all available banks
     */
    List<BankDto> getAllBanks();

    List<LegalFormDto> getAllLegalForms();

    List<ActivitySectorDto> getAlActivitySectors();

    List<ActivitySubSectorDto> getAllActivitySubSectors();

    List<ActivityGroupDto> getAllActivityGroups();

    List<ActivityClassDto> getAllActivityClasses();

    List<ActivitySupportTypeDto> getAllActivitySupportTypes();

    List<AuthorityDto> getAllAuthorities();
}
