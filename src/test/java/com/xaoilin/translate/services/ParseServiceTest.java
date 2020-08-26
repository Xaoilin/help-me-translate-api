package com.xaoilin.translate.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ParseServiceTest {

    private ParseService parseService;

    @BeforeEach
    public void setup() {
        parseService = new ParseService();
    }

    @Test
    public void given_fileWithEnglishText_then_returnsTheContent() throws IOException {
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
    public void given_fileWithArabicText_then_returnsTheContent() throws IOException {
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
}