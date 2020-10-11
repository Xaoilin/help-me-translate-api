package com.xaoilin.translate.core.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xaoilin.translate.database.model.SavedTranslations;
import lombok.Builder;

import java.util.List;

@Builder
@JsonDeserialize(builder = SavedTranslationResponse.SavedTranslationResponseBuilder.class)
public class SavedTranslationResponse {
    private final String email;
    private final List<SavedTranslations> savedTranslations;

    public SavedTranslationResponse(String email, List<SavedTranslations> savedTranslations) {
        this.email = email;
        this.savedTranslations = savedTranslations;
    }

    public String getEmail() {
        return email;
    }

    public List<SavedTranslations> getSavedTranslations() {
        return savedTranslations;
    }
}
