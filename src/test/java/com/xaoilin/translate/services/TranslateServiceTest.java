package com.xaoilin.translate.services;

import com.xaoilin.translate.domain.SupportedLanguages;
import com.xaoilin.translate.responses.TranslationResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TranslateServiceTest {

    private TranslateService translateService;

    @BeforeEach
    public void setup() {
        translateService = new TranslateService();
    }

    @Test
    public void given_englishText_when_callingGoogleApi_then_translatesToArabic() {
        // given
        String englishText = "The weather is lovely today";

        // when
        String translatedText = translateService.translate(englishText, SupportedLanguages.ENGLISH, SupportedLanguages.ARABIC);

        // then
        Assertions.assertThat(translatedText).isEqualTo("الطقس جميل اليوم");
    }

    @Test
    public void given_arabicText_when_callingGoogleApi_then_translatesToEnglish() {
        // given
        String textToTranslate = "أنا جائع";

        // when
        String translatedText = translateService.translate(textToTranslate, SupportedLanguages.ARABIC, SupportedLanguages.ENGLISH);

        // then
        Assertions.assertThat(translatedText).isEqualTo("I am hungry");
    }

    @Test
    public void given_arabicText_when_incorrectArguments_then_returnsArabicText() {
        // given
        String textToTranslate = "أنا جائع";

        // when
        String translatedText = translateService.translate(textToTranslate, SupportedLanguages.ENGLISH, SupportedLanguages.ARABIC);

        // then
        Assertions.assertThat(translatedText).isEqualTo("أنا جائع");
    }

    @Test
    public void given_englishText_when_callingTranslateWithOnlyTargetLanguage_then_translatesToArabic() {
        // given
        String englishText = "The weather is lovely today";

        // when
        TranslationResponse translatedText = translateService.translate(englishText, SupportedLanguages.ARABIC);

        // then
        Assertions.assertThat(translatedText.getTranslatedText()).isEqualTo("الطقس جميل اليوم");
        Assertions.assertThat(translatedText.getSourceText()).isEqualTo(englishText);
        Assertions.assertThat(translatedText.getSourceLanguage()).isEqualTo(SupportedLanguages.ENGLISH.name());
        Assertions.assertThat(translatedText.getTargetLanguage()).isEqualTo(SupportedLanguages.ARABIC.name());
    }

    @Test
    public void given_englishText_when_callingTranslateWithOnlyTargetLanguage_then_translatesToEnglish() {
        // given
        String englishText = "The weather is lovely today";

        // when
        TranslationResponse translatedText = translateService.translate(englishText, SupportedLanguages.ENGLISH);

        // then
        Assertions.assertThat(translatedText.getTranslatedText()).isEqualTo(englishText);
        Assertions.assertThat(translatedText.getSourceText()).isEqualTo(englishText);
        Assertions.assertThat(translatedText.getSourceLanguage()).isEqualTo(SupportedLanguages.ENGLISH.name());
        Assertions.assertThat(translatedText.getTargetLanguage()).isEqualTo(SupportedLanguages.ENGLISH.name());
    }

}