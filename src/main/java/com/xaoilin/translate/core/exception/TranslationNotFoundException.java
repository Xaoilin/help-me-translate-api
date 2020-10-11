package com.xaoilin.translate.core.exception;

public class TranslationNotFoundException extends Exception {
    public TranslationNotFoundException(String message) {
        super(message);
    }

    public TranslationNotFoundException() {
        super("Translation not found!");
    }
}
