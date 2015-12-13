package com.formbuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.formbuilder.dto.FormInformation;

@RestController
@EnableAutoConfiguration
public class DynamicController {

	@Autowired
	private FormInformationService formTemplatesService;

	private static Logger logger = Logger.getLogger(DynamicController.class);

	@RequestMapping("/disableDesigner")
	public boolean disableDesigner() {
		return formTemplatesService.hideDesigner();
	}

	@RequestMapping(value = "/saveForm", method = RequestMethod.POST)
	public JSONObject saveForm(@RequestBody JSONObject input, @RequestParam("app_name") String appName, @RequestParam("formid") String formId,
			@RequestParam("dataid") String dataId) throws ParseException, SQLException {
		return formTemplatesService.save(input, appName, formId, dataId);
	}

	@RequestMapping("/getApplicationDisplayName/{app_name}")
	public String getApplicationDisplayName(@PathVariable("app_name") String appName) {
		return formTemplatesService.getApplicationDisplayName(appName);
	}

	@RequestMapping("/getFormList/{app_name}")
	public List<Map> getFormList(@PathVariable("app_name") String appName) {

		try {
			return formTemplatesService.findAllFormTemplates(appName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/getFormDataList/{app_name}/{form_id}")
	public Map<String, Object> getFormDataList(@PathVariable("app_name") String appName, @PathVariable("form_id") String formId) {

		return formTemplatesService.findAllDataByNames(appName, formId);
	}

	@RequestMapping("/getFormData/{app_name}/{form_id}/{data_id}")
	public Map<String, Object> getFormData(@PathVariable("app_name") String appName, @PathVariable("form_id") String formId,
			@PathVariable("data_id") String dataId) throws SQLException {
		try {
			return formTemplatesService.getData(appName, formId, String.valueOf(dataId));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/deleteRecord/{app_name}/{record_id}/{form_id}", method = RequestMethod.POST)
	public int deleteRecord(@PathVariable("app_name") String appName, @PathVariable("record_id") String rowId,
			@PathVariable("form_id") String formId) throws SQLException {

		formTemplatesService.deleteRecord(appName, rowId, formId);
		return 1;
	}

	@RequestMapping("/getDesignOfForm/{app_name}/{formName}")
	public FormInformation getDesignOfForm(@PathVariable("app_name") String appName, @PathVariable("formName") String formName)
			throws JsonParseException, JsonMappingException, IOException {
		return formTemplatesService.findTemplateByName(appName, formName);
	}

	@RequestMapping(value = "/saveDesignOfForm", method = RequestMethod.POST)
	public void saveDesignOfForm(@RequestBody FormInformation formInformation, @RequestParam("app_name") String appName,
			@RequestParam("formid") String formId) throws Exception {
		formTemplatesService.saveForm(formInformation, appName, formId);
	}

	@RequestMapping(value = "/getFormPreviewData/{app_name}/{formName}")
	public Map<String, Object> getFormPreviewData(@PathVariable("app_name") String appName, @PathVariable("formName") String formName)
			throws JsonParseException, JsonMappingException, IOException {
		return formTemplatesService.getFormPreviewData(appName, formName);
	}
	
	
	@RequestMapping(value = "/getQuickCreateDesignOfForms/{app_name}")
	public Map<String, Object> getQuickCreateDesignOfForms(@PathVariable("app_name") String appName)
			throws JsonParseException, JsonMappingException, IOException {
		return formTemplatesService.getQuickCreateDesignOfForms(appName);
	}
	
	@RequestMapping(value = "/saveQuickDesignOfForm", method = RequestMethod.POST)
	public void saveQuickDesignOfForm(@RequestBody JSONObject input, @RequestParam("app_name") String appName) throws Exception {
		formTemplatesService.saveQuickDesignOfForm(input, appName);
	}
}
