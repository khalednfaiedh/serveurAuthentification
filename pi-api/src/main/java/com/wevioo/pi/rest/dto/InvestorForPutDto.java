package com.wevioo.pi.rest.dto;

import com.wevioo.pi.domain.enumeration.PersonTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class InvestorForPutDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4751698478447203565L;

    /**
     * Investor's id
     */
    private String id;

    /**
     * Investor type
     */
    private PersonTypeEnum investorType;

    /**
     * Investor's firstName
     */
    private String firstName;

    /**
     * Investor's creationDate
     */
    private String lastName;

    /**
     * Investor's socialReason ( for moral person )
     */
    private String socialReason;

    /**
     * Investor's email
     */
    private String email;

    /**
     * Investor active: 1 : Yes | 0 : No
     */
    private boolean active;

}
