package com.xaoilin.translate.core.services;


import com.xaoilin.translate.core.exception.UserNotFoundException;
import com.xaoilin.translate.database.model.AuthUser;
import com.xaoilin.translate.database.model.SavedTranslations;
import com.xaoilin.translate.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataService.class})
@EnableJpaRepositories(basePackageClasses = {UserRepository.class})
@EntityScan(basePackages = {"com.xaoilin.translate.database.model"})
@DataJpaTest
class DataServiceTest {

    @Autowired
    private DataService dataService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void given_validSourceAndTargetText_then_savesIntoDb() throws UserNotFoundException {
        // given
        String email = "test@hmt.com";
        String sourceLanguage = "English";
        String targetLanguage = "Arabic";
        String sourceText = "This is a great morning!";
        String targetText = "هذا صباح رائع!";

        // when
        dataService.saveWork(email, sourceLanguage, targetLanguage, sourceText, targetText);

        // then
        Optional<AuthUser> byEmail = userRepository.findByEmail(email);

        assertThat(byEmail).isPresent();
        List<SavedTranslations> savedTranslations = byEmail.get().getSavedTranslations();
        assertThat(savedTranslations.size()).isEqualTo(1);
        assertThat(savedTranslations.get(0).sourceLanguage).isEqualTo(sourceLanguage);
        assertThat(savedTranslations.get(0).targetLanguage).isEqualTo(targetLanguage);
        assertThat(savedTranslations.get(0).sourceText).isEqualTo(sourceText);
        assertThat(savedTranslations.get(0).targetText).isEqualTo(targetText);
    }

    @Test
    void given_validEmailWithSavedTranslations_then_retrievesWorkFromDb() throws UserNotFoundException {
        // given
        String email = "populated@hmt.com";

        // when
        List<SavedTranslations> savedTranslations = dataService.getSavedWork(email);

        // then
        assertThat(savedTranslations.size()).isEqualTo(1);
        assertThat(savedTranslations.get(0).sourceLanguage).isEqualTo("English");
        assertThat(savedTranslations.get(0).targetLanguage).isEqualTo("Arabic");
        assertThat(savedTranslations.get(0).sourceText).isEqualTo("This is a great morning!");
        assertThat(savedTranslations.get(0).targetText).isEqualTo("هذا صباح رائع!");
    }

}
