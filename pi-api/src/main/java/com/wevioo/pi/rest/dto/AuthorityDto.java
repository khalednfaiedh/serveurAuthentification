package com.wevioo.pi.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDto {

    /**
     * id
     */
    private String id;
    /**
     * label
     */
    private  String label;
    /**
     * code
     */
    private String code;

}
