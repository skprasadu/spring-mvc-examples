package com.formbuilder.rule;

import org.json.simple.JSONObject;

public class AssertIfOneIsPresentRule extends Rule {

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return "Only one of " + input + " should be present";
	}

	@Override
	public boolean evaluate(JSONObject inputJson) {
		String []split = input.split(",");
		
		int counter = 0;
		
		for(String key: split){
			Object value = inputJson.get(key.trim());
			if(key.endsWith("_id")){
				Integer iId = getId(value);
				if(iId != null && iId != 0){
					counter++;
				}
			} else {
				if(value != null && !value.equals("")){
					counter++;
				}
			}
		}
		return counter < 2;
	}

}
