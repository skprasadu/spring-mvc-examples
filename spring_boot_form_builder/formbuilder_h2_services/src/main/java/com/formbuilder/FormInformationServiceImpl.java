package com.formbuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.val;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.formbuilder.dto.FormInformation;
import com.formbuilder.dto.RuleValidationOutcome;
import com.formbuilder.dto.UiForm;

@Service
public class FormInformationServiceImpl implements FormInformationService {

	private final UiRuleValidatorService uiRuleValidatorService;
	private final UiFormDao repository;
	private static Logger logger = Logger.getLogger(FormInformationServiceImpl.class);

	@Autowired
	public FormInformationServiceImpl(UiFormDao repository, UiRuleValidatorService uiRuleValidatorService) {
		this.repository = repository;
		this.uiRuleValidatorService = uiRuleValidatorService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.formbuilder.FormInformationService#findAllFormTemplates()
	 */
	@Override
	public List<Map> findAllFormTemplates(String appName) throws Exception {
		return repository.getFormList(appName).stream().collect(Collectors.groupingBy(UiForm::getGroupBy)).entrySet().stream().map(x -> {
			Map map1 = new LinkedHashMap();
			map1.put("group", x.getKey());
			map1.put("tableList", x.getValue());
			return map1;
		}).collect(Collectors.toList());
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
		try {
			val form = repository.getFormInfo(appName, Integer.valueOf(formName));
			val formLinks = repository.getFormLinkInfo(appName, Integer.valueOf(formName));

			val json = repository.getFormData(appName, Integer.valueOf(dataid), form, formLinks);
			val json1 = new LinkedHashMap<String, Object>();
			json1.put("fields", json);
			json1.put("type", "fieldset");
			json1.put("label", form.getDisplayName());
			val json2 = new LinkedHashMap<String, Object>();
			json2.put(form.getFormTableName(), json1);

			return json2;
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
		val formInfo = repository.getFormInfo(appName, Integer.valueOf(formName));

		List<Map> list = repository.getFormDataList(appName, Integer.valueOf(formName));

		val map = new LinkedHashMap();
		map.put("formName", formInfo.getDisplayName());
		map.put("formList", list);

		return map;
	}

	@Override
	public boolean hideDesigner() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getApplicationDisplayName(String appName) {
		return repository.getApplicationDisplayName(appName);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FormInformation findTemplateByName(String appName, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRecord(String appName, String rowId, String formId) {
		JSONObject json = new JSONObject();
		logger.debug("test delete");
		logger.debug("Table id " + formId);
		logger.debug("row id " + rowId);
		try {
			Integer iRowId = Integer.valueOf(rowId);
			Integer iFormId = Integer.valueOf(formId);
			repository.deleteRow(appName, iRowId, iFormId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public JSONObject save(JSONObject input, String appName, String formId, String dataId) {
		JSONObject json = new JSONObject();
		try {
			val list = uiRuleValidatorService.evaluate(appName, formId, input);
			if (list.isEmpty()) {
				Integer iDataId = Integer.valueOf(dataId);
				Integer iFormId = Integer.valueOf(formId);
				repository.saveFormData(appName, iFormId, iDataId, input);
			}
			json.put("success", list.isEmpty());
			json.put("outcomeList", list);
		} catch (ParseException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public void saveForm(FormInformation formInformation, String appName, String formId) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public Map<String, Object> getFormPreviewData(String appName, String formName) throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getQuickCreateDesignOfForms(String appName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveQuickDesignOfForm(JSONObject input, String appName) {
		// TODO Auto-generated method stub
		
	}
}
