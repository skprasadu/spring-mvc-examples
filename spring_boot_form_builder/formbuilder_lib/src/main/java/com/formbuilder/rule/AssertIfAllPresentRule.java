package com.formbuilder.rule;

import org.json.simple.JSONObject;

public class AssertIfAllPresentRule extends Rule implements Evaluator{

	@Override
	public String computeDroolsRule() {
		//String inputConditions = generateConditions(formInputForDrool);
		return String.format(droolString, nameListToValidate);
	}

	@Override
	public boolean eval() {
		int count = 0;

		String[] names = nameListToValidate.split(",");
		for (String name : names) {
			if (data.get(name.trim()) != null && !data.get(name.trim()).isEmpty()) {
				count++;
			}
		}
		
		System.out.println("count=" + count);
		return count == names.length;
	}
}
