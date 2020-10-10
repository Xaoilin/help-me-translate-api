package com.xaoilin.translate.core.services;

import com.xaoilin.translate.core.domain.SupportedLanguages;
import com.xaoilin.translate.core.responses.TranslationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TranslateService.class, ParseService.class, CharacterService.class})
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

    // No longer translates to apostrophe, should test this with a different text input.
    @Test
    void given_arabicThatTranslatesToApostrophe_when_translating_then_returnsApostropheNotASCIICode() {
        // given
        String multipleLineText = "هناك العديد من الأشياء المختلفة التي يمكن أن تجعلنا نشعر بالقلق. على سبيل المثال ، قد نشعر بالقلق عندما نكون على وشك إجراء اختبار مهم ، أو عندما نكون على وشك إلقاء خطاب أمام الجمهور.\n";

        // when
        TranslationResponse translate = translateService.translate(multipleLineText, SupportedLanguages.ENGLISH.getCode());

        // then
        assertThat(translate.getTranslatedText()).isEqualTo("There are many different things that can make us anxious. For example, we may feel anxious when we are about to take an important test, or when we are about to give a speech in front of an audience.");
    }

    @Test
    void given_englishThatTranslatesToApostrophe_when_translating_then_returnsApostropheNotASCIICode() {
        // given
        String textToTranslate = "Anxiety in 'teenagers'";

        // when
        TranslationResponse translate = translateService.translate(textToTranslate, SupportedLanguages.ARABIC.getCode());

        // then
        assertThat(translate.getTranslatedText()).isEqualTo("القلق لدى 'المراهقين'");
    }
}