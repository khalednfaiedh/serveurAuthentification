package com.wevioo.pi.domain.entity.request;

import com.wevioo.pi.domain.entity.account.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PI013_PARTICIPATION_IDENTIFICATION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationIdentification implements Serializable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 1234567890L;

    /**
     * ID of the participation identification.
     */
    @Id
    @Column(name = "PI013_ID", nullable = false, updatable = false)
    private String id;

    /**
     * Social capital.
     */
    @Column(name = "PI013_SOCIAL_CAPITAL")
    private BigDecimal socialCapital;

    /**
     * Free capital.
     */
    @Column(name = "PI013_FREE_CAPITAL")
    private BigDecimal freeCapital;

    /**
     * Number of actions.
     */
    @Column(name = "PI013_NUMBER_ACTION")
    private Long numberAction;

    /**
     * Nominal value.
     */
    @Column(name = "PI013_NOMINAL_VALUE")
    private BigDecimal nominalValue;

    /**
     * Date of immatriculation.
     */
    @Column(name = "PI013_IMMATRICULATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date immatriculationDate;

    /**
     * Participation rate.
     */
    @Column(name = "PI013_PARTICIPATION_RATE")
    private Float participationRate;

    /**
     * Number of parts.
     */
    @Column(name = "PI013_INUMBER_PART")
    private Long numberPart;

    /**
     * Contribution amount.
     */
    @Column(name = "PI013_CONTRIBUTION_AMOUNT")
    private BigDecimal contributionAmount;

    /**
     * Indicates if paid capital is by tranche or not.
     */
    @Column(name = "PI013_PAID_CAPITAL_BY_TRANCHE")
    private Boolean paidCapitalByTranche;


    /**
     * Creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI013_CREATION_DATETIME", nullable = false)
    private Date creationDate;

    /**
     * Created by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI013_CREATED_BY")
    private User createdBy;

    /**
     * Modification date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI013_MODIFICATON_DATETIME", nullable = true)
    private Date modificationDate;


    /**
     * Modified by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI013_MODIFIED_BY", nullable = true)
    private User modifiedBy;

    @PrePersist
   void onCreate(){
       this.creationDate = new Date();
   }
   @PreUpdate
    void onUpdate(){
        this. modificationDate = new Date();
    }

}
