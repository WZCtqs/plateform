package com.szhbl.common.utils.email;

import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

@Slf4j
public class MailUtils {


    static {
        System.setProperty("mail.mime.splitlongparameters", "false");
        System.setProperty("mail.mime.charset", "UTF-8");
    }

    public static void mailByAll(String smtpSever,String emailAuthentication,String sendEmail, String password,
                                 String receiveEmail,String subject, String content) {
        if("0".equals(emailAuthentication)){
            emailAuthentication="true";
        }else {
            emailAuthentication="false";
        }
        System.out.println(emailAuthentication);
        try {
            // 1.创建一个程序与邮件服务器会话对象 Session

            Properties props = new Properties();
            //设置发送的协议
            props.setProperty("mail.transport.protocol", "smtp");

            //设置发送邮件的服务器
            props.setProperty("mail.host", smtpSever);
            props.setProperty("mail.smtp.auth", emailAuthentication);// 指定验证

            // 创建验证器
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    //设置发送人的帐号和密码
                    return new PasswordAuthentication(sendEmail, password);
                }
            };

            //Session session = Session.getInstance(props, auth);
            Session session =javax.mail.Session.getInstance(props, auth);
            // 2.创建一个Message，它相当于是邮件内容
            Message message = new MimeMessage(session);
            //设置发送者
            message.setFrom(new InternetAddress(sendEmail));

            //设置发送方式与接收者
            if (receiveEmail != null&&receiveEmail != "") {
                InternetAddress[] iaToList = new InternetAddress().parse(receiveEmail.replaceAll("，",",").replaceAll("\\|",",").replaceAll(";",","));
                message.setRecipients(Message.RecipientType.TO, iaToList); // 收件人
            }

            //设置邮件主题
            message.setSubject(subject);
            // message.setText("这是一封激活邮件，请<a href='#'>点击</a>");

            //设置邮件内容
            //message.setContent(content, "text/html;charset=utf-8"); 换行符是<br>
            message.setContent(content, "text/plain;charset=utf-8"); //换行符是\n

