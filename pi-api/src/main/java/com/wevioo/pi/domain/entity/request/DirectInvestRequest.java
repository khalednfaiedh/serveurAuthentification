package com.wevioo.pi.domain.entity.request;

import com.wevioo.pi.domain.entity.account.Representative;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.entity.referential.Agency;
import com.wevioo.pi.domain.entity.referential.Bank;
import com.wevioo.pi.domain.enumeration.DepositType;
import com.wevioo.pi.domain.enumeration.DirectInvestStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *  Direct Invest Request Entity
 */
@Entity
@Table(name = "PI012_DIRECT_INVEST_REQUEST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DirectInvestRequest  implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 7732861602930366311L;

    /**
     * ID
     */
    @Id
    @Column(name = "PI012_ID" , nullable = false , updatable = false)
    private String id;
    /**
     * deposit Type
     */
    @Column(name = "PI012_DEPOSIT_TYPE")
    @Enumerated(EnumType.STRING)
    private DepositType depositType;

    /**
     * status
     */
    @Column(name = "PI012_STATUS")
    @Enumerated(EnumType.STRING)
    private DirectInvestStatusEnum status;



    /**
     * Direct Invest Request's Bank.
     */
    @ManyToOne
    @JoinColumn(name = "PI012_BANK_ID", nullable = true, insertable = false, updatable = false)
    private Bank bank;

    /**
     * Direct Invest Request's Agency.
     */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name =  "PI012_AGENCY_ID", referencedColumnName = "GR138AG", nullable = true),
            @JoinColumn(name =  "PI012_BANK_ID", referencedColumnName = "GR028BQ", nullable = true)
    })
    private Agency agency;


    /**
     * Creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI012_CREATION_DATETIME", nullable = false)
    private Date creationDate;

    /**
     * Created by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI012_CREATED_BY")
    private User createdBy;

    /**
     * Modification date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PI012_MODIFICATON_DATETIME", nullable = true)
    private Date modificationDate;


    /**
     * Modified by.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PI012_MODIFIED_BY", nullable = true)
    private User modifiedBy;

    @OneToMany(mappedBy = "directInvestRequest")
    private List<ImportationPiece> importationPieces;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PI012_INVEST_IDENTIFICATION_IDFK")
    private InvestIdentification investIdentification;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PI012_PARTICIPATION_IDENTIFICATION_IDFK")
    private  ParticipationIdentification  participationIdentification;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PI012_CURRENCY_FINANCING_IDFK")
    private   CurrencyFinancing   currencyFinancing;

}
