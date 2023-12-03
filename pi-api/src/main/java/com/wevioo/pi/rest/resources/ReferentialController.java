package com.wevioo.pi.rest.resources;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/referential", produces = "application/json;charset=UTF-8")
@Validated
public class ReferentialController {

    /**
     * Injected bean {@link ReferentialService}
     */
    @Autowired
    private ReferentialService referentialService;

    /**
     * Retrieves a list of all banks via HTTP GET request.
     *
     * @return List of BankDto objects representing all available banks
     */
    @GetMapping("/banks")
    public List<BankDto> getAllBanks() {
        return referentialService.getAllBanks();
    }

    /**
     * Retrieves a list of agencies associated with a specific bank via HTTP GET request.
     *
     * @param bankId The unique identifier of the bank
     * @return List of AgencyDto objects associated with the specified bankId
     */
    @GetMapping("/bank/{bankId}/agencies")
    public List<AgencyDto> getAllAgencies(@PathVariable("bankId") String bankId) {
        return referentialService.getAgenciesByBankId(bankId);
    }
    /**
     * Retrieves a list of countries.
     *
     * @return List of CountryDto
     */
    @GetMapping("/countries")
    public List<CountryDto> getAllCountries() {
        return referentialService.getAllCountries();
    }

    /**
     * Retrieves a list of legal forms.
     *
     * @return List of LegalFormDto
     */
    @GetMapping("/legalForms")
    public List<LegalFormDto> getAllLegalForms() {
        return referentialService.getAllLegalForms();
    }

    /**
     * Retrieves a list of activity sectors.
     *
     * @return List of ActivitySectorDto
     */
    @GetMapping("/activitySectors")
    public List<ActivitySectorDto> getAllActivitySectors() {
        return referentialService.getAlActivitySectors();
    }

    /**
     * Retrieves a list of activity sub sectors.
     *
     * @return List of ActivitySubSectorDto
     */
    @GetMapping("/activitySubSectors")
    public List<ActivitySubSectorDto> getAllActivitySubSectors() {
        return referentialService.getAllActivitySubSectors();
    }

    /**
     * Retrieves a list of activity groups.
     *
     * @return List of ActivityGroupDto
     */
    @GetMapping("/activityGroups")
    public List<ActivityGroupDto> getAllActivityGroups() {
        return referentialService.getAllActivityGroups();
    }

    /**
     * Retrieves a list of activity classes.
     *
     * @return List of ActivityClassDto
     */
    @GetMapping("/activityClasses")
    public List<ActivityClassDto> getAllActivityClasses() {
        return referentialService.getAllActivityClasses();
    }

    /**
     * Retrieves a list of activity support types.
     *
     * @return List of ActivitySupportTypeDto
     */
    @GetMapping("/activitySupportTypes")
    public List<ActivitySupportTypeDto> getAllActivitySupportTypes() {
        return referentialService.getAllActivitySupportTypes();
    }

    /**
     * Retrieves a list of authorities.
     *
     * @return List of AuthorityDto
     */
    @GetMapping("/authorities")
    public List<AuthorityDto> getAllAuthorities() {
        return referentialService.getAllAuthorities();
    }


}
