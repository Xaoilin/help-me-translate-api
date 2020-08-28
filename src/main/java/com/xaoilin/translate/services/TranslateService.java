package com.xaoilin.translate.services;

import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.xaoilin.translate.domain.SupportedLanguages;
import com.xaoilin.translate.responses.TranslationResponse;
import org.springframework.stereotype.Service;

@Service
public class TranslateService {

    private Translate translate;

    public TranslateService() {
        translate = TranslateOptions.getDefaultInstance().getService();
    }

    public TranslationResponse translate(String textToTranslate, SupportedLanguages source, SupportedLanguages target) {
        Translate.TranslateOption sourceLanguage = Translate.TranslateOption.sourceLanguage(source.getCode());
        Translate.TranslateOption targetLanguage = Translate.TranslateOption.targetLanguage(target.getCode());

        Translation translatedText = translate.translate(textToTranslate, sourceLanguage, targetLanguage);

        return TranslationResponse.builder()
                .sourceLanguage(source.name())
                .sourceText(textToTranslate)
                .targetLanguage(target.name())
                .translatedText(translatedText.getTranslatedText())
                .direction(target.getDirection())
                .build();
    }

    public TranslationResponse translate(String textToTranslate, SupportedLanguages target) {
        Translate.TranslateOption targetLanguage = Translate.TranslateOption.targetLanguage(target.getCode());

        Translation translatedText = translate.translate(textToTranslate, targetLanguage);

        SupportedLanguages sourceLanguage = SupportedLanguages.fromCode(translatedText.getSourceLanguage());

        return TranslationResponse.builder()
                .sourceLanguage(sourceLanguage.name())
                .sourceText(textToTranslate)
                .targetLanguage(target.name())
                .translatedText(translatedText.getTranslatedText())
                .direction(target.getDirection())
                .build();
    }
}
