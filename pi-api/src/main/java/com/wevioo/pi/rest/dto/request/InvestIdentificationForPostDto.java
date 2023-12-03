package com.wevioo.pi.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvestIdentificationForPostDto implements Serializable {

    /**
     * The serial version ID
     */
    private static final long serialVersionUID = -9193199587483049806L;

    /**
     * id
     */
    private String id;

    /**
     * uniqueIdentifier
     */
    private String uniqueIdentifier;

    /**
     * companyName
     */
    private String companyName;

    /**
     * legalForm
     */
    private String legalForm;

    /**
     * quoted
     */
    private boolean quoted;
    /**
     * resident
     */
    private boolean resident;
    /**
     * has secondary Activity Support ?
     */
    private boolean secondaryActivitySupport;
    /**
     * has main Activity Support?
     */
    private boolean mainActivitySupport;
    /**
     * has secondary Activity Declaration ?
     */
    private boolean secondaryActivityDeclaration;

    /**
     *  main Activity Support list
     */
    private List<ActivitySupportForPostDto> mainActivitySupportList;

    /**
     *  secondary Activity Support list
     */
    private List<ActivitySupportForPostDto> secondaryActivitySupportList;

    /**
     *  main Activity Declaration list
     */
    private List<ActivityDeclarationForPostDto> mainActivityDeclarationList;

    /**
     *  secondary Activity Declaration list
     */
    private List<ActivityDeclarationForPostDto> secondaryActivityDeclarationList;

}
