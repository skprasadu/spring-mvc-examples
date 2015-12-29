package com.formbuilder;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.formbuilder.dto.UiRule;


public interface UiRuleRepository extends MongoRepository<UiRule, String> {

	@Query(value = "{\"uiRuleId\": ?0}")
	UiRule findRule(String uiRuleId);
}
