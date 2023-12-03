package com.wevioo.pi.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationIdentificationStepTwoForGetDto {
    private String idFicheDirectInvest;

    /**
     * Social capital.
     */
    private BigDecimal socialCapital;

    /**
     * Free capital.
     */
    private BigDecimal freeCapital;

    /**
     * Number of actions.
     */
    private Long numberAction;

    /**
     * Nominal value.
     */
    private BigDecimal nominalValue;

    /**
     * Date of immatriculation.
     */
    private Date immatriculationDate;

    /**
     * Participation rate.
     */
    private Float participationRate;

    /**
     * Number of parts.
     */
    private Long numberPart;

    /**
     * Contribution amount.
     */
    private BigDecimal contributionAmount;

    /**
     * Indicates if paid capital is by tranche or not.
     */
    private Boolean paidCapitalByTranche;
}
