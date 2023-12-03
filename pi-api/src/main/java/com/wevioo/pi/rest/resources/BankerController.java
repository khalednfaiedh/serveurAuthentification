package com.wevioo.pi.rest.resources;


import com.wevioo.pi.rest.dto.BankerForGetAllDto;
import com.wevioo.pi.rest.dto.BankerForGetDto;
import com.wevioo.pi.rest.dto.BankerForPostDto;
import com.wevioo.pi.rest.dto.BankerForPutDto;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import com.wevioo.pi.service.IBankerService;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for Banker
 */
@Slf4j
@RestController
@RequestMapping(value = "/bankers", produces = "application/json;charset=UTF-8")
public class BankerController {

    @Autowired
    IBankerService iBankerService;

    /**
     * Handles the HTTP POST request to save banker information.
     *
     * @param bankerDto The DTO containing banker information to be saved.
     * @param result    The binding result for validation.
     * @return BankerForGetDto the saved BankerDto in the body.
     */
    @PostMapping
    public BankerForGetDto saveBanker(@RequestBody BankerForPostDto bankerDto , BindingResult result){
        log.debug(" Start adding banker ....");
        return iBankerService.saveBanker(bankerDto , result);
    }

    /**
     * Handles the HTTP GET request to find bankers based on search criteria.
     *
     * @param page      The page number for pagination (optional).
     * @param size      The size of each page (optional).
     * @param sort      The sorting order (optional).
     * @param bankerId  The ID of the banker (optional).
     * @param bankId    The ID of the bank associated with the banker (optional).
     * @return  PaginatedResponse<BankerForGetAllDto> The response entity containing a paginated list of BankerDto objects.
     */

    @GetMapping
    public  PaginatedResponse<BankerForGetAllDto> findBySearch(
            @RequestParam(name = "page"  , required = false) Integer  page,
            @RequestParam(name = "size"  , required = false) Integer size,
            Sort sort,
            @RequestParam(name = "bankerId"  , required = false)  String    bankerId,
            @RequestParam(name = "bankId"  , required = false)  String  bankId ){
        log.info("  start search  banker .......");
        return iBankerService.findAllBySearch(page,size,sort,bankerId,bankId);
    }

    /**
     * Handles the HTTP GET request to retrieve a banker by their ID.
     *
     * @param id The ID of the banker to retrieve.
     * @return BankerDto The response entity containing the BankerDto with the specified ID.
     */
    @GetMapping("/{id}")
    BankerForGetDto findBankerById(@PathVariable String id){
            return iBankerService.findBankerById(id);
    }

    /**
     * Handles the HTTP PUT request to update a banker by their ID.
     *
     * @param id         The ID of the banker to be updated.
     * @param bankerDto  The DTO containing updated banker information.
     * @param result     The binding result for validation.
     * @return  BankerDto The response entity containing the updated BankerDto.
     */

    @PutMapping ("/{id}")
     BankerForGetDto updateBanker(@PathVariable String id ,
                                                  @RequestBody  BankerForPutDto bankerDto,
                                                 BindingResult result){
        return iBankerService.updateBanker(id , bankerDto  , result );

    }
}
