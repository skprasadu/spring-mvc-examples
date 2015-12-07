package com.formbuilder;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.val;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formbuilder.dto.FormInformation;
import com.formbuilder.dto.RuleValidationOutcome;
import com.formbuilder.dto.UiForm;
import com.google.common.collect.ImmutableMap;

@Service
public class FormInformationServiceImpl implements FormInformationService {

	private final FormInformationRepository repository;
	private static Logger logger = Logger.getLogger(FormInformationServiceImpl.class);

	@Autowired
	public FormInformationServiceImpl(FormInformationRepository repository) {
		this.repository = repository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.formbuilder.FormInformationService#deleteAll()
	 */
	@Override
	public void deleteRecord(String appName, int rowId, String formId) {
		// repository.deleteAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.formbuilder.FormInformationService#findAllFormTemplates()
	 */
	@Override
	public List<Map> findAllFormTemplates(String appName) throws Exception {
		val i = new AtomicInteger();
		return repository
				.findAllFormTemplates(appName)
				.map(x -> new UiForm(x.getRootnode().getId(), x.getRootnode().getId(), x.getRootnode().getLabel(), i.incrementAndGet(), x
						.getRootnode().getLabel(), x.getEntryType())).collect(Collectors.groupingBy(UiForm::getGroupBy)).entrySet().stream()
				.map(x -> ImmutableMap.of("group", x.getKey(), "tableList", x.getValue())).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.formbuilder.FormInformationService#save(com.formbuilder.dao.
	 * FormInformation)
	 */
	@Override
	public JSONObject save(JSONObject input, String appName, String formId, int dataId) {
		JSONObject json = new JSONObject();
		try {
			UiRuleValidatorService uiRuleValidatorService = new UiRuleValidatorServiceImpl(null, formId, input);
			val rvo = uiRuleValidatorService.validate(uiRuleValidatorService.getRules());
			if (UiRuleValidatorService.success(rvo)) {

				val om = new ObjectMapper();
				val formTemplate = om.readValue(input.toJSONString(), FormInformation.class);
				formTemplate.setApplication(appName);
				repository.save(formTemplate);
			}
			json.put("success", UiRuleValidatorService.success(rvo));
			json.put("outcomeList", rvo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.formbuilder.FormInformationService#getData(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Map<String, Object> getData(String appName, String formName, String dataid) throws JsonParseException, JsonMappingException,
			IOException {
		logger.debug("****appName=" + appName + " formName=" + formName + " dataid=" + dataid);
		val root = dataid.trim().equals("0") ? findTemplateByName(appName.trim(), formName.trim()) : repository.findFormData(appName.trim(),
				formName.trim(), dataid.trim());
		logger.debug("getData root=" + root);
		val map = Utils.convertAttributeToUi(root);
		logger.debug("getData map=" + map);

		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.formbuilder.FormInformationService#findAllDataByNames(java.lang.String
	 * )
	 */
	@Override
	public LinkedHashMap findAllDataByNames(String appName, String formName) {
		/*
		 * return repository.findAllFormData(formName) .map(x -> { Map map = new
		 * LinkedHashMap(); map.put(x.getId(), x.getRootnode().getLabel());
		 * return map; }) .collect(Collectors.toList());
		 */
		return null;
	}

	@Override
	public boolean hideDesigner() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getApplicationDisplayName(String appName) {
		return "Vendor Management";
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public FormInformation findTemplateByName(String appName, String name) {
		// TODO Auto-generated method stub
		logger.debug("findTemplateByName appName=" + appName + " name=" + name);
		return repository.findTemplateByName(appName, name);
	}
}
