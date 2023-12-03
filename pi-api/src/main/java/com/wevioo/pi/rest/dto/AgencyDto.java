package com.wevioo.pi.rest.dto;

import com.wevioo.pi.domain.entity.referential.AgencyId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * AgencyDto
 */
@Getter
@Setter
public class AgencyDto implements Serializable {
    /**
     * Serial Number
     */
    private static final long serialVersionUID = 7659556534619721646L;
    /**
     * AGENCY id.
     */
    private AgencyId id;

    /**
     * AGENCY's code
     */
    private String code;

    /**
     * AGENCY's label
     */
    private String label;
}
