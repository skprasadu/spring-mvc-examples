package com.formbuilder;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.formbuilder.dto.UiRule;

public class UiRuleValidatorServiceImpl extends UiRuleValidatorService {
	private MongoTemplate mongoTemplate;

	public UiRuleValidatorServiceImpl(MongoTemplate mongoTemplate, String formId, JSONObject input) {
		super(formId, input);
		this.mongoTemplate = mongoTemplate;
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.service.UiRuleValidatorService#getRules()
	 */
	@Override
	public List<UiRule> getRules() {
		String sql = String.format("select * from ui_rule where ui_form_id=%d", formId);
		return null;
	}
}
