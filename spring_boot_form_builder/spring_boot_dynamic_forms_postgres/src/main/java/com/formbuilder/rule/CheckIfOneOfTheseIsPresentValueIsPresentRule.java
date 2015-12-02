package com.formbuilder.rule;

import org.json.simple.JSONObject;

public class CheckIfOneOfTheseIsPresentValueIsPresentRule extends Rule {

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		String []split = input.split(",");
		String subst = input.substring(0,  input.length() - split[split.length-1].length());
		return "If one of " + subst + " is present than " + split[split.length-1] + " should be present";
	}

	@Override
	public boolean evaluate(JSONObject inputJson) {
		String []split = input.split(",");
		int counter = 0;

		for(int i=0; i< split.length-1; i ++){
			Object value = inputJson.get(split[i].trim());
			if(split[i].endsWith("_id")){
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
		
		String lastItemVal = (String) inputJson.get(split[split.length - 1].trim());
		
		if(counter == 0 || counter == 1 && (lastItemVal != null && !lastItemVal.equals(""))){
			return true;
		}
		return false;
	}
}
