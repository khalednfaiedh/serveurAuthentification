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
@Table(name = "PI020_ACTIVITY_GROUP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityGroup implements Serializable, Identifiable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 12345749814L;

    /**
     * ID of the activity group.
     */
    @Id
    @Column(name = "PI020_ID", nullable = false, updatable = false)
    private String id;

    /**
     * Code of the Activity group.
     */
    @Column(name = "PI020_CODE")
    private String code;

    /**
     * label of the Activity group.
     */
    @Column(name = "PI020_LABEL")
    private String label;
}
