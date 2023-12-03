package com.wevioo.pi.rest.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttachementRequestDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = -3488411799290744113L;
    private Long id;
    private String fileName;
    private Integer fileSize;

}
