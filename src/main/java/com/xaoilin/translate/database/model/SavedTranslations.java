package com.xaoilin.translate.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "saved_translations")
public class SavedTranslations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    public AuthUser authUser;

    @Column(name = "source_language")
    public String sourceLanguage;

    @Column(name = "target_language")
    public String targetLanguage;

    @Column(name = "source_text")
    public String sourceText;

    @Column(name = "target_text")
    public String targetText;

    public SavedTranslations(AuthUser authUser, String sourceLanguage, String targetLanguage, String sourceText, String targetText) {
        this.authUser = authUser;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.sourceText = sourceText;
        this.targetText = targetText;
    }

    public SavedTranslations() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getTargetText() {
        return targetText;
    }

    public void setTargetText(String targetText) {
        this.targetText = targetText;
    }
}
