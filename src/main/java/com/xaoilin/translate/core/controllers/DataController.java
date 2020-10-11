package com.xaoilin.translate.core.controllers;

import com.xaoilin.translate.auth.payload.response.MessageResponse;
import com.xaoilin.translate.core.exception.TranslationNotFoundException;
import com.xaoilin.translate.core.exception.UserNotFoundException;
import com.xaoilin.translate.core.payload.SaveTranslationRequest;
import com.xaoilin.translate.core.responses.SavedTranslationResponse;
import com.xaoilin.translate.core.services.DataService;
import com.xaoilin.translate.database.model.SavedTranslations;
import com.xaoilin.translate.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/data")
public class DataController {

    private final DataService dataService;
    private final UserRepository userRepository;

    @Autowired
    public DataController(DataService dataService, UserRepository userRepository) {
        this.dataService = dataService;
        this.userRepository = userRepository;
    }

    @CrossOrigin
    @PostMapping(path = "/save")
    public ResponseEntity<MessageResponse> translateJson(@RequestBody SaveTranslationRequest dto) {

        try {
            dataService.saveWork(dto.getEmail(), dto.getSourceLanguage(), dto.getTargetLanguage(), dto.getSourceText(), dto.getTargetText());
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("User not found! " + e.getMessage()));
        }

        return ResponseEntity.ok().body(new MessageResponse("Successfully saved user!"));
    }

    @CrossOrigin
    @GetMapping(path = "/translations/{userId}")
    public ResponseEntity<SavedTranslationResponse> translateJson(@PathVariable long userId) {

        try {
            String email = userRepository.findById(userId)
                    .orElseThrow(UserNotFoundException::new)
                    .getEmail();

            List<SavedTranslations> savedWork = dataService.getSavedWork(email);
            return ResponseEntity.ok().body(new SavedTranslationResponse(email, savedWork));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @CrossOrigin
    @GetMapping(path = "/translations/{userId}/{translationId}")
    public ResponseEntity<SavedTranslations> translateJson(@PathVariable long userId, @PathVariable long translationId) {

        try {
            SavedTranslations savedTranslation = dataService.getTranslation(userId, translationId);
            return ResponseEntity.ok().body(savedTranslation);
        } catch (UserNotFoundException | TranslationNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
