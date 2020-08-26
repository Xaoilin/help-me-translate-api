package com.xaoilin.translate.domain;

public enum SupportedLanguages {
    ENGLISH("en"),
    ARABIC("ar");

    private String code;

    SupportedLanguages(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static SupportedLanguages fromCode(String code) {
        for (SupportedLanguages language : SupportedLanguages.values()) {
            if (language.getCode().equals(code)) {
                return language;
            }
        }

        return null;
    }
}
