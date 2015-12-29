package com.formbuilder.rule;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CheckIfOneOfTheseIsPresentValueIsPresentRule extends Rule {

	@Override
	public String computeDroolsRule() {
		try {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(nameListToValidate);

			String whenPlaceholder = "this[\"%s\"] %s null && this[\"%s\"] %s \"%s\"";
			String thenPlaceholder = "$map.get(\"%s\") %s null && $map.get(\"%s\") %s \"%s\"";

			String when = (String) json.get("when");
			String[] whenSplit = when.split(" ");
			String then = (String) json.get("then");
			String[] thenSplit = then.split(" ");
			System.out.println("when=" + when);
			System.out.println("then=" + then);
			if (whenSplit.length == 3 && thenSplit.length == 3) {
				String whenClause = String.format(whenPlaceholder, whenSplit[0], whenSplit[1], whenSplit[0], whenSplit[1], whenSplit[2]);
				String rhs = rhsVal(thenSplit);
				String thenClause = String.format(thenPlaceholder, thenSplit[0], thenSplit[1], thenSplit[0], thenSplit[1], rhs);
				System.out.println("whenClause=" + whenClause);
				System.out.println("thenClause="+ thenClause);

				String st = String.format(droolString, whenClause, thenClause, then + " condition not met");
				System.out.println(st);
				return st;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	private String rhsVal(String[] thenSplit) {
		String dat = "";
		if (thenSplit[2].equals("empty")) {
			dat = "";
		}
		return dat;
	}
}
