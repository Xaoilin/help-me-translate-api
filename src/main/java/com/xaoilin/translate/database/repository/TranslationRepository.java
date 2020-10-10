package com.xaoilin.translate.database.repository;

import com.xaoilin.translate.database.model.SavedTranslations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends CrudRepository<SavedTranslations, Long> {
}
