package com.szhbl.framework.email;

/**
 * @Author: hp
 * @Description: 封装一个发邮件的接口，后边直接调用即可
 * @Date: Create in 2019/1/28/0028 21:57
 * @param: $params$
 * @return: $returns$
 */
public interface IMailService {

    /**
     * 发送文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    String sendSimpleMail(String to, String subject, String content);

    String sendSimpleMail(String to[], String subject, String content);

    /**
     * 发送HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    public String sendHtmlMail(String to, String subject, String content);
    public String sendHtmlMail(String to[], String subject, String content);

    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件
     */
    public String sendAttachmentsMail(String to, String subject, String content, String filePath);
    public String sendAttachmentsMail(String to[], String subject, String content, String filePath);


}
