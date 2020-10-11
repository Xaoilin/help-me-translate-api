package com.xaoilin.translate.core.controllers;

import com.google.gson.GsonBuilder;
import com.xaoilin.translate.core.payload.SaveTranslationRequest;
import com.xaoilin.translate.database.model.AuthUser;
import com.xaoilin.translate.database.model.SavedTranslations;
import com.xaoilin.translate.database.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class DataControllerTest {

    @Autowired
    private DataController dataController;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(dataController).build();
    }

    @Test
    void given_userSavesTranslation_then_savesIntoDatabase() throws Exception {
        // given
        String email = "test@hmt.com";
        String sourceLanguage = "English";
        String targetLanguage = "Arabic";
        String sourceText = "This is a great morning!";
        String targetText = "هذا صباح رائع!";


        SaveTranslationRequest payload = new SaveTranslationRequest(email, sourceLanguage, targetLanguage, sourceText, targetText);
        String requestBody = new GsonBuilder().create().toJson(payload);

        // when
        mockMvc.perform(post("/api/v1/data/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is(200));

        // then
        Optional<AuthUser> byEmail = userRepository.findByEmail(email);

        assertThat(byEmail).isPresent();
        List<SavedTranslations> savedTranslations = byEmail.get().getSavedTranslations();

        assertThat(savedTranslations.get(0).getSourceLanguage()).isEqualTo(sourceLanguage);
        assertThat(savedTranslations.get(0).getTargetLanguage()).isEqualTo(targetLanguage);
        assertThat(savedTranslations.get(0).getSourceText()).isEqualTo(sourceText);
        assertThat(savedTranslations.get(0).getTargetText()).isEqualTo(targetText);
    }

    @Test
    void given_userSavesWithBadEmail_then_throwsError() throws Exception {
        // given
        String email = "bademail@hmt.com";
        String sourceLanguage = "English";
        String targetLanguage = "Arabic";
        String sourceText = "This is a great morning!";
        String targetText = "هذا صباح رائع!";


        SaveTranslationRequest payload = new SaveTranslationRequest(email, sourceLanguage, targetLanguage, sourceText, targetText);
        String requestBody = new GsonBuilder().create().toJson(payload);

        // when
        mockMvc.perform(post("/api/v1/data/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is(400));

        // then
        Optional<AuthUser> byEmail = userRepository.findByEmail(email);

        assertThat(byEmail).isEmpty();
    }

    @Test
    void given_userGetsExistingData_then_returnsData() throws Exception {
        // given
        int userId = 998;

        // when & then
        mockMvc.perform(get("/api/v1/data/translations/" + userId))
                .andExpect(status().is(200))
                .andExpect(jsonPath("email").value("populated@hmt.com"))
                .andExpect(jsonPath("$.savedTranslations[0].sourceLanguage").value("English"))
                .andExpect(jsonPath("$.savedTranslations[0].targetLanguage").value("Arabic"))
                .andExpect(jsonPath("$.savedTranslations[0].sourceText").value("This is a great morning!"))
                .andExpect(jsonPath("$.savedTranslations[0].targetText").value("هذا صباح رائع!"));
    }

    @Test
    void given_userGetsExistingTranslation_then_returnsTranslation() throws Exception {
        // given
        int userId = 998;
        int translationId = 999;

        // when & then
        mockMvc.perform(get("/api/v1/data/translations/" + userId + "/" + translationId))
                .andExpect(status().is(200))
                .andExpect(jsonPath("sourceLanguage").value("English"))
                .andExpect(jsonPath("targetLanguage").value("Arabic"))
                .andExpect(jsonPath("sourceText").value("This is a great morning!"))
                .andExpect(jsonPath("targetText").value("هذا صباح رائع!"));
    }
}