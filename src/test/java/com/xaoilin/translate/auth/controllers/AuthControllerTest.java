package com.xaoilin.translate.auth.controllers;

import com.google.gson.GsonBuilder;
import com.xaoilin.translate.auth.payload.request.LoginRequest;
import com.xaoilin.translate.auth.payload.request.SignupRequest;
import com.xaoilin.translate.database.model.AuthUser;
import com.xaoilin.translate.database.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void given_newUserSubmittingSignUp_then_savesUserInDatabaseAndReturns200OK() throws Exception {
        // given
        SignupRequest signupRequest = new SignupRequest("test@gmail.com", "password");
        String requestBody = new GsonBuilder().create().toJson(signupRequest);

        // when & then
        mockMvc.perform(post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is(200))
                .andExpect(jsonPath("message").value("User registered successfully!"));

        Optional<AuthUser> newUser = userRepository.findByEmail("test@gmail.com");
        Assertions.assertThat(newUser).isPresent();
        Assertions.assertThat(newUser.get().getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    void given_userExistsInDatabase_when_userSignsUp_then_return400BadRequest() throws Exception {
        // given
        SignupRequest signupRequest = new SignupRequest("test@hmt.com", "password");
        String requestBody = new GsonBuilder().create().toJson(signupRequest);

        // when & then
        mockMvc.perform(post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is(400))
                .andExpect(jsonPath("message").value("Error: Email is already in use!"));

        Optional<AuthUser> newUser = userRepository.findByEmail("test@hmt.com");
        Assertions.assertThat(newUser).isPresent();
    }

    @Test
    void given_badEmail_then_returns400BadRequest() throws Exception {
        // given
        SignupRequest signupRequest = new SignupRequest("tester", "password");
        String requestBody = new GsonBuilder().create().toJson(signupRequest);

        // when & then
        mockMvc.perform(post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is(400))
                .andExpect(jsonPath("message").value("Error: Bad email / password"));

        Optional<AuthUser> newUser = userRepository.findByEmail("tester");
        Assertions.assertThat(newUser).isNotPresent();
    }

    @Test
    void given_badPassword_then_returns400BadRequest() throws Exception {
        // given
        SignupRequest signupRequest = new SignupRequest("tester@gmail.com", "p2");
        String requestBody = new GsonBuilder().create().toJson(signupRequest);

        // when & then
        mockMvc.perform(post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is(400))
                .andExpect(jsonPath("message").value("Error: Bad email / password"));

        Optional<AuthUser> newUser = userRepository.findByEmail("tester@gmail.com");
        Assertions.assertThat(newUser).isNotPresent();
    }

    @Test
    void given_existingUser_when_signingInWithWrongPassword_then_returns401BadCredentials() {
        // given
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@gmail.com")
                .password("password")
                .build();

        String requestBody = new GsonBuilder().create().toJson(loginRequest);

        // when & then
         org.junit.jupiter.api.Assertions.assertThrows(NestedServletException.class, () -> {
             mockMvc.perform(post("/api/v1/auth/signin")
                     .contentType(MediaType.APPLICATION_JSON)
                     .accept(MediaType.APPLICATION_JSON)
                     .content(requestBody))
                     .andExpect(status().is(401))
                     .andExpect(jsonPath("code").value(401))
                     .andExpect(jsonPath("message").isNotEmpty())
                     .andExpect(jsonPath("timestamp").isNotEmpty());
         });
    }

    @Test
    void given_existingUser_when_signingInWithCorrectPassword_then_returns200OK() throws Exception {
        // given
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@hmt.com")
                .password("password")
                .build();

        String requestBody = new GsonBuilder().create().toJson(loginRequest);

        // when & then
        mockMvc.perform(post("/api/v1/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is(200))
                .andExpect(jsonPath("email").value("test@hmt.com"))
                .andExpect(jsonPath("accessToken").isNotEmpty());
    }
}