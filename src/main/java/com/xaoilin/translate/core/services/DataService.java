package com.xaoilin.translate.core.services;

import com.xaoilin.translate.core.exception.TranslationNotFoundException;
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
    public void saveWork(String email, String sourceLanguage, String targetLanguage, String sourceText, String targetText) throws UserNotFoundException {
        AuthUser user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        SavedTranslations translations = new SavedTranslations(user, sourceLanguage, targetLanguage, sourceText, targetText);

        List<SavedTranslations> savedTranslations = user.getSavedTranslations();
        savedTranslations.add(translations);

        userRepository.save(user);
    }

    public List<SavedTranslations> getSavedWork(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new)
                .getSavedTranslations();
    }

    public SavedTranslations getTranslation(long userId, long translationId) throws UserNotFoundException, TranslationNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new)
                .getSavedTranslations()
                .stream()
                .filter(data -> data.getId() == translationId)
                .findFirst()
                .orElseThrow(TranslationNotFoundException::new);
    }
}
