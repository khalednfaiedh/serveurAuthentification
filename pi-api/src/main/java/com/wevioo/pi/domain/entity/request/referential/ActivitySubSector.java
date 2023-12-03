package com.wevioo.pi.domain.entity.request.referential;

import com.wevioo.pi.mapper.Identifiable;
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

@Entity
@Table(name = "PI019_ACTIVITY_SUB_SECTOR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySubSector implements Serializable, Identifiable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 12345749814L;

    /**
     * ID of the activity SubSector.
     */
    @Id
    @Column(name = "PI019_ID", nullable = false, updatable = false)
    private String id;

    /**
     * Code of the Activity SubSector.
     */
    @Column(name = "PI019_CODE")
    private String code;

    /**
     * label of the Activity SubSector.
     */
    @Column(name = "PI019_LABEL")
    private String label;

    /**
     * label of the Activity parent sector.
     */
    @ManyToOne
    @JoinColumn(name = "PI019_ACTIVITY_SECTOR_ID", nullable = false)
    private ActivitySector activitySector;
}
