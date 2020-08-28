package com.xaoilin.translate.domain;

public enum SupportedLanguages {
    ENGLISH("en", "ltr"),
    ARABIC("ar", "rtl");

    private final String code;
    private final String direction;

    SupportedLanguages(String code, String direction) {
        this.code = code;
        this.direction = direction;
    }

    public String getCode() {
        return code;
    }

    public String getDirection() {
        return direction;
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
