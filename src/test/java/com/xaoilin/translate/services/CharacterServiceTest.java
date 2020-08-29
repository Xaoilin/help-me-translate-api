package com.xaoilin.translate.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharacterServiceTest {

    private CharacterService characterService;

    @BeforeEach
    void setup() {
        characterService = new CharacterService();
    }

    @Test
    void given_stringWithApostropheASCII_then_shouldReplaceASCIICodeWithApostrophe() {
        // given
        String wordWithApostropheASCII = "we&#39;re";

        // when
        String convertedWord = characterService.convertApostrophe(wordWithApostropheASCII);

        // then
        Assertions.assertThat(convertedWord).isEqualTo("we're");
    }

    @Test
    void given_stringWithApostropheCode_then_shouldReplaceASCIICodeWithApostrophe() {
        // given
        String wordWithApostropheASCII = "القلق لدى &quot;المراهقين&quot;";

        // when
        String convertedWord = characterService.convertApostrophe(wordWithApostropheASCII);

        // then
        Assertions.assertThat(convertedWord).isEqualTo("القلق لدى 'المراهقين'");
    }
}