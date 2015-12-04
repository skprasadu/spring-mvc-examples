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
import com.formbuilder.dao.FormInformation;

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
	public JSONObject saveForm(@RequestBody JSONObject input, @RequestParam("app_name") String appName, @RequestParam("formid") int formId, @RequestParam("dataid") int dataId)
			throws ParseException, SQLException {
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
	public Map<String, Object> getFormDataList(@PathVariable("app_name") String appName, @PathVariable("form_id") int formId) {
		
		return formTemplatesService.findAllDataByNames(appName, String.valueOf(formId));
	}

	@RequestMapping("/getFormData/{app_name}/{form_id}/{data_id}")
	public JSONObject getFormData(@PathVariable("app_name") String appName, @PathVariable("form_id") int formId, @PathVariable("data_id") int dataId) throws SQLException {
		try {
			return formTemplatesService.getData(appName, String.valueOf(formId), String.valueOf(dataId));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/deleteRecord/{app_name}/{record_id}/{form_id}", method = RequestMethod.POST)
	public int deleteRecord(@PathVariable("app_name") String appName, @PathVariable("record_id") int rowId, @PathVariable("form_id") int formId) throws SQLException {
		
		formTemplatesService.deleteRecord(appName, rowId, formId);
		return 1;
	}
	
	@RequestMapping("/getDesignOfForm/{formName}")
	public FormInformation getDesignOfForm(@PathVariable("formName") String formName) throws JsonParseException, JsonMappingException,
			IOException {
		return formTemplatesService.findTemplateByName(formName);
	}

	@RequestMapping(value = "/saveDesignOfForm", method = RequestMethod.POST)
	public void saveDesignOfForm(@RequestBody FormInformation input) {
		//formTemplatesService.save(input);
	}

}
