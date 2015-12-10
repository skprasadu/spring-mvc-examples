package com.formbuilder;

import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formbuilder.dto.UiRule;

@Service
public class UiRuleValidatorServiceImpl extends UiRuleValidatorService {
	private final UiFormRepository repository;

	@Autowired
	public UiRuleValidatorServiceImpl(UiFormRepository repository) {
		super("", null);
		this.repository = repository;
	}

	
	public void setFormId(String formId){
		this.formId = formId;
	}
	
	public void setInput(JSONObject input){
		this.input = input;
	}

	public String getFormId(){
		return formId;
	}
	
	public JSONObject getInput(){
		return input;
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.service.UiRuleValidatorService#getRules()
	 */
	@Override
	public List<UiRule> getRules() {
		return repository.findAllRulesForForm(formId).collect(Collectors.toList());
	}
	
	public void save(UiRule rule){
		repository.save(rule);
	}
}
