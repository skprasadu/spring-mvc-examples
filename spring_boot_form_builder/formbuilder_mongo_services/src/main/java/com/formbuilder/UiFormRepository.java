package com.formbuilder;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.formbuilder.dto.UiRule;


public interface UiFormRepository extends MongoRepository<UiRule, String> {

	@Query(value = "{\"uiFormId\": ?0}")
	Stream<UiRule> findAllRulesForForm(String uiFormId);
}
