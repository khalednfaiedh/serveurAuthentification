package com.wevioo.pi.domain.entity;

import com.wevioo.pi.domain.entity.account.Investor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Attachment Model DataTable
 *
 * @author shl
 *
 */
@Entity

@Table(name = "PI006T_ATTACHMENT")
@Getter
@Setter
public class Attachment implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = -7256931927252100822L;

    /**
     * Attachment's Id
     */
    @Id
    @Column(name = "PI006_ID", updatable = false, nullable = false)
    private String id;

    /**
     * Attachment's fileName
     */
    @Column(name = "PI006_FILE_NAME", nullable = false)
    private String fileName;

    /**
     * Attachment's creationDate
     */
    @Column(name = "PI006_CREATION_DATE", nullable = false)
    private Date creationDate;

    /**
     * Attachment's createdBy
     */
    @Column(name = "PI006_CREATED_BY", nullable = true)
    private String createdBy;

    /**
     * Attachment's updateDate
     */
    @Column(name = "PI006_UPDATE_DATE", nullable = true)
    private Date updateDate;


    /**
     * Attachment's investor
     */
    @ManyToOne
    @JoinColumn(name = "PI006_INVESTOR_IDFK")
    private Investor investor;

    /**
     * fileUrl
     */
    @Column(name = "PI006_FILE_PATH")
    private String fileUrl;

    /**
     * Attachment's contentType
     */

    @Column(name = "PI006_CONTENT_TYPE")
    private String contentType;

    /**
     * Attachment's File size
     */
    @Column(name = "PI006_FILE_SIZE")
    private Integer fileSize;

}