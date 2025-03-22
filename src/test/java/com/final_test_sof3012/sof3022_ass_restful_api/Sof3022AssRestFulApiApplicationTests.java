package com.final_test_sof3012.sof3022_ass_restful_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@EnableScheduling
class Sof3022AssRestFulApiApplicationTests {


    @Test
    void contextLoads() {
    }



    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("CLOUDINARY_URL", () -> "cloudinary://349514472457269:r8pMioZZudpQKeWjDENMtQ0vsbg@sof3022-image-cloudinary");
        registry.add("EXPIRATION_TIME", () -> "84600000");
        registry.add("JWT_SECRET_KEY", () -> "asdofhoahwelfknaldkjvbaibwieofulahdslkfjhfkhfaiwubeifawefjshdalfkjhdaslkfjahslkdfjahlskdf");
        registry.add("SPRING_DB_USERNAME", () -> "sa");
        registry.add("SPRING_DB_PASSWORD", () -> "123");
        registry.add("SPRING_MAIL_USERNAME", () -> "doanchanphong0610@gmail.com");
        registry.add("SPRING_MAIL_PASSWORD", () -> "pslu mezy znzy mdeb");
        registry.add("SPRING_DEFAULT_FROM", () -> "doanchanphong0610@gmail.com");
        registry.add("EXPIRATION_JWT_REFRESH_TIME", () -> "604800000");
        registry.add("SPRING_DB_URL", () -> "jdbc:sqlserver://localhost;database=DCPsShopWebsite;encrypt=false;trustServerCertificate=true");
        registry.add("DEFAULT_PAYMENT_STATUS", () -> "NOT_PAYMENT");
        registry.add("SPRING_MAIL_DEFAULT_FROM",() -> "HELLO @!#!@#!@#");


        List<?> list = new ArrayList<>();
    }
}
