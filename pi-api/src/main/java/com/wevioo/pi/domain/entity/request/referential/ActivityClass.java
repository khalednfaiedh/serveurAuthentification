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
@Table(name = "PI021_ACTIVITY_CLASS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityClass implements Serializable, Identifiable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 12479749814L;

    /**
     * ID of the activity class.
     */
    @Id
    @Column(name = "PI021_ID", nullable = false, updatable = false)
    private String id;

    /**
     * Code of the Activity class.
     */
    @Column(name = "PI021_CODE")
    private String code;

    /**
     * label of the Activity class.
     */
    @Column(name = "PI021_LABEL")
    private String label;
}
