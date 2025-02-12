package com.szhbl.framework.email.impl;

import com.szhbl.framework.email.IMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @Author: hp
 * @Description:
 * @Date: Create in 2019/1/28/0028 22:00
 * @param: $params$
 * @return: $returns$
 */
@Service
public class IMailServiceImpl implements IMailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Spring Boot 提供了一个发送邮件的简单抽象，使用的是下面这个接口，这里直接注入即可使用
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 配置文件中我的qq邮箱
     */
    @Value("${spring.mail.from}")
    private String from;

    /**
     * 简单文本邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public String sendSimpleMail(String to, String subject, String content) {
        try {
            //创建SimpleMailMessage对象
            SimpleMailMessage message = new SimpleMailMessage();
            //邮件发送人
            message.setFrom(from);
            //邮件接收人
            message.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容
            message.setText(content);
            //发送邮件
            mailSender.send(message);
        } catch (MailSendException e) {
            logger.error("发送邮件时发生异常！", e);
            return e.getMessageExceptions()[0].getMessage();
        }
        return "发送成功";
    }

    @Override
    public String sendSimpleMail(String to[], String subject, String content) {
        try {
            //创建SimpleMailMessage对象
            SimpleMailMessage message = new SimpleMailMessage();
            //邮件发送人
            message.setFrom(from);
            //邮件接收人
            message.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容
            message.setText(content);
            //发送邮件
            mailSender.send(message);
        } catch (MailSendException e) {
            logger.error("发送邮件时发生异常！", e);
            return e.getMessageExceptions()[0].getMessage();
        }
        return "发送成功";
    }

    /**
     * html邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public String sendHtmlMail(String to, String subject, String content) {
        //获取MimeMessage对象
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人
            messageHelper.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容，html格式
            messageHelper.setText(content, true);
            //发送
            mailSender.send(message);
            //日志信息
            logger.info("邮件已经发送。");
        } catch (MailSendException e) {
            logger.error("发送邮件时发生异常！", e);
            return e.getMessageExceptions()[0].getMessage();
        } catch (MessagingException e) {
            logger.error("发送邮件时发生异常！", e);
            return e.getNextException().getMessage();
        }
        return "发送成功";
    }

    @Override
    public String sendHtmlMail(String to[], String subject, String content) {
        //获取MimeMessage对象
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人
            messageHelper.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容，html格式
            messageHelper.setText(content, true);
            //发送
            mailSender.send(message);
            //日志信息
            logger.info("邮件已经发送。");
        } catch (MailSendException e) {
            logger.error("发送邮件时发生异常！", e);
            return e.getMessageExceptions()[0].getMessage();
        } catch (MessagingException e) {
            logger.error("发送邮件时发生异常！", e);
            return e.getNextException().getMessage();
        }
        return "发送成功";
    }

    /**
     * 带附件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件
     */
    @Override
    public String sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            //日志信息
            logger.info("邮件已经发送。");
        } catch (MailSendException e) {
            logger.error("发送邮件时发生异常！", e);
            return e.getMessageExceptions()[0].getMessage();
        } catch (MessagingException e) {
            logger.error("发送邮件时发生异常！", e);
            return e.getNextException().getMessage();
        }
        return "发送成功";
    }

    @Override
    public String sendAttachmentsMail(String to[], String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            //日志信息
            logger.info("邮件已经发送。");
        } catch (MailSendException e) {
            logger.error("发送邮件时发生异常！", e);
            return e.getMessageExceptions()[0].getMessage();
        } catch (MessagingException e) {
            logger.error("发送邮件时发生异常！", e);
            return e.getNextException().getMessage();
        }
        return "发送成功";
    }
}

