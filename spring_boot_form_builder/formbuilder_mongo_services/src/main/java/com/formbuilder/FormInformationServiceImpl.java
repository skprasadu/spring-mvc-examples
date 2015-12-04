package com.formbuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
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

	private final FormInformationRepository repository;

	@Autowired
	public FormInformationServiceImpl(FormInformationRepository repository) {
		this.repository = repository;
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.FormInformationService#deleteAll()
	 */
	@Override
	public void deleteRecord(String appName, int rowId, int formId) {
		//repository.deleteAll();
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.FormInformationService#findAllFormTemplates()
	 */
	@Override
	public List<Map> findAllFormTemplates( String appName) throws Exception {
		//return repository.findAllFormTemplates()
		//		.map(x -> ListInformation.builder().id(x.getRootnode().getId()).name(x.getRootnode().getLabel()).build())
		//		.collect(Collectors.toList());
		return null;
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.FormInformationService#save(com.formbuilder.dao.FormInformation)
	 */
	@Override
	public JSONObject save(JSONObject input, String appName, int formId, int dataId) {
		FormInformation formTemplate = null;
		formTemplate.setType("template");
		repository.save(formTemplate);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.FormInformationService#findTemplateByName(java.lang.String)
	 */
	@Override
	public FormInformation findTemplateByName(String name) {
		return repository.findTemplateByName(name);
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.FormInformationService#save(org.json.simple.JSONObject, java.lang.String, java.lang.String)
	 */
	@Override
	public void save(JSONObject input, String formName, String dataId) {
		val formTemplate = dataId.equals("0") ? findTemplateByName(formName) : repository.findFormData(formName, dataId);

		if (dataId.equals("0")) {
			formTemplate.setId(null);
			formTemplate.setType("data");
		}
		// combine formTemplate and input
		Utils.combineFormDataAndInput(formTemplate, input);
		repository.save(formTemplate);
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.FormInformationService#getData(java.lang.String, java.lang.String)
	 */
	@Override
	public JSONObject getData(String appName, String formName, String dataid) throws JsonParseException, JsonMappingException, IOException {
		//val root = dataid.equals("0") ? findTemplateByName(formName) : repository.findFormData(formName, dataid);
		//return Utils.convertAttributeToUi(root);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.formbuilder.FormInformationService#findAllDataByNames(java.lang.String)
	 */
	@Override
	public LinkedHashMap findAllDataByNames(String appName, String formName) {
		/*return repository.findAllFormData(formName)
				.map(x -> {
					Map map = new LinkedHashMap();
					map.put(x.getId(), x.getRootnode().getLabel());
					return map;
				})
				.collect(Collectors.toList());*/
		return null;
	}

	@Override
	public boolean hideDesigner() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String getApplicationDisplayName(String appName){
		return "";
	}
}
