package com.formbuilder;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.formbuilder.dto.UiRule;


public interface UiRuleRepository extends CrudRepository<UiRule, Long> {

	@Query(value = "select * from uiRules where uiRuleId=?0", nativeQuery=true)
	UiRule findRule(String uiRuleId);
}
