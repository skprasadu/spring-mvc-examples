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
import com.formbuilder.dto.QuickFormInformation;
import com.formbuilder.dto.UiForm;
import com.google.common.collect.ImmutableMap;
import static com.formbuilder.Utils.*;

@Service
public class FormInformationServiceImpl implements FormInformationService {

	private static Logger logger = Logger.getLogger(FormInformationServiceImpl.class);

	private final FormInformationRepository repository;
	private final UiRuleValidatorServiceImpl uiRuleValidatorService;

	@Autowired
	public FormInformationServiceImpl(FormInformationRepository repository, UiRuleValidatorServiceImpl uiRuleValidatorService) {
		this.repository = repository;
		this.uiRuleValidatorService = uiRuleValidatorService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.formbuilder.FormInformationService#deleteAll()
	 */
	@Override
	public void deleteRecord(String appName, String rowId, String formId) {
		repository.delete(rowId);
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
	public JSONObject save(JSONObject input, String appName, String formId, String dataId) {
		JSONObject json = new JSONObject();
		uiRuleValidatorService.setInput(input);
		uiRuleValidatorService.setFormId(formId);
		val rvo = uiRuleValidatorService.validate(uiRuleValidatorService.getRules());
		if (UiRuleValidatorService.success(rvo)) {
			val formTemplate = dataId.equals("0") ? findTemplateByName(appName, formId) : repository.findFormData(appName, formId, dataId);

			logger.debug("input" + input.toJSONString());
			if (dataId.equals("0")) {
				formTemplate.setId(null);
				formTemplate.setType("data");
			}
			// combine formTemplate and input
			combineFormDataAndInput(formTemplate, input);
			formTemplate.setApplication(appName);
			repository.save(formTemplate);
		}
		json.put("success", UiRuleValidatorService.success(rvo));
		json.put("outcomeList", rvo);
		return json;
	}

	@Override
	public void saveForm(FormInformation formInformation, String appName, String formId) throws Exception {
		if (formId.equals("0")) {
			FormInformation formInformation1 = repository.findTemplateByName(formInformation.getApplication(), formInformation.getRootnode()
					.getId());

			if (formInformation1 != null) {
				throw new Exception("Form with same name exists");
			}
		}
		repository.save(formInformation);
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
		val map = convertAttributeToUi(root, false);
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

		List<Map> list = repository.findAllFormData(appName, formName).map(x -> {
			Map map = new LinkedHashMap();
			map.put("id", x.getId());
			map.put("label", x.getRootnode().getLabel());

			x.getRootnode().getChildren().forEach(x1 -> {
				map.put(x1.getLabel(), x1.getVal());
			});
			return map;
		}).collect(Collectors.toList());

		val map = new LinkedHashMap();
		if (!list.isEmpty()) {
			map.put("formName", list.get(0).get("label"));
		}
		map.put("formList", list);
		return map;
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

	@Override
	public Map<String, Object> getFormPreviewData(String appName, String formName) throws JsonParseException, JsonMappingException, IOException {
		val root = findTemplateByName(appName.trim(), formName.trim());
		val map = convertAttributeToUi(root, true);

		return map;
	}

	@Override
	public Map<String, Object> getQuickCreateDesignOfForms(String appName) {
		// TODO Auto-generated method stub
		//{"fuelload":{"label":"Fuel Load","type":"fieldset","fields":{"load-fieldset":{"label":"Load","type":"fieldset","fields":{"load":{"type":"number","minValue":0,"maxValue":0,"placeholder":"Numeric Value"}}}}}}
		
		val map1 = new LinkedHashMap<String, String>();
		map1.put("type", "textarea");
		map1.put("label", "Quick Data Entry");
		val map2 = new LinkedHashMap<String, Object>();
		map2.put("quickdata", map1);
		
		val map3 = new LinkedHashMap<String, Object>();
		map3.put("fields", map2);
		map3.put("label", "Quick Data Entry Form");
		map3.put("type", "fieldset");
		
		val map4 = new LinkedHashMap<String, Object>();
		map4.put("quickdata", map3);
		val map5 = new LinkedHashMap<String, Object>();
		map5.put("type", "submit");
		map5.put("label", "Submit");
		map4.put("submit", map5);

		return map4;
	}

	@Override
	public void saveQuickDesignOfForm(JSONObject input, String appName) throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		val mapper = new ObjectMapper();
		String quickData = (String)input.get("quickdata");
		val quickFormInformation = mapper.readValue(quickData, QuickFormInformation.class);
		
		val list = convertToFormInformation(quickFormInformation);
		logger.debug(list);
	}
}
