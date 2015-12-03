package com.formbuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.val;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.formbuilder.dao.FormInformation;
import com.formbuilder.dao.ListInformation;

@Service
public class FormInformationServiceImpl implements FormInformationService {

	//private final FormInformationRepository repository;
	private final UiFormDao repository;

	@Autowired
	public FormInformationServiceImpl(UiFormDao repository) {
		this.repository = repository;
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.FormInformationService#findAllFormTemplates()
	 */
	@Override
	public List<ListInformation> findAllFormTemplates() throws Exception {
		return  repository.getFormList("").stream()
				.map(x -> ListInformation.builder().id(String.valueOf(x.getId())).name(x.getDisplayName()).build())
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.FormInformationService#save(com.formbuilder.dao.FormInformation)
	 */
	@Override
	public void save(FormInformation formTemplate) {
		//formTemplate.setType("template");
		//repository.save(formTemplate);
		//repository.saveFormData("", formId, dataId, input)
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.FormInformationService#findTemplateByName(java.lang.String)
	 */
	@Override
	public FormInformation findTemplateByName(String name) {
		//return repository.findTemplateByName(name);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.FormInformationService#save(org.json.simple.JSONObject, java.lang.String, java.lang.String)
	 */
	@Override
	public void save(JSONObject input, String formName, String dataId) {
		//val formTemplate = dataId.equals("0") ? findTemplateByName(formName) : repository.findFormData(formName, dataId);

		//if (dataId.equals("0")) {
		//	formTemplate.setId(null);
		//	formTemplate.setType("data");
		//}
		// combine formTemplate and input
		//Utils.combineFormDataAndInput(formTemplate, input);
		try {
			repository.saveFormData("", Integer.valueOf(formName), Integer.valueOf(dataId), input);
		} catch (NumberFormatException | ParseException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.FormInformationService#getData(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> getData(String formName, String dataid) throws JsonParseException, JsonMappingException, IOException {
		val form = repository.getFormInfo("", Integer.valueOf(formName));
		val formLinks = repository.getFormLinkInfo("", Integer.valueOf(formName));

		try {
			val json = repository.getFormData("", Integer.valueOf(dataid), form, formLinks);
			val json1 = new LinkedHashMap();
			json1.put("fields", json);
			json1.put("type", "fieldset");
			json1.put("label", form.getDisplayName());
			val json2 = new LinkedHashMap();
			json2.put(form.getFormTableName(), json1);
			return json2;
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.FormInformationService#findAllDataByNames(java.lang.String)
	 */
	@Override
	public List<Map> findAllDataByNames(String formName) {
		return repository.getFormDataList("", Integer.valueOf(formName));
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hideDesigner() {
		// TODO Auto-generated method stub
		return true;
	}
}
