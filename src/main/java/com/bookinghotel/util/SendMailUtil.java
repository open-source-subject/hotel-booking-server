package com.bookinghotel.util;

import com.bookinghotel.dto.common.DataMailDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendMailUtil {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    /**
     * Gửi mail với file html
     *
     * @param mail     Thông tin của mail cần gửi
     * @param template Tên file html trong folder resources/template
     *                 Example: Index.html
     */
    @Async
    @SneakyThrows
    public void sendEmailWithHTML(DataMailDTO mail, String template) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());

            Context context = new Context();
            context.setVariables(mail.getProperties());
            String htmlMsg = templateEngine.process(template, context);
            helper.setText(htmlMsg, true);
            mailSender.send(message);
        } catch (Exception ex) {
            log.error("Send mail with html template to email = {} fail, ERROR: ", mail.getTo(), ex);
        }
    }


    /**
     * Gửi mail với tệp đính kèm
     *
     * @param dataMail Thông tin của mail cần gửi
     * @param files    File cần gửi
     */
    @Async
    @SneakyThrows
    public void sendMail(DataMailDTO dataMail, MultipartFile[] files) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setTo(dataMail.getTo());
            helper.setSubject(dataMail.getSubject());
            helper.setText(dataMail.getContent());
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
                }
            }
            mailSender.send(message);
        } catch (Exception ex) {
            log.error("Send mail with attachment to email = {} fail, ERROR: ", dataMail.getTo(), ex);
        }
    }
}
