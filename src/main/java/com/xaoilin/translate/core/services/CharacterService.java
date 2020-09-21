package com.xaoilin.translate.core.services;

import com.xaoilin.translate.core.constants.AsciiConstants;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {

    public String convertApostrophe(String wordWithApostropheASCII) {
        return wordWithApostropheASCII.replace(AsciiConstants.APOSTROPHE, "'")
                .replace(AsciiConstants.APOSTROPHE_ARABIC, "'");
    }
}
