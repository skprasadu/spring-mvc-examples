package com.formbuilder;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formbuilder.dto.FormInformation;
import com.formbuilder.dto.NameValue;
import com.formbuilder.dto.UiRule;

@Service
public class UiRuleValidatorServiceImpl extends UiRuleValidatorService {
	final UiRuleRepository uiRuleRepository;
	final FormInformationRepository formInformationRepository;

	@Autowired
	public UiRuleValidatorServiceImpl(UiRuleRepository uiRuleRepository, FormInformationRepository formInformationRepository) {
		this.uiRuleRepository = uiRuleRepository;
		this.formInformationRepository = formInformationRepository;
	}

	@Override
	public void save(UiRule rule) {
		uiRuleRepository.save(rule);
	}

	@Override
	protected NameValue findTemplateByName(String appName, String formId) {
		FormInformation info = formInformationRepository.findTemplateByName(appName, formId);
		return info.getRuleDetails().get(0);
	}

	@Override
	protected UiRule findRule(String name) {
		return uiRuleRepository.findRule(name);
	}
}
