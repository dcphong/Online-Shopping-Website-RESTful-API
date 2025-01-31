package com.final_test_sof3012.sof3022_ass_restful_api;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Sof3022AssRestFulApiApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();
        System.setProperty("SPRING_MAIL_USERNAME",dotenv.get("SPRING_MAIL_USERNAME"));
        System.setProperty("SPRING_MAIL_PASSWORD",dotenv.get("SPRING_MAIL_PASSWORD"));
        System.setProperty("SPRING_DB_USERNAME ",dotenv.get("SPRING_DB_USERNAME"));
        System.setProperty("SPRING_DB_PASSWORD",dotenv.get("SPRING_DB_PASSWORD"));
        System.setProperty("SPRING_DEFAULT_FROM",dotenv.get("SPRING_DEFAULT_FROM"));
        System.setProperty("CLOUDINARY_URL",dotenv.get("CLOUDINARY_URL"));
        System.setProperty("JWT_SECRET_KEY",dotenv.get("JWT_SECRET_KEY"));
        Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
        SpringApplication.run(Sof3022AssRestFulApiApplication.class, args);
    }

}
