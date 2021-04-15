package com.cbsys.saleexplore.service;

import com.cbsys.saleexplore.iservice.IEmailAccountVeriService;
import com.cbsys.saleexplore.config.AppProperties;
import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.entity.EmailPwdVCode;
import com.cbsys.saleexplore.entity.User;
import com.cbsys.saleexplore.idao.IEmailPwdVeriDAO;
import com.cbsys.saleexplore.util.RandomStringUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;


@Service
public class EmailAccountVeriService implements IEmailAccountVeriService, InitializingBean {

    @Autowired
    private IEmailPwdVeriDAO pwdVeriDAO;

    @Autowired
    private AppProperties appProperties;


    private RandomStringUtil randomStringUtil;


    //https://www.mkyong.com/spring-boot/spring-boot-how-to-send-email-via-smtp/
    //https://www.quickprogrammingtips.com/spring-boot/how-to-send-email-from-spring-boot-applications.html
    //https://memorynotfound.com/spring-mail-integration-testing-junit-greenmail-example/
    //https://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration freemarkerConfig;


    @Override
    public void afterPropertiesSet() {
        // the veri-code has 5 characters
        randomStringUtil = new RandomStringUtil(5);
    }


    @Override
    @Async
    public void sendVeriCodeEmailPwd(User user) {

        // the verification code is not case sensitive
        EmailPwdVCode vCode = new EmailPwdVCode(user.getEmail(), randomStringUtil.nextString().toLowerCase());

        // first has to insert the veriCode to db
        pwdVeriDAO.insert(vCode);

        // conduct the email sending process
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            Map<String, Object> model = new HashMap();
            model.put("username", user.getUsername());
            model.put("veriCode", vCode.getVeriCode());

            // set loading location to src/main/resources
            // You may want to use a subfolder such as /templates here
            freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

            Template t = freemarkerConfig.getTemplate("updatePwd.html");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(user.getEmail());
            helper.setText(text, true); // set to html
            // config the subject of this email
            helper.setSubject(ConstantConfig.EMAIL_RESET_SUBJECT);

            sender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Async
    public void sendEmailVeryLink(User user) {
        // the verification code is not case sensitive
        EmailPwdVCode vCode = new EmailPwdVCode(user.getEmail(), randomStringUtil.nextString().toLowerCase());

        // first has to insert the veriCode to db
        pwdVeriDAO.insert(vCode);

        // sending the verification link
        String veriLink = appProperties.getServerBaseUrl() + "api/public/1/auth/confirmEmailVeri?email=" + user.getEmail() + "&veriCode=" + vCode.getVeriCode();

        // conduct the email sending process
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            Map<String, Object> model = new HashMap();
            model.put("username", user.getUsername());
            model.put("veriLink", veriLink);

            // set loading location to src/main/resources
            // You may want to use a subfolder such as /templates here
            freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

            Template t = freemarkerConfig.getTemplate("emailConfirm.html");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(user.getEmail());
            helper.setText(text, true); // set to html
            // config the subject of this email
            helper.setSubject(ConstantConfig.EMAIL_ACCOUNT_VERIFI_SUBJECT);

            sender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public EmailPwdVCode getVCodeByEmail(String email) {
         return pwdVeriDAO.get(email);
    }

    @Override
    public int deleteEmailVCode(String email) {
        return pwdVeriDAO.delete(email);
    }

}
