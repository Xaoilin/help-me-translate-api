package com.xaoilin.translate.core.payload;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveTranslationRequest {

    private String email;
    private String sourceLanguage;
    private String targetLanguage;
    private String sourceText;
    private String targetText;
}
