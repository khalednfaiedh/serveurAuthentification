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

@Entity
@Table(name = "PI023_AUTHORITY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements Serializable, Identifiable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 123479547114L;

    /**
     * ID of the Authority.
     */
    @Id
    @Column(name = "PI023_ID", nullable = false, updatable = false)
    private String id;

    /**
     * Code of the Authority.
     */
    @Column(name = "PI023_CODE")
    private String code;

    /**
     * label of the Authority.
     */
    @Column(name = "PI023_LABEL")
    private String label;

}
