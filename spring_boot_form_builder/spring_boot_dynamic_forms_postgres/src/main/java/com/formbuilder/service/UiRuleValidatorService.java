package com.formbuilder.service;

import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.formbuilder.dto.UiRule;
import com.formbuilder.rule.Rule;

public class UiRuleValidatorService {
	private JdbcTemplate jdbcTemplate;
	private int formId;
	private JSONObject input;
	private static final RuleValidationOutcome successOutcome = new RuleValidationOutcome("success", "");

	public UiRuleValidatorService(JdbcTemplate jdbcTemplate, int formId, JSONObject input) {
		this.jdbcTemplate = jdbcTemplate;
		this.formId = formId;
		this.input = input;
	}

	public List<RuleValidationOutcome> validate(List<UiRule> rules) {
		try {
			return rules.stream().map(x -> validateRule(x)).collect(Collectors.toList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private RuleValidationOutcome validateRule(UiRule x) {
		try {
			Rule clauseDetails = evaluate(x.getClause());

			if (clauseDetails != null && clauseDetails.evaluate(input)) {
				return successOutcome;
			} else {
				return new RuleValidationOutcome("failure", clauseDetails.getErrorMessage());
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | ParseException e) {
			e.printStackTrace();
			
			return new RuleValidationOutcome("failure", e.getMessage() + ", please contact adminstrator");
		}
	}

	private Rule evaluate(String clause) throws InstantiationException, IllegalAccessException, ClassNotFoundException, ParseException {
		if (clause != null && !clause.equals("")) {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(clause);
			String action = (String) json.get("action");
			String input = (String) json.get("input");
			if (action != null && input != null) {
				String className = Character.toString(action.charAt(0)).toUpperCase() + action.substring(1);

				Rule rule = (Rule) Class.forName("com.formbuilder.rule." + className + "Rule").newInstance();
				rule.setInput(input);
				return rule;
			}
		}
		return null;
	}

	public List<UiRule> getRules() {
		String sql = String.format("select * from ui_rule where ui_form_id=%d", formId);
		return jdbcTemplate.query(sql, (rs, rowNum) -> new UiRule(rs.getInt("ui_form_id"), rs.getString("clause")));
	}

	public static boolean success(List<RuleValidationOutcome> rvo) {
		return (rvo.stream().filter(x -> x.getOutcome().equals("failure")).count() == 0);
	}
}
