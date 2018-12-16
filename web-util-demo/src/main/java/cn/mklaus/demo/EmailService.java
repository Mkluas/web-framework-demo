package cn.mklaus.demo;



/**
 * @author Mklaus
 * @date 2018-03-27 下午12:06
 */
public interface EmailService {

    /**
     * 发送简单邮件
     * @param to 收件人地址
     * @param subject  邮件标题
     * @param content 邮件内容
     */
    void sendSimpleMail(String to, String subject, String content);

    void sendHtmlMail(String[] to, String subject, String content);


}
