package com.xaoilin.translate.core.controllers;

import com.xaoilin.translate.core.responses.TranslationDTO;
import com.xaoilin.translate.core.responses.TranslationResponse;
import com.xaoilin.translate.core.services.ParseService;
import com.xaoilin.translate.core.services.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/translate")
public class TranslateController {

    private final TranslateService translateService;
    private final ParseService parseService;

    @Autowired
    public TranslateController(TranslateService translateService, ParseService parseService) {
        this.translateService = translateService;
        this.parseService = parseService;
    }

    @PostMapping(path = "/file/{targetLanguageCode}")
    public ResponseEntity<TranslationResponse> translateFile(@RequestParam("file") MultipartFile file, @PathVariable String targetLanguageCode) throws IOException {
        String fileContent = parseService.parseContent(file);
        TranslationResponse translate = translateService.translate(fileContent, targetLanguageCode);

        return ResponseEntity.ok().body(translate);
    }

    @PostMapping(path = "/json/{targetLanguageCode}")
    public ResponseEntity<TranslationResponse> translateJson(@RequestBody TranslationDTO translationDTO, @PathVariable String targetLanguageCode) {

        TranslationResponse translate = translateService.translate(translationDTO.getText(), targetLanguageCode);

        return ResponseEntity.ok().body(translate);
    }

    @PostMapping(path = "/json/{sourceLanguageCode}/{targetLanguageCode}")
    public ResponseEntity<TranslationResponse> translateJson(@RequestBody TranslationDTO translationDTO, @PathVariable String sourceLanguageCode, @PathVariable String targetLanguageCode) {

        TranslationResponse translate = translateService.translate(translationDTO.getText(), sourceLanguageCode, targetLanguageCode);

        return ResponseEntity.ok().body(translate);
    }

    @PostMapping(path = "/markup/json/{sourceLanguageCode}/{targetLanguageCode}")
    public ResponseEntity<TranslationResponse> translateMarkup(@RequestBody TranslationDTO translationDTO, @PathVariable String sourceLanguageCode, @PathVariable String targetLanguageCode) {

        TranslationResponse translate = translateService.translate(translationDTO.getText(), sourceLanguageCode, targetLanguageCode);

        return ResponseEntity.ok().body(translate);
    }
}
