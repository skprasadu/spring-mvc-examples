package com.formbuilder;

import org.json.simple.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.formbuilder.UiRuleValidatorService;
import com.formbuilder.dto.FormInformation;
import com.formbuilder.dto.UiRule;

@Service
public class UiRuleValidatorServiceImpl extends UiRuleValidatorService {
	private JdbcTemplate jdbcTemplate;

	public UiRuleValidatorServiceImpl(JdbcTemplate jdbcTemplate) {
		//super(Integer.valueOf(formId).toString(), input);
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(UiRule rule) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected FormInformation findTemplateByName(String appName, String formId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected UiRule findRule(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.formbuilder.service.UiRuleValidatorService#getRules()
	 */
	/*@Override
	public List<UiRule> getRules() {
		String sql = String.format("select * from ui_rule where ui_form_id=%s", formId);
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			UiRule rule = new UiRule();
			rule.setUiRuleId(rs.getString("ui_form_id"));
			rule.setRule(rs.getString("clause"));
			return rule;
		});
	}*/
}
