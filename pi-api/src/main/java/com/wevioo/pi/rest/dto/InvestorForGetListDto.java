package com.wevioo.pi.rest.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class InvestorForGetListDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4751568478447203565L;

    /**
     * Investor's id
     */
    private String id;

    /**
     * Investor type
     */
    private String investorType;

    /**
     * Investor's fullName
     */
    private String fullName;

    /**
     * Investor's creationDate
     */
    private Date creationDate;

}
