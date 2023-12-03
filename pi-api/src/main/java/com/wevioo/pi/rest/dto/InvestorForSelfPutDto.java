package com.wevioo.pi.rest.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class InvestorForSelfPutDto implements Serializable {


    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4751698521447203565L;

    /**
     * Investor's id
     */
    private String id;

    /**
     * Investor's email
     */
    private String email;

    /**
     * Investor's address
     */
    private String address;

    /**
     * Investor's phoneNumber
     */

    private String phoneNumber;

    /**
     * Investor's Passport number
     */
    private String passportNumber;

    /**
     * Investor's Passport expiration date
     */
    private Date passportExpirationDate;

    /**
     * Investor's countryOfResidency.
     */
    private String countryOfResidency;

    /**
     * Investor's nationality.
     */
    private String nationality;





}
