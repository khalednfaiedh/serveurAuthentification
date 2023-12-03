package com.wevioo.pi.service.imp;

import com.wevioo.pi.domain.entity.Attachment;
import com.wevioo.pi.domain.entity.account.Investor;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.repository.AttachmentsRepository;
import com.wevioo.pi.service.KeyGenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CommonService {

    @Value("${upload-path}")
    private String basePath;

    /**
     * Injected bean {@link KeyGenService}
     */
    @Autowired
    private KeyGenService keyGenService;

    /**
     * Injected bean {@link AttachmentsRepository}
     */
    @Autowired
    private AttachmentsRepository attachmentsRepository;


    /**
     * @param powerOfAattorneyFile
     * @param investor
     * @param userType
     * @return
     * @throws IOException
     */
    public List<Attachment> createAttachmentList(MultipartFile powerOfAattorneyFile,
                                                 Investor investor,
                                                 UserTypeEnum userType)
            throws IOException {

        List<Attachment> attachments = new ArrayList<>();

        Path uploadDir = null;
        if (userType != null && investor != null && investor.getId() != null) {
            uploadDir = Paths.get(basePath + File.separator + userType.toString() + File.separator + investor.getId());
        } else {
            return attachments;
        }

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        if (powerOfAattorneyFile != null) {
            String fileName = org.springframework.util.StringUtils
                    .cleanPath(powerOfAattorneyFile.getOriginalFilename());
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(powerOfAattorneyFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Files.write(filePath, powerOfAattorneyFile.getBytes());
            Attachment attachment = new Attachment();
            attachment.setId(keyGenService.getNextKey(KeyGenType.ATTACHMENT_KEY , true , null));
            attachment.setFileName(fileName);
            attachment.setContentType(powerOfAattorneyFile.getContentType());
            attachment.setCreationDate(new Date());
            attachment.setFileSize(powerOfAattorneyFile.getInputStream().available());
            attachment.setFileUrl(uploadDir.toString());
            attachment.setInvestor(investor);

            attachments.add(attachment);
            powerOfAattorneyFile.getInputStream().close();
        }
        return attachments;
    }
}