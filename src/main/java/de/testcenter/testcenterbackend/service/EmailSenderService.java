package de.testcenter.testcenterbackend.service;

import java.io.File;
import java.util.List;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import de.testcenter.testcenterbackend.models.Zertifikat;

@Service
public class EmailSenderService {

    private final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}") 
    String fromMail;

    public void sendMail(String toMail, List<File> files, Zertifikat zertifikat) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
                mimeMessage.setFrom(new InternetAddress(fromMail));
                mimeMessage.setSubject("Ihr Testresultat");
    
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                for (File file : files) {
                    FileSystemResource fileResource = new FileSystemResource(file);
                    helper.addAttachment(file.getName(), fileResource, "application/pdf");
                }
                if (isPositiveTest(zertifikat)) {
                    Resource resource = new ClassPathResource("pdf/merkblatt_positivtest.pdf");
                    helper.addAttachment("merkblatt_positivtest.pdf", new ByteArrayResource(IOUtils.toByteArray(resource.getInputStream())));
                }
                helper.setText("", true);
            }
        };
    
        try {
            mailSender.send(preparator);
            logger.info("Zertifikat successfully sent to " + toMail);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private boolean isPositiveTest(Zertifikat zertifikat) {
        return "positiv".equals(zertifikat.getTestung().getErgebnis());
    }
}
