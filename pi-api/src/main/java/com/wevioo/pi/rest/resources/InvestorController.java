package com.wevioo.pi.rest.resources;

import com.wevioo.pi.rest.dto.InvestorForGetDto;
import com.wevioo.pi.rest.dto.InvestorForGetListDto;
import com.wevioo.pi.rest.dto.InvestorForPostDto;
import com.wevioo.pi.rest.dto.InvestorForPutDto;
import com.wevioo.pi.rest.dto.InvestorForSelfPutDto;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import com.wevioo.pi.service.InvestorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "/investors", produces = "application/json;charset=UTF-8")
public class InvestorController {

    /**
     * Injected bean {@link com.wevioo.pi.service.InvestorService}
     */
    @Autowired
    InvestorService investorService;


    @GetMapping("/current")
    public InvestorForGetDto getInvestor() {
        return investorService.getInvestor();
    }

    @GetMapping()
    public PaginatedResponse<InvestorForGetListDto> getInvestors(
            @RequestParam(name = "page"  , required = false) Integer  page,
            @RequestParam(name = "size"  , required = false) Integer size,
            Sort sort) {
        log.info("start get investors");
        return investorService.getInvestorsList( page , size , sort);
    }

    @PutMapping()
    public InvestorForGetDto updateInvestor(@RequestBody @Valid InvestorForSelfPutDto investorForSelfPutDto,
                                                            BindingResult result){
        return investorService.updateInvestor(investorForSelfPutDto,result);
    }

    @GetMapping("/{id}/bct")
    public InvestorForPutDto getInvestorForBct(@PathVariable String id) {
        log.info("start get investor by id");
        return investorService.getInvestorById(id);
    }


    @PutMapping("/{id}/bct")
    public InvestorForGetDto updateInvestorForBct(@PathVariable String id, @RequestBody @Valid InvestorForPutDto investorForPutDto,
                                                            BindingResult result){
        investorForPutDto.setId(id);
        return investorService.updateInvestorForBct(id,investorForPutDto,result);
    }

    /**
     *
     * @param investorForPostDto
     * @param powerOfAttorney     MultipartFile
     * @return InvestorForGetDto
     * @throws IOException custom exception
     */
    @PostMapping(consumes = "multipart/form-data")
    public InvestorForGetDto registerInvestor(@RequestPart("investorForPostDto") String investorForPostDto,
                                                            @RequestPart(value = "powerOfAttorney", required = false) MultipartFile powerOfAttorney) throws IOException {
        return investorService.saveInvestor(investorForPostDto,powerOfAttorney);
    }

    /**
     *
     * @param investorForPostDto
     * @return InvestorForGetDto
     */
    @PostMapping("/create")
    public InvestorForGetDto createInvestor(@RequestBody InvestorForPostDto investorForPostDto,
                                                              BindingResult result){
        return investorService.createInvestor(investorForPostDto,result);
    }
}