            // 3.创建 Transport用于将邮件发送
            Transport.send(message);
            log.info("mailByAll成功:" + receiveEmail);
        }catch (MessagingException m){
            log.error("mailByAll失败：{}",m.toString(),m.getStackTrace());
            throw new RuntimeException("邮件发送失败");
        }
    }

    //发送带有附件的邮件
    public static void sendAttachmentMail(String smtpSever,String emailAuthentication,String sendEmail, String password,
                                          String to[],String ms[],String subject, String mailContent,File file) {
        if("0".equals(emailAuthentication)){
            emailAuthentication="true";
        }else {
            emailAuthentication="false";
        }
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", smtpSever);
        props.setProperty("mail.smtp.auth", emailAuthentication);
        // 创建验证器
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //设置发送人的帐号和密码
                return new PasswordAuthentication(sendEmail, password);
            }
        };
        try {
            // 1.创建会话
            Session session =javax.mail.Session.getInstance(props, auth);
            // 2.获取传输对象
            Transport transport = session.getTransport("smtp");
            // 3.设置连接
            transport.connect(smtpSever, sendEmail, password);
            // 4.创建邮件
            // 邮件头
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sendEmail));
            String toList = null;
            String msList = null;
            if (to != null) {
                toList = getMailList(to);
                InternetAddress[] iaToList = new InternetAddress().parse(toList);
                message.setRecipients(Message.RecipientType.TO, iaToList); // 收件人
            }
            if (ms != null) {
                msList = getMailList(ms);
                InternetAddress[] iaMsList = new InternetAddress().parse(msList);
                message.setRecipients(Message.RecipientType.BCC, iaMsList); // 密送人
            }
            message.setSubject(subject);
            // 邮件体（发送的字符串内容+附件）
            Multipart multipart = new MimeMultipart();
            // 信息（发送的字符串内容）
            BodyPart content = new MimeBodyPart();
            content.setContent(mailContent, "text/html;charset=utf-8");
            // 添加信息
            multipart.addBodyPart(content);
            //附件对象----->路径------>附件转换为DataSource源------>DataSource源封装到附件对象中
            if(file!=null){
                // 附件
                BodyPart attachment = new MimeBodyPart();
                String filePath = file.getPath();
                FileDataSource fileDataSource = new FileDataSource(filePath);
                attachment.setDataHandler(new DataHandler(fileDataSource));
                //设置接收到 的附件名
                String filename = fileDataSource.getName();
                attachment.setFileName(MimeUtility.encodeText(filename));
                // 添加附件
                multipart.addBodyPart(attachment);
            }
            // 5.发送邮件
            message.setContent(multipart);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e) {
            log.error("发送带有附件的邮件失败：{}",e.toString(),e.getStackTrace());
            throw new RuntimeException("邮件发送失败");
        }
    }

   /* public void send(String to[], String cs[], String ms[], String subject,
                     String content, String formEmail, String fileList[]) {
        try {
            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.transport.protocol", "smtp");
            p.put("mail.smtp.host", "smtp.qq.com");
            p.put("mail.smtp.port", "25");
            // 建立会话
            Session session = Session.getInstance(p);
            Message msg = new MimeMessage(session); // 建立信息
            BodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            msg.setFrom(new InternetAddress(formEmail)); // 发件人

            String toList = null;
            String toListcs = null;
            String toListms = null;

            // 发送,
            if (to != null) {
                toList = getMailList(to);
                InternetAddress[] iaToList = new InternetAddress().parse(toList);
                msg.setRecipients(Message.RecipientType.TO, iaToList); // 收件人
            }

            // 抄送
            if (cs != null) {
                toListcs = getMailList(cs);
                InternetAddress[] iaToListcs = new InternetAddress().parse(toListcs);
                msg.setRecipients(Message.RecipientType.CC, iaToListcs); // 抄送人
            }

            // 密送
            if (ms != null) {
                toListms = getMailList(ms);
                InternetAddress[] iaToListms = new InternetAddress().parse(toListms);
                msg.setRecipients(Message.RecipientType.BCC, iaToListms); // 密送人
            }
            msg.setSentDate(new Date()); // 发送日期
            msg.setSubject(subject); // 主题
            msg.setText(content); // 内容
            // 显示以html格式的文本内容
            messageBodyPart.setContent(content, "text/html;charset=utf-8");
            multipart.addBodyPart(messageBodyPart);

            // 2.保存多个附件
            if (fileList != null) {
                addTach(fileList, multipart);
            }

            msg.setContent(multipart);
            // 邮件服务器进行验证
            Transport tran = session.getTransport("smtp");
            tran.connect("smtp.qq.com", "postmaster@qq.com",
                    "asiamedia");
            tran.sendMessage(msg, msg.getAllRecipients()); // 发送
            System.out.println("邮件发送成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 添加多个附件
    public static void addTach(String fileList[], Multipart multipart)
            throws MessagingException, UnsupportedEncodingException {
        for (int index = 0; index < fileList.length; index++) {
            MimeBodyPart mailArchieve = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(fileList[index]);
            mailArchieve.setDataHandler(new DataHandler(fds));
            mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(),
                    "GBK", "B"));
            multipart.addBodyPart(mailArchieve);
        }
    }*/

    private static String getMailList(String[] mailArray) {
        StringBuffer toList = new StringBuffer();
        int length = mailArray.length;
        if (mailArray != null && length < 2) {
            toList.append(mailArray[0]);
        } else {
            for (int i = 0; i < length; i++) {
                toList.append(mailArray[i]);
                if (i != (length - 1)) {
                    toList.append(",");
                }
            }
        }
        return toList.toString();
    }

    public static void gendan2Send(String receiveEmail, String subject, String content) {
        mailByAll("smtp.qiye.163.com", "0", "booking@zih718.com", "UD6VqLfJRq2yhDwJ", receiveEmail, subject, content);
    }
  /*  public static void main(String[] args) {
        mailByAll("smtp.qiye.163.com","0","booking@zih718.com","1018zih718ZIH","1374633657@qq.com","cesji","213");
    }*/
}
