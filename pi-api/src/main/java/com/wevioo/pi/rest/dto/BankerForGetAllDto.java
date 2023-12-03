package com.wevioo.pi.rest.dto;

import com.wevioo.pi.domain.enumeration.TypeAdministrator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankerForGetAllDto {


    private String bankName;
    private String id;
    private String firstName;
    private String lastName;
    private TypeAdministrator typeAdministrator;
    private Date creationDate;

}
