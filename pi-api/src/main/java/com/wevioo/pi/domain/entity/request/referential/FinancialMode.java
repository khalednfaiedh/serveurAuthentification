package com.wevioo.pi.domain.entity.request.referential;

import com.wevioo.pi.mapper.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity representing Piece Category.
 */
@Entity
@Table(name = "PI017_FINANCIAL_MODE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinancialMode implements Serializable, Identifiable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 1234567890L;

    /**
     * ID of the Financial Mode.
     */
    @Id
    @Column(name = "PI017_ID", nullable = false, updatable = false)
    private String id;

    /**
     * Code of the Financial Mode.
     */
    @Column(name = "PI017_CODE")
    private String code;

    /**
     * French label of the Financial Mode.
     */
    @Column(name = "PI017_LABEL")
    private String label;

}
