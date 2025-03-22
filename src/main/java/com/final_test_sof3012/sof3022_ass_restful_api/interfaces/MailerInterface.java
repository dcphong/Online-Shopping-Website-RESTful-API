package com.final_test_sof3012.sof3022_ass_restful_api.interfaces;

import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.MailerRequest;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Component;

@Component
public interface MailerInterface {

    void send(MailerRequest request) throws MessagingException;
    void queue(MailerRequest request) throws MessagingException;
    void send(String to, String subject, String body) throws MessagingException ;
}
