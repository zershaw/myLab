package com.cbsys.saleexplore.service.store;

import com.cbsys.saleexplore.iservice.store.IStoreBusiService;
import com.cbsys.saleexplore.config.ConstantConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
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
public class StoreBusiService implements IStoreBusiService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration freemarkerConfig;

    @Override
    @Async
    public void sendBusinessClaimInfo(String email) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            Map<String, Object> model = new HashMap();

            // set loading location to src/main/resources
            // You may want to use a subfolder such as /templates here
            freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

            Template t = freemarkerConfig.getTemplate("claimBusiIntro.html");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(email);
            helper.setText(text, true); // set to html
            // config the subject of this email
            helper.setSubject(ConstantConfig.EMAIL_CLAIM_BUSINESS_INFO_SUBJECT);

            sender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
