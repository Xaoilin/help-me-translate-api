package com.xaoilin.translate.services;

import com.xaoilin.translate.domain.SupportedLanguages;
import com.xaoilin.translate.responses.TranslationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TranslateServiceTest {

    @Autowired
    private TranslateService translateService;

    @BeforeEach
    public void setup() {
    }

    @Test
    void given_englishText_when_callingGoogleApi_then_translatesToArabic() {
        // given
        String englishText = "The weather is lovely today";

        // when
        TranslationResponse translatedText = translateService.translate(englishText, SupportedLanguages.ENGLISH.getCode(), SupportedLanguages.ARABIC.getCode());

        // then
        assertThat(translatedText.getTranslatedText()).isEqualTo("الطقس جميل اليوم");
        assertThat(translatedText.getSourceText()).isEqualTo(englishText);
        assertThat(translatedText.getSourceLanguage()).isEqualTo(SupportedLanguages.ENGLISH.name());
        assertThat(translatedText.getTargetLanguage()).isEqualTo(SupportedLanguages.ARABIC.name());
    }

    @Test
    void given_arabicText_when_callingGoogleApi_then_translatesToEnglish() {
        // given
        String textToTranslate = "أنا جائع";

        // when
        TranslationResponse translatedText = translateService.translate(textToTranslate, SupportedLanguages.ARABIC.getCode(), SupportedLanguages.ENGLISH.getCode());

        // then
        assertThat(translatedText.getTranslatedText()).isEqualTo("I am hungry");
        assertThat(translatedText.getSourceText()).isEqualTo(textToTranslate);
        assertThat(translatedText.getSourceLanguage()).isEqualTo(SupportedLanguages.ARABIC.name());
        assertThat(translatedText.getTargetLanguage()).isEqualTo(SupportedLanguages.ENGLISH.name());
    }

    @Test
    void given_arabicText_when_incorrectArguments_then_returnsArabicText() {
        // given
        String textToTranslate = "أنا جائع";

        // when
        TranslationResponse translatedText = translateService.translate(textToTranslate, SupportedLanguages.ENGLISH.getCode(), SupportedLanguages.ARABIC.getCode());

        // then
        assertThat(translatedText.getTranslatedText()).isEqualTo("أنا جائع");
        assertThat(translatedText.getSourceText()).isEqualTo(textToTranslate);
        assertThat(translatedText.getSourceLanguage()).isEqualTo(SupportedLanguages.ENGLISH.name());
        assertThat(translatedText.getTargetLanguage()).isEqualTo(SupportedLanguages.ARABIC.name());
    }

    @Test
    void given_englishText_when_callingTranslateWithOnlyTargetLanguage_then_translatesToArabic() {
        // given
        String englishText = "The weather is lovely today";

        // when
        TranslationResponse translatedText = translateService.translate(englishText, SupportedLanguages.ARABIC.getCode());

        // then
        assertThat(translatedText.getTranslatedText()).isEqualTo("الطقس جميل اليوم");
        assertThat(translatedText.getSourceText()).isEqualTo(englishText);
        assertThat(translatedText.getSourceLanguage()).isEqualTo(SupportedLanguages.ENGLISH.name());
        assertThat(translatedText.getTargetLanguage()).isEqualTo(SupportedLanguages.ARABIC.name());
    }

    @Test
    void given_englishText_when_callingTranslateWithOnlyTargetLanguage_then_translatesToEnglish() {
        // given
        String englishText = "The weather is lovely today";

        // when
        TranslationResponse translatedText = translateService.translate(englishText, SupportedLanguages.ENGLISH.getCode());

        // then
        assertThat(translatedText.getTranslatedText()).isEqualTo(englishText);
        assertThat(translatedText.getSourceText()).isEqualTo(englishText);
        assertThat(translatedText.getSourceLanguage()).isEqualTo(SupportedLanguages.ENGLISH.name());
        assertThat(translatedText.getTargetLanguage()).isEqualTo(SupportedLanguages.ENGLISH.name());
    }

    @Test
    void given_multipleLineText_when_translating_then_returnsMultipleLineTranslations() {
        // given
        String multipleLineText = "Hello World.\nMy name is Sabah.";

        // when
        TranslationResponse translate = translateService.translate(multipleLineText, SupportedLanguages.ARABIC.getCode());

        // then
        assertThat(translate.getTranslatedText()).isEqualTo("مرحبا بالعالم.\nاسمي صباح.");
    }

    @Test
    void given_arabicThatTranslatesToApostrophe_when_translating_then_returnsApostropheNotASCIICode() {
        // given
        String multipleLineText = "هناك العديد من الأشياء المختلفة التي يمكن أن تجعلنا نشعر بالقلق. على سبيل المثال ، قد نشعر بالقلق عندما نكون على وشك إجراء اختبار مهم ، أو عندما نكون على وشك إلقاء خطاب أمام الجمهور.\n";

        // when
        TranslationResponse translate = translateService.translate(multipleLineText, SupportedLanguages.ENGLISH.getCode());

        // then
        assertThat(translate.getTranslatedText()).isEqualTo("There are many different things that can make us anxious. For example, we might feel anxious when we're about to take an important test, or when we're about to give a speech in front of an audience.");
    }
}