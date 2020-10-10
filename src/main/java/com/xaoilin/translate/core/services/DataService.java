package com.xaoilin.translate.core.services;

import com.xaoilin.translate.core.exception.UserNotFoundException;
import com.xaoilin.translate.database.model.SavedTranslations;
import com.xaoilin.translate.database.model.AuthUser;
import com.xaoilin.translate.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DataService {

    private final UserRepository userRepository;

    @Autowired
    public DataService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveWork(String email, String sourceLanguage, String targetLanguage, String sourceText, String targetText) {
        AuthUser user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found!"));
        SavedTranslations translations = new SavedTranslations(user, sourceLanguage, targetLanguage, sourceText, targetText);

        List<SavedTranslations> savedTranslations = user.getSavedTranslations();
        savedTranslations.add(translations);

        userRepository.save(user);
    }

    public List<SavedTranslations> getSavedWork(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"))
                .getSavedTranslations();
    }
}
