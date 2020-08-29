package com.xaoilin.translate.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.xaoilin.translate.domain.SupportedLanguages;
import com.xaoilin.translate.responses.TranslationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TranslateControllerTest {

    @Autowired
    private TranslateController translateController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(translateController).build();
    }

    /**
     * Integration test
     */
    @Test
    void given_englishText_when_translateEndpointIsCalled_then_returnsArabicText() throws Exception {
        //given
        byte[] fileContent = "Good Morning, my name is Sabah.".getBytes();
        String key = "file";
        String filename = "text.txt";

        MockMultipartFile mockMultipartFile = new MockMultipartFile(key, filename,
                "text/plain", fileContent);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/translate/file/ar")
                .file(mockMultipartFile))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("sourceLanguage").value(SupportedLanguages.ENGLISH.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("sourceText").value("Good Morning, my name is Sabah."))
                .andExpect(MockMvcResultMatchers.jsonPath("targetLanguage").value(SupportedLanguages.ARABIC.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("translatedText").value("صباح الخير اسمي صباح."))
                .andExpect(MockMvcResultMatchers.jsonPath("direction").value("rtl"));
    }

    @Test
    void given_textInJson_when_translateWithText_then_returnsArabicText() throws Exception {
        //given
        TranslationDTO translationDTO = TranslationDTO.builder()
                .text("Good Morning, my name is Sabah.")
                .build();

        String requestBody = new GsonBuilder().create().toJson(translationDTO);

        //when & then
        mockMvc.perform(post("/api/v1/translate/json/ar")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("sourceLanguage").value(SupportedLanguages.ENGLISH.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("sourceText").value("Good Morning, my name is Sabah."))
                .andExpect(MockMvcResultMatchers.jsonPath("targetLanguage").value(SupportedLanguages.ARABIC.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("translatedText").value("صباح الخير اسمي صباح."))
                .andExpect(MockMvcResultMatchers.jsonPath("direction").value("rtl"));
    }

    @Test
    void given_arabicInJson_when_translateWithText_then_returnsEnglishTranslationDTO() throws Exception {
        //given
        TranslationDTO translationDTO = TranslationDTO.builder()
                .text("صباح الخير اسمي صباح.")
                .build();

        String requestBody = new GsonBuilder().create().toJson(translationDTO);

        //when & then
        mockMvc.perform(post("/api/v1/translate/json/en")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("sourceLanguage").value(SupportedLanguages.ARABIC.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("sourceText").value("صباح الخير اسمي صباح."))
                .andExpect(MockMvcResultMatchers.jsonPath("targetLanguage").value(SupportedLanguages.ENGLISH.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("translatedText").value("Good morning, my name is Sabah."))
                .andExpect(MockMvcResultMatchers.jsonPath("direction").value("ltr"));
    }

}