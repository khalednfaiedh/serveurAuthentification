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
@Table(name = "PI022_ACTIVITY_SUPPORT_TYPE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySupportType implements Serializable, Identifiable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 12347969814L;

    /**
     * ID of the activity support type.
     */
    @Id
    @Column(name = "PI022_ID", nullable = false, updatable = false)
    private String id;

    /**
     * Code of the Activity support type.
     */
    @Column(name = "PI022_CODE")
    private String code;

    /**
     * label of the Activity support type.
     */
    @Column(name = "PI022_LABEL")
    private String label;

}
