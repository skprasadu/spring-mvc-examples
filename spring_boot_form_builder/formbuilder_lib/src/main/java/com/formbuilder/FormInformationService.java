package com.formbuilder;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.formbuilder.dto.FormInformation;

public interface FormInformationService {

	public abstract void deleteRecord(String appName, String rowId, String formId);

	public abstract List<Map> findAllFormTemplates(String appName) throws Exception;

	public abstract JSONObject save(JSONObject input, String appName, String formId, String dataId);

	public abstract FormInformation findTemplateByName(String appName, String name);

	public abstract Map<String, Object> getData(String appName, String formName, String dataid) throws JsonParseException, JsonMappingException,
			IOException;

	public abstract LinkedHashMap findAllDataByNames(String appName, String formName);

	public abstract boolean hideDesigner();

	public abstract String getApplicationDisplayName(String appName);

	void deleteAll();

	void saveForm(FormInformation formInformation, String appName, String formId) throws Exception;

	public abstract Map<String, Object> getFormPreviewData(String appName, String formName) throws JsonParseException, JsonMappingException, IOException;

	public abstract Map<String, Object> getQuickCreateDesignOfForms(String appName);

	public abstract void saveQuickDesignOfForm(JSONObject input, String appName) throws Exception;

}