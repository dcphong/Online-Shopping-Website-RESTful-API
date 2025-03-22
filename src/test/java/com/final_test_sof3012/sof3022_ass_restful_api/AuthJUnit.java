package com.final_test_sof3012.sof3022_ass_restful_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_test_sof3012.sof3022_ass_restful_api.dto.request.LoginRequest;
import com.final_test_sof3012.sof3022_ass_restful_api.models.User;
import com.final_test_sof3012.sof3022_ass_restful_api.services.AuthService;
import com.final_test_sof3012.sof3022_ass_restful_api.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthJUnit {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSetup() {
        var varible = "phong";
        Assertions.assertEquals("phong", varible);
    }

    @Test
    public void testLogin_Success() throws Exception {
        LoginRequest loginRequest = new LoginRequest("phongtest", "phong123");

        ResultActions result = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.expiresIn").exists())
                .andExpect(jsonPath("$.user").isNotEmpty())
                .andDo(print());
    }

    @Test
    public void testLogin_Fail() throws Exception {
        // Tạo request body JSON với sai mật khẩu
        LoginRequest loginRequest = new LoginRequest("admin", "wrongpassword");

        // Gửi request POST
        ResultActions result = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        result.andExpect(status().isForbidden())
//                .andExpect(jsonPath("$.error").exists())
                .andDo(print());

    }

    @Test
    public void isExistedUsername() {
        var username = "phong";
        User user = userService.findUserByUsername(username).orElseThrow();
        Assertions.assertNotNull(user);
    }

    @Test
    public void isIncorrectPassword(){
        var password = "incorrect";
        User user = userService.findUserByUsername("phongtest").orElseThrow();
        Assertions.assertNotEquals(user.getPassword(),password);
    }
     @Test
    public void correctPassword(){
        var password = "phong123";
        User user = userService.findUserByUsername("phongtest").orElseThrow();
        boolean isCorrect = passwordEncoder.matches(password,user.getPassword());
        Assertions.assertTrue(isCorrect);
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
        registry.add("SPRING_MAIL_DEFAULT_FROM", () -> "HELLO @!#!@#!@#");


    }
}
