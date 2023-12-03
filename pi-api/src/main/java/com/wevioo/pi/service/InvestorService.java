package com.wevioo.pi.service;

import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.exception.UnauthorizedException;
import com.wevioo.pi.rest.dto.InvestorForGetDto;
import com.wevioo.pi.rest.dto.InvestorForGetListDto;
import com.wevioo.pi.rest.dto.InvestorForPostDto;
import com.wevioo.pi.rest.dto.InvestorForPutDto;
import com.wevioo.pi.rest.dto.InvestorForSelfPutDto;
import com.wevioo.pi.rest.dto.response.PaginatedResponse;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface InvestorService {

    /**
     * Checks if an Investor with the given email exists.
     *
     * @param email The email address to check
     * @return {@code true} if an Investor with the given email exists, {@code false} otherwise
     */
    boolean investorExistByEmail(String email);


    /**
     * Saves a new Investor using the provided parameters.
     *
     * @param paramInvestorForPostDto The JSON string representing InvestorForPostDto information
     * @param powerOfAttorney         The MultipartFile representing the power of attorney file
     * @return InvestorForGetDto representing the saved Investor if successful, null otherwise
     * @throws IOException            If an I/O exception occurs during file processing
     */
    InvestorForGetDto saveInvestor(String paramInvestorForPostDto, MultipartFile powerOfAttorney)
            throws IOException;

    InvestorForGetDto createInvestor(InvestorForPostDto investorForPostDto, BindingResult result);

    /**
     * Retrieves the information of the currently authenticated Investor.
     *
     * @return InvestorForGetDto representing the authenticated Investor's information
     * @throws DataNotFoundException If no Investor is found with the authenticated user's id
     */
    InvestorForGetDto getInvestor() throws DataNotFoundException;
    /**
     * Retrieves a paginated list of Investors.
     *
     * @param page The page number (default is 1)
     * @param size The number of items per page (default is 10)
     * @param sort The sorting criteria (default is by creation date in descending order)
     * @return Page of InvestorForGetListDto representing the paginated list of Investors
     * @throws UnauthorizedException If the current user is not authorized to perform the operation
     */
    PaginatedResponse<InvestorForGetListDto> getInvestorsList(Integer page, Integer size, Sort sort);

    /**
     * Retrieves an Investor by their unique identifier.
     *
     * @param id The unique identifier of the Investor
     * @return InvestorForPutDto representing the retrieved Investor
     * @throws DataNotFoundException If no Investor is found with the given id
     */
    InvestorForPutDto getInvestorById(String id) throws DataNotFoundException;

    /**
     * Updates an existing Investor's information based on the provided InvestorForPutDto.
     *
     * @param id                The unique identifier of the existing Investor
     * @param investorForPutDto The InvestorForPutDto containing updated information
     * @param result            The BindingResult object to handle validation errors
     * @return InvestorForGetDto representing the updated Investor if successful
     * @throws DataNotFoundException If no Investor is found with the given id
     */
    InvestorForGetDto updateInvestorForBct(String id, InvestorForPutDto investorForPutDto, BindingResult result) throws DataNotFoundException;

    /**
     * Updates the information of the currently authenticated Investor based on the provided InvestorForSelfPutDto.
     *
     * @param investorForSelfPutDto The InvestorForSelfPutDto containing updated information
     * @param result                The BindingResult object to handle validation errors
     * @return InvestorForGetDto representing the updated Investor if successful
     * @throws DataNotFoundException If no Investor is found with the authenticated user's id
     */
    InvestorForGetDto updateInvestor(InvestorForSelfPutDto investorForSelfPutDto, BindingResult result) throws DataNotFoundException;


}
