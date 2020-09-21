package com.xaoilin.translate.core.services;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.xaoilin.translate.core.domain.SupportedLanguages;
import com.xaoilin.translate.core.responses.TranslationResponse;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranslateService {

    private final Translate translate;
    private final ParseService parseService;
    private final CharacterService characterService;

    @Autowired
    public TranslateService(ParseService parseService, CharacterService characterService) {
        translate = TranslateOptions.getDefaultInstance().getService();
        this.parseService = parseService;
        this.characterService = characterService;
    }

    public TranslationResponse translate(String textToTranslate, String target) {
        return translate(textToTranslate, Strings.EMPTY, target);
    }

    public TranslationResponse translate(String textToTranslate, String source, String target) {
        SupportedLanguages supportedSourceLanguage = SupportedLanguages.fromCode(source).orElse(SupportedLanguages.AUTOMATIC);
        SupportedLanguages supportedTargetLanguage = SupportedLanguages.fromCode(target).orElse(SupportedLanguages.AUTOMATIC);

        Translate.TranslateOption sourceLanguageOption = Translate.TranslateOption.sourceLanguage(supportedSourceLanguage.getCode());
        Translate.TranslateOption targetLanguageOption = Translate.TranslateOption.targetLanguage(supportedTargetLanguage.getCode());

        List<String> multilineText = parseService.convertLinesToArray(textToTranslate);
        Pair<String, String> sourceAndTranslationPair = translateMultilineText(multilineText, sourceLanguageOption, targetLanguageOption);

        SupportedLanguages sourceLanguage = SupportedLanguages.fromCode(sourceAndTranslationPair.getLeft()).orElse(SupportedLanguages.AUTOMATIC);

        String translatedText = convertAsciiCharacters(sourceAndTranslationPair.getRight());

        return TranslationResponse.builder()
                .sourceLanguage(sourceLanguage.name())
                .sourceText(textToTranslate)
                .targetLanguage(supportedTargetLanguage.name())
                .translatedText(translatedText)
                .direction(supportedTargetLanguage.getDirection())
                .build();
    }

    private Pair<String, String> translateMultilineText(List<String> multilineText, Translate.TranslateOption sourceLanguage, Translate.TranslateOption targetLanguage) {
        // todo: refactor to behavioural parameterisation with functional interface
        List<Translation> translations = multilineText.stream()
                .map(text -> {
                    if (Strings.isBlank(sourceLanguage.toString())) {
                        return translate.translate(text, targetLanguage);
                    }

                    return translate.translate(text, sourceLanguage, targetLanguage);
                })
                .collect(Collectors.toList());

        String googleSourceLanguage = translations.stream()
                .findFirst()
                .map(Translation::getSourceLanguage)
                .orElse(Strings.EMPTY);

        String translatedText = translations.stream()
                .map(Translation::getTranslatedText)
                .collect(Collectors.joining("\n"));

        return new ImmutablePair<>(googleSourceLanguage, translatedText);
    }

    private String convertAsciiCharacters(String text) {
        return characterService.convertApostrophe(text);
    }
}
