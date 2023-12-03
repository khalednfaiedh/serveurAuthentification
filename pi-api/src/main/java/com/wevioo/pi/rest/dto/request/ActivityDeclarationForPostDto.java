package com.wevioo.pi.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDeclarationForPostDto implements Serializable {

    /**
     * The serial version ID
     */
    private static final long serialVersionUID = -9185799692883074106L;

    /**
     * sector of activity declaration.
     */
    private String activitySector;

    /**
     * subSector of activity declaration.
     */
    private String activitySubSector;

    /**
     * group of activity declaration.
     */
    private String activityGroup;

    /**
     * class of activity declaration.
     */
    private String activityClass;


}
