package com.xaoilin.translate.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParseServiceTest {

    private ParseService parseService;

    @BeforeEach
    public void setup() {
        parseService = new ParseService();
    }

    @Test
    void given_fileWithEnglishText_then_returnsTheContent() throws IOException {
        // given
        byte[] fileContent = "Good Morning, my name is Sabah.".getBytes();
        String key = "file";
        String filename = "text.txt";

        MockMultipartFile mockMultipartFile = new MockMultipartFile(key, filename,
                "text/plain", fileContent);

        // when
        String content = parseService.parseContent(mockMultipartFile);

        // then
        Assertions.assertThat(content).isEqualTo("Good Morning, my name is Sabah.");
    }

    @Test
    void given_fileWithArabicText_then_returnsTheContent() throws IOException {
        // given
        byte[] fileContent = "صباح الخير اسمي صباح.".getBytes();
        String key = "file";
        String filename = "text.txt";

        MockMultipartFile mockMultipartFile = new MockMultipartFile(key, filename,
                "text/plain", fileContent);

        // when
        String content = parseService.parseContent(mockMultipartFile);

        // then
        Assertions.assertThat(content).isEqualTo("صباح الخير اسمي صباح.");
    }

    @Test
    void given_stringWithMultipleLines_then_returnsTranslationWithMultipleLines() {
        // given
        String multipleLineText = "Hello World.\nMy name is Sabah.";

        // when
        List<String> multilineTextToTranslate = parseService.convertLinesToArray(multipleLineText);

        // then
        Assertions.assertThat(multilineTextToTranslate.size()).isEqualTo(2);
        Assertions.assertThat(multilineTextToTranslate.get(0)).isEqualTo("Hello World.");
        Assertions.assertThat(multilineTextToTranslate.get(1)).isEqualTo("My name is Sabah.");
    }
}