package com.xaoilin.translate.controllers;

import com.xaoilin.translate.domain.SupportedLanguages;
import com.xaoilin.translate.responses.TranslationResponse;
import com.xaoilin.translate.services.ParseService;
import com.xaoilin.translate.services.TranslateService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1/translate")
public class TranslateController {

    private final TranslateService translateService;
    private ParseService parseService;

    @Autowired
    public TranslateController(TranslateService translateService, ParseService parseService) {
        this.translateService = translateService;
        this.parseService = parseService;
    }

    @CrossOrigin
    @PostMapping(path = "/file/{targetLanguageCode}")
    public ResponseEntity<TranslationResponse> translateFile(@RequestParam("file") MultipartFile file, @PathVariable String targetLanguageCode) throws IOException {
        String fileContent = parseService.parseContent(file);
        TranslationResponse translate = translateService.translate(fileContent, SupportedLanguages.fromCode(targetLanguageCode));

        return ResponseEntity.ok().body(translate);
    }
}
