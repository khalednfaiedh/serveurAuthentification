package com.wevioo.pi.service.imp;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.wevioo.pi.business.email.EmailDto;
import com.wevioo.pi.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    /**
     * JavaMailSender
     */
    @Autowired
    public JavaMailSender emailSender;

    /**
     * Template engine for advanced mail
     */
    @Autowired
    private SpringTemplateEngine templateEngine;

    public static final String EMAIL_FROM = "kad@wevioo.com";

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendSimpleMessage(EmailDto dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getTo());
        if (null != dto.getCc())
            message.setCc(dto.getCc());
        message.setSubject(dto.getSubject());
        message.setText(dto.getText());
        message.setFrom(EMAIL_FROM);
        emailSender.send(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendHtmlMessage(EmailDto dto) {
        try {
            log.info("Send email, subject : " + dto.getSubject() + " inside !");

            if (dto.getTemplateName() != null)
                sendHtmlTemplateMessage(dto, dto.getTemplateName());
            else {
                MimeMessage message = emailSender.createMimeMessage();

                List<String> receiversTo = new ArrayList<>();
                List<String> receiversCc = new ArrayList<>();

                // set the main receiver(s) list
                if (null != dto.getTo())
                    receiversTo.add(dto.getTo());
                if (null != dto.getToGroup() && !dto.getToGroup().isEmpty())
                    receiversTo = dto.getToGroup();

                // set the copied receiver(s) list
                if (null != dto.getCc())
                    receiversCc.add(dto.getCc());
                if (dto.getCcGroup() != null && !dto.getCcGroup().isEmpty())
                    receiversCc = dto.getCcGroup();

                Address[] addressesTo = new Address[receiversTo.size()];
                Address[] addressesCc = new Address[receiversCc.size()];

                // set the main receiver(s) array of addresses
                if (!receiversTo.isEmpty())
                    for (int i = 0; i < receiversTo.size(); i++) {
                        addressesTo[i] = new InternetAddress(receiversTo.get(i), false);
                    }

                // set the copied receiver(s) array of addresses
                if (!receiversCc.isEmpty())
                    for (int j = 0; j < receiversCc.size(); j++) {
                        addressesCc[j] = new InternetAddress(receiversCc.get(j), false);
                    }

                message.setRecipients(Message.RecipientType.TO, addressesTo);
                if (addressesCc.length > 0)
                    message.setRecipients(Message.RecipientType.CC, addressesCc);

                log.info("-------------------------********* sending to : " + addressesTo.length);
                log.info("-------------------------********* sending cc : " + addressesCc.length);

                message.setFrom(EMAIL_FROM);
                message.setSubject(dto.getSubject());
                message.setContent(dto.getText(), "text/html; charset=utf-8");
                emailSender.send(message);
            }
        } catch (MessagingException e) {
            log.error("Unexpected exception : ", e);
        }
    }

    /////////// PRIVATE METHODS /////////////////////////////////////
    /**
     *
     * send HTML mails based on template
     *
     * @param mail
     * @param template
     */
    private void sendHtmlTemplateMessage(EmailDto mail, String template) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariables(mail.getProperties());

            String html = templateEngine.process(template, context);

            // set the main receiver(s)
            List<String> receiversTo = new ArrayList<>();
            List<String> receiversCc = new ArrayList<>();

            // set the main receiver(s) list
            if (null != mail.getTo())
                receiversTo.add(mail.getTo());
            if (null != mail.getToGroup() && !mail.getToGroup().isEmpty())
                receiversTo = mail.getToGroup();

            // set the copied receiver(s) list
            if (null != mail.getCc())
                receiversCc.add(mail.getCc());
            if (mail.getCcGroup() != null && !mail.getCcGroup().isEmpty())
                receiversCc = mail.getCcGroup();

            log.info("-------------------------********* 1 sending to : " + receiversTo.size());
            log.info("-------------------------********* 1 sending cc : " + receiversCc.size());

            String[] addressesTo = new String[receiversTo.size()];
            String[] addressesCc = new String[receiversCc.size()];

            // set the main receiver(s) array of addresses
            if (!receiversTo.isEmpty())
                for (int i = 0; i < receiversTo.size(); i++) {
                    addressesTo[i] = receiversTo.get(i);
                }

            // set the copied receiver(s) array of addresses
            if (!receiversCc.isEmpty())
                for (int j = 0; j < receiversCc.size(); j++) {
                    addressesCc[j] = receiversCc.get(j);
                }

            helper.setTo(addressesTo);
            helper.setCc(addressesCc);
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(EMAIL_FROM);

            emailSender.send(message);

        } catch (MessagingException e) {
            log.error("Unexpected exception : ", e);
        }
    }
}
