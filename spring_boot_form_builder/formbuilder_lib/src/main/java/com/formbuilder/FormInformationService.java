package com.formbuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.formbuilder.dao.FormInformation;
import com.formbuilder.dao.ListInformation;

public interface FormInformationService {
	
	public abstract void deleteAll();

	public abstract List<ListInformation> findAllFormTemplates() throws Exception;

	public abstract void save(FormInformation formTemplate);

	public abstract FormInformation findTemplateByName(String name);

	public abstract void save(JSONObject input, String formName, String dataId);

	public abstract Map<String, Object> getData(String formName, String dataid) throws JsonParseException, JsonMappingException, IOException;

	public abstract List<Map> findAllDataByNames(String formName);
	
	public abstract boolean hideDesigner();

}