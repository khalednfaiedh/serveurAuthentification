package com.wevioo.pi.domain.entity.request.referential;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity representing Piece  .
 */
@Entity
@Table(name = "PI016_PIECE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Piece implements Serializable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 1234567890L;

    /**
     * ID of the piece category.
     */
    @Id
    @Column(name = "PI016_ID", nullable = false, updatable = false)
    private String id;

    /**
     * Code of the piece category.
     */
    @Column(name = "PI016_CODE")
    private String code;

    /**
     * French label of the piece category.
     */
    @Column(name = "PI016_LABEL_FR")
    private String labelFr;

    /**
     * English label of the piece category.
     */
    @Column(name = "PI016_LABEL_EN")
    private String labelEn;

    @ManyToOne
    @JoinColumn(name = "PI016_PIECE_CATEGORY_ID")
    private PieceCategory pieceCategory;
}
