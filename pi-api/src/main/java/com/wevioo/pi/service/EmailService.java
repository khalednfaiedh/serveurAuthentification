package com.wevioo.pi.service;

import com.wevioo.pi.business.email.EmailDto;

public interface EmailService {

    /**
     * Send simple email
     *
     * @param dto {@link EmailDto}
     */
    void sendSimpleMessage(EmailDto dto);

    /**
     * Send HTML email
     *
     * @param dto {@link EmailDto}
     */
    void sendHtmlMessage(EmailDto dto);
}
