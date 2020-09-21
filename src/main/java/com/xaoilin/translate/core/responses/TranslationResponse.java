package com.xaoilin.translate.core.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TranslationResponse {
    private final String sourceLanguage;
    private final String targetLanguage;
    private final String sourceText;
    private final String translatedText;
    private final String direction;
}
