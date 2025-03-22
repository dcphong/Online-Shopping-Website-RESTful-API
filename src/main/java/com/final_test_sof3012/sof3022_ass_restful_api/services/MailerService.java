package com.final_test_sof3012.sof3022_ass_restful_api.services;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.MailerRequest;
import com.final_test_sof3012.sof3022_ass_restful_api.interfaces.MailerInterface;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MailerService implements MailerInterface {

    final JavaMailSender sender;
    final OtpService otpService;

    @Value("${SPRING_MAIL_DEFAULT_FROM}")
     String defaultFrom;

    static List<MailerRequest> listMails = new ArrayList<>();

    @Override
    public void send(MailerRequest request) throws MessagingException {
        MimeMessage mimeMessage = sender.createMimeMessage();

        MimeMessageHelper mmHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        setupBasicInfo(mmHelper,request);
        setupCcBcc(mmHelper,request);
        setupAttachments(mmHelper,request);

        sender.send(mimeMessage);
    }

    @Override
    public void queue(MailerRequest request) throws MessagingException {
        listMails.add(request);
    }

    @Override
    public void send(String to, String subject, String body) throws MessagingException {
        MailerRequest request = new MailerRequest();
        request.setTo(to);
        request.setSubject(subject);
        request.setBody(body);
        request.setFrom(defaultFrom);
        send(request);
    }

    public void sendOtpToEmail(String email) throws MessagingException {
        String otp = generateOtp();
        otpService.saveOTP(email,otp);

        String subject = "Mã OTP của bạn";
        String body = "Mã OTP của bạn là: '<b style='color:red'>" + otp + "</b>' Mã này có hiệu lực trong 2 phút.";

        send(email, subject, body);
    }

    public boolean verifyOtp(String email,String otp){
        String storedOtp = otpService.getOtp(email);
        if(storedOtp != null && storedOtp.equals(otp)){
            otpService.deleteOtp(email);
            return true;
        }
        return false;
    }

    @Scheduled(fixedDelay = 60000)
    public void run() {
        if (!listMails.isEmpty()) {
            MailerRequest mail = listMails.removeFirst();
            try {
                this.send(mail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setupBasicInfo(MimeMessageHelper mmHelper, MailerRequest request) throws MessagingException {
        mmHelper.setFrom(request.getFrom());
        mmHelper.setTo(request.getTo());
        mmHelper.setSubject(request.getSubject());
        mmHelper.setText(request.getBody(), true);
        mmHelper.setReplyTo(request.getFrom());
    }

    private void setupCcBcc(MimeMessageHelper mmHelper, MailerRequest request) throws MessagingException {
        String[] cc = request.getCc();
        if (cc != null && cc.length > 0) {
            mmHelper.setCc(cc);
        }

        String[] bcc = request.getBcc();
        if(bcc !=null && bcc.length >0){
            mmHelper.setBcc(bcc);
        }
    }

    private void setupAttachments(MimeMessageHelper mmHelper, MailerRequest request)throws MessagingException{
        String[] attachments = request.getAttachments();
        if(attachments != null){
            for(String attachment : attachments){
                File file = new File(attachment);
                mmHelper.addAttachment(file.getName(),file);
            }
        }
    }
    public static String generateOtp(){
        byte[] randomStyles = new byte[5];
        new SecureRandom().nextBytes(randomStyles);
        return Base64.getEncoder().withoutPadding().encodeToString(randomStyles).substring(0,6);
    }
}
