package by.nces.qr_vcard_app.service;

import by.nces.qr_vcard_app.model.Employee;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class EmailSenderService {

    private final JavaMailSender javaMailSender;

    private final EmployeeQRService employeeQRService;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String senderEmail;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender, EmployeeQRService employeeQRService) {
        this.javaMailSender = javaMailSender;
        this.employeeQRService = employeeQRService;
    }

    public void sendEmailWithAttachment(String subject, String body, Employee employee) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(senderEmail);
        helper.setTo(employee.getEmail());
        helper.setSubject(subject);
        helper.setText(body);

        String outputDir = System.getProperty("user.home") + "/qr-codes/";
        String fileName = employee.getFirstName() + "_" + employee.getLastName() + ".png";
        Path filePath = Paths.get(outputDir, fileName);
        FileSystemResource file = new FileSystemResource(new File(String.valueOf(filePath)));
        helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);

        javaMailSender.send(mimeMessage);
        employeeQRService.deleteContents(new File(outputDir));
    }
}
