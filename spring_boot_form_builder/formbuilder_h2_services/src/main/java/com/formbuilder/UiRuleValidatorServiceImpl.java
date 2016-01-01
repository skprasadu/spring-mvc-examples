package com.formbuilder;

import lombok.val;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.formbuilder.dto.NameValue;
import com.formbuilder.dto.UiRule;

@Service
public class UiRuleValidatorServiceImpl extends UiRuleValidatorService {
	private final JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(UiRuleValidatorServiceImpl.class);
	@Autowired
	public UiRuleValidatorServiceImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(UiRule rule) {
	}

	@Override
	protected NameValue findTemplateByName(String appName, String formId) {
		val sql = "select validator, validator_inputs from ui_form where id=? and ui_app_id=?";
		logger.debug("appName=" + appName + " formId=" + formId);
		System.out.println("**********appName=" + appName + " formId=" + formId);
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
			NameValue nameValue = new NameValue();
			nameValue.setName(rs.getString("validator"));
			nameValue.setValue(rs.getString("validator_inputs"));
			return nameValue;
		}, formId, 1);
	}

	@Override
	protected UiRule findRule(String name) {
		val sql = "select * from uiRules where uiRuleId=?";
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
			UiRule uiRule = new UiRule();
			uiRule.setUiRuleId(rs.getString("uiRuleId"));
			uiRule.setRule(rs.getString("rule"));
			return uiRule;
		}, name);
	}
}
