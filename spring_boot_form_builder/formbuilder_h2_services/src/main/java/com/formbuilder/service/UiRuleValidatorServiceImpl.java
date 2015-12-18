package com.formbuilder.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import com.formbuilder.UiRuleValidatorService;
import com.formbuilder.dto.UiRule;

public class UiRuleValidatorServiceImpl extends UiRuleValidatorService {
	private JdbcTemplate jdbcTemplate;

	public UiRuleValidatorServiceImpl(JdbcTemplate jdbcTemplate, int formId, JSONObject input) {
		super( Integer.valueOf(formId).toString(), input);
		this.jdbcTemplate = jdbcTemplate;
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.service.UiRuleValidatorService#getRules()
	 */
	@Override
	public List<UiRule> getRules() {
		String sql = String.format("select * from ui_rule where ui_form_id=%s", formId);
		return jdbcTemplate.query(sql, (rs, rowNum) -> new UiRule("", rs.getString("ui_form_id"), rs.getString("clause")));
	}
}
