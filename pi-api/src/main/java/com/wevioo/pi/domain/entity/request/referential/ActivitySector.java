package com.wevioo.pi.domain.entity.request.referential;

import com.wevioo.pi.mapper.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PI018_ACTIVITY_SECTOR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySector implements Serializable, Identifiable {

    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 12345647814L;

    /**
     * ID of the activity sector.
     */
    @Id
    @Column(name = "PI018_ID", nullable = false, updatable = false)
    private String id;

    /**
     * Code of the Activity Sector.
     */
    @Column(name = "PI018_CODE")
    private String code;

    /**
     * label of the Activity Sector.
     */
    @Column(name = "PI018_LABEL")
    private String label;

    @OneToMany(mappedBy = "activitySector", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActivitySubSector> subSectors;

}
