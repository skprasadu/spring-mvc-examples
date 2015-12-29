package com.formbuilder;

import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.formbuilder.dto.RuleValidationOutcome;
import com.formbuilder.dto.UiRule;
import com.formbuilder.rule.Rule;

public abstract class UiRuleValidatorService {
	protected String formId;
	protected JSONObject input;
	private static final RuleValidationOutcome successOutcome = new RuleValidationOutcome("success", "");

	public UiRuleValidatorService(String formId, JSONObject input) {
		this.formId = formId;
		this.input = input;
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.service.UiRuleValidatorService#validate(java.util.List)
	 */
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
		/*try {
			Rule clauseDetails = evaluate(x.getRule());

			if (clauseDetails != null && clauseDetails.evaluate(input)) {
				return successOutcome;
			} else {
				return new RuleValidationOutcome("failure", clauseDetails.getErrorMessage());
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | ParseException e) {
			e.printStackTrace();
			
			return new RuleValidationOutcome("failure", e.getMessage() + ", please contact adminstrator");
		}*/
		return successOutcome;
	}

	protected Rule evaluate(String clause) throws InstantiationException, IllegalAccessException, ClassNotFoundException, ParseException {
		/*if (clause != null && !clause.equals("")) {
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
		}*/
		return null;
	}


	public abstract List<UiRule> getRules();
	
	public static boolean success(List<RuleValidationOutcome> rvo) {
		return (rvo.stream().filter(x -> x.getOutcome().equals("failure")).count() == 0);
	}
}