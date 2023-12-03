package com.wevioo.pi.domain.entity.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * Operator Model DataTable
 *
 * @author  knh
 *
 */
@Table(name = "PI004T_BCT_AGENT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BctAgent  extends  User {
    /**
     * registration Number
     */
    @Column(name = "REGISTRATION_NUMBER" )
    private String  registrationNumber;
    /**
     * General Management Assignment
     */
    @Column(name = "GENERAL_MANAGEMENT_ASSIGNMENT")
    private String generalManagementAssignment ;
    /**
     * service Assignment
     */
    @Column(name = "SERVICE_ASSIGNMENT")
    private  String serviceAssignment;
    /**
     * IS Admin
     */
    @Column(name = "IS_ADMIN")
    private Boolean isAdmin;
}
