package com.xaoilin.translate.controllers;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.xaoilin.translate.domain.SupportedLanguages;
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
    public void given_englishText_when_translateEndpointIsCalled_then_returnsArabicText() throws Exception {
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
                .andExpect(MockMvcResultMatchers.jsonPath("translatedText").value("صباح الخير اسمي صباح."));
    }

}