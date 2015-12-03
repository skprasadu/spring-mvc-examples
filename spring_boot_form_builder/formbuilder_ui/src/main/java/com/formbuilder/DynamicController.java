package com.formbuilder;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.val;

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
import com.formbuilder.dao.ListInformation;

@RestController
@EnableAutoConfiguration
public class DynamicController {

	@Autowired
	private FormInformationService formTemplatesService;

	@RequestMapping("/disableDesigner")
	public boolean disableDesigner() {
		return formTemplatesService.hideDesigner();
	}

	@RequestMapping("/getDataList/{formName}")
	public Map<String, Object> getDataList(@PathVariable("formName") String formName) throws Exception {
		val map = new LinkedHashMap();
		map.put("formList", formTemplatesService.findAllDataByNames(formName));
		val form = formTemplatesService.findTemplateByName(formName);
		map.put("formName", form.getRootnode().getLabel());
		return map;
	}

	@RequestMapping("/getTemplateList")
	public List<ListInformation> getTemplateList() throws Exception {
		return formTemplatesService.findAllFormTemplates();
	}

	@RequestMapping("/getFormData/{formName}/{dataid}")
	public Map<String, Object> getFormData(@PathVariable("formName") String formName, @PathVariable("dataid") String dataid)
			throws JsonParseException, JsonMappingException, IOException {
		return formTemplatesService.getData(formName, dataid);
	}

	@RequestMapping("/getDesignOfForm/{formName}")
	public FormInformation getDesignOfForm(@PathVariable("formName") String formName) throws JsonParseException, JsonMappingException,
			IOException {
		return formTemplatesService.findTemplateByName(formName);
	}

	@RequestMapping(value = "/saveDesignOfForm", method = RequestMethod.POST)
	public void saveDesignOfForm(@RequestBody FormInformation input) {
		formTemplatesService.save(input);
	}

	@RequestMapping(value = "/saveForm", method = RequestMethod.POST)
	public void saveForm(@RequestBody JSONObject input, @RequestParam("formid") String formId, @RequestParam("dataid") String dataId) {
		formTemplatesService.save(input, formId, dataId);
	}
}
