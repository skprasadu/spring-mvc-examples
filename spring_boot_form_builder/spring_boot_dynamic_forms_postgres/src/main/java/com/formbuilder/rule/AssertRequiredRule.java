package com.formbuilder.rule;

import org.json.simple.JSONObject;

public class AssertRequiredRule extends Rule {

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
		return counter == split.length;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return "All the elements in " + input + " should be present";
	}

}
