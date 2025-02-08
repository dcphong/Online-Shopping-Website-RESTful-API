package com.final_test_sof3012.sof3022_ass_restful_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class Sof3022AssRestFulApiApplicationTests {


    @Test
    void contextLoads() {
    }
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("CLOUDINARY_URL", () -> "cloudinary://349514472457269:r8pMioZZudpQKeWjDENMtQ0vsbg@sof3022-image-cloudinary");
        registry.add("EXPIRATION_TIME", () -> "84600000");
        registry.add("JWT_SECRET_KEY", () -> "asdofhoahwelfknaldkjvbaibwieofulahdslkfjhfkhfaiwubeifawefjshdalfkjhdaslkfjahslkdfjahlskdf");

    }
}
