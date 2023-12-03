package com.wevioo.pi.domain.entity.request.referential;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * Entity representing Piece Category.
 */
@Entity
@Table(name = "PI015_PIECE_CATEGORY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PieceCategory implements Serializable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 1234567890L;

    /**
     * ID of the piece category.
     */
    @Id
    @Column(name = "PI015_ID", nullable = false, updatable = false)
    private String id;

    /**
     * Code of the piece category.
     */
    @Column(name = "PI015_CODE")
    private String code;

    /**
     * French label of the piece category.
     */
    @Column(name = "PI015_LABEL_FR")
    private String labelFr;

    /**
     * English label of the piece category.
     */
    @Column(name = "PI015LABEL_EN")
    private String labelEn;
    /**
     * list of pieces
     */
    @OneToMany(mappedBy = "pieceCategory")
    private List<Piece>  pieces;
}
