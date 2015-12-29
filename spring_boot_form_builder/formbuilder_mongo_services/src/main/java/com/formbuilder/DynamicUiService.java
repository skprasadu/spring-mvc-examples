package com.formbuilder;

import static com.google.common.collect.ImmutableMap.of;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.formbuilder.dto.FormInformation;
import com.formbuilder.dto.ListInformation;
import com.formbuilder.dto.Node;
import com.formbuilder.dto.QuickFormInformation;
import com.formbuilder.dto.TableDetail;

@Service
public class DynamicUiService {

	private static Logger logger = Logger.getLogger(DynamicUiService.class);

	private FormInformationRepository repository;
	private UiRuleRepository uiRuleRepository;

	@Autowired
	public DynamicUiService(FormInformationRepository repository, UiRuleRepository uiRuleRepository) {
		this.repository = repository;
		this.uiRuleRepository = uiRuleRepository;
	}

	public Map<String, Object> convertAttributeToUi(FormInformation root, boolean preview)
			throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		logger.debug("in convertAttributeToUi=" + repository);
		val map = new LinkedHashMap<String, Object>();
		val map1 = new LinkedHashMap<String, Object>();
		Node node = root.getRootnode();
		map.put(node.getId(), getUiInfo(node, root.getApplication(), ""));
		if (!preview) {
			map1.put("type", "submit");
			map1.put("label", "Submit");
			map.put("submit", map1);
		}
		return map;
	}

	private Map<String, Object> getUiInfo(Node node, String appName, String parentOptId) {

		val map = new LinkedHashMap<String, Object>();
		addGenericInfo(node, parentOptId, map);

		if (compositeItem(node) && childrenPresent(node)) {
			addChildrenAttributes(node, appName, map);
		} else {
			if (parentCompositeSingle(parentOptId)) {
				addDatatypeAttributes(node, appName, map);
			} else {
				map.put("label", node.getLabel());
				map.put("type", node.getDatatype());
				addAdditionalRules(node, appName, map);
			}
		}
		return map;
	}

	private boolean parentCompositeSingle(String parentOptId) {
		return !parentOptId.isEmpty();
	}

	private void addChildrenAttributes(Node node, String appName, Map<String, Object> map) {
		val map1 = new LinkedHashMap<String, Object>();

		int cnt = node.getChildren().size();
		String curretParentOptId = "";
		if (isCompositeSelectsingle(node, cnt)) {
			curretParentOptId = addSelectionForTheGroup(node, map1, cnt);
		}
		for (Node n : node.getChildren()) {
			if (!curretParentOptId.isEmpty()) {
				map1.put(n.getId() + "-fieldset", getUiInfo(n, appName, curretParentOptId));
			} else {
				map1.put(n.getId(), getUiInfo(n, appName, curretParentOptId));
			}
		}
		map.put("fields", map1);
	}

	private boolean childrenPresent(Node node) {
		return node.getChildren() != null && !node.getChildren().isEmpty();
	}

	private String addSelectionForTheGroup(Node node, Map<String, Object> map, int cnt) {
		String curretParentOptId;
		curretParentOptId = node.getId() + "-opt";
		val map3 = new LinkedHashMap<String, Object>();
		map3.put("label", node.getLabel() + " Options");
		map3.put("type", "radio");
		Map<String, Object> map2 = new LinkedHashMap<String, Object>();
		for (Node n : node.getChildren()) {
			map2.put(n.getId() + "-opt", n.getLabel());
		}
		map3.put("values", map2);
		map.put(curretParentOptId, map3);
		return curretParentOptId;
	}

	private boolean isCompositeSelectsingle(Node node, int count) {
		return node.getDatatype().equals("Composite-selectsingle") && count > 1;
	}

	private void addGenericInfo(Node node, String parentOptId, Map<String, Object> map) {
		map.put("label", node.getLabel());
		addEnableDisableRule(node, parentOptId, map);
		map.put("type", "fieldset");
	}

	private boolean compositeItem(Node node) {
		return node.getDatatype().startsWith("Composite");
	}

	private void addDatatypeAttributes(Node node, String appName, Map<String, Object> map) {
		val map1 = new LinkedHashMap<String, Object>();
		map1.put("type", node.getDatatype());

		addAdditionalRules(node, appName, map1);
		val map2 = new LinkedHashMap<String, Object>();
		map2.put(node.getId(), map1);
		map.put("fields", map2);
	}

	private void addEnableDisableRule(Node node, String parentOptId, Map<String, Object> map) {
		if (!parentOptId.equals("")) {
			val map1 = new LinkedHashMap<String, Object>();
			map1.put("ng-disabled", "urlFormData['" + parentOptId + "'] != '" + node.getId() + "-opt'");
			map.put("attributes", map1);
		}
	}

	private void addAdditionalRules(Node node, String appName, Map<String, Object> map) {
		switch (node.getDatatype()) {
		case "number":
			map.put("minValue", node.getLowerbound());
			map.put("maxValue", node.getUpperbound());
			map.put("placeholder", "Numeric Value");
			if (node.getVal() != null) {
				float fVal = Float.parseFloat(node.getVal());
				map.put("val", fVal);
			}
			break;
		case "text":
			map.put("placeholder", "Text Value");
			if (node.getVal() != null) {
				map.put("val", node.getVal());
			}
			break;
		case "textarea":
			map.put("placeholder", "Description");
			if (node.getVal() != null) {
				map.put("val", node.getVal());
			}
			break;
		case "select":
			// Added option information
			logger.debug("node id=" + node);
			logger.debug("node id=" + node.getId());
			String tableName = getTableName(node.getId());
			logger.debug("tableName name=" + tableName);

			map.put("options",
					repository.findAllFormDataByName(appName, tableName)
							.map(x -> new ListInformation(x.getId(), x.getRootnode().getChildren().get(0).getVal()))
							.collect(Collectors.toMap(ListInformation::getId, p -> of("label", p.getName()))));

			break;
		default:
			break;
		}
	}

	public void combineFormDataAndInput(FormInformation formTemplate, JSONObject input) {
		combineFormDataAndInput(formTemplate.getRootnode(), input);
	}

	private void combineFormDataAndInput(Node node, JSONObject input) {
		Object oVal = input.get(node.getId());
		if (oVal != null) {
			node.setVal(oVal.toString());
		}

		if (node.getChildren() != null) {
			node.getChildren().forEach(x -> combineFormDataAndInput(x, input));
		}
	}

	public List<FormInformation> convertToFormInformation(QuickFormInformation quickFormInformation) {
		return quickFormInformation.getTableDetails().stream()
				.map(x -> getFormInformation(quickFormInformation.getApplicationName(), x))
				.collect(Collectors.toList());
	}

	private FormInformation getFormInformation(String applicationName, TableDetail x) {
		FormInformation formData = new FormInformation();

		formData.setApplication(applicationName);
		formData.setEntryType(x.getTableType());
		formData.setType("template");

		Node rootnode = new Node();
		rootnode.setId(x.getTableName());
		rootnode.setDatatype("Composite-selectall");
		rootnode.setLabel(getDisplayName(x.getTableName()).trim());

		List<Node> children = new ArrayList<Node>();

		for (String columnName : x.getColumnNames().split(",")) {
			Node child = new Node();

			child.setId(columnName);
			child.setDatatype(getDataType(columnName));
			child.setLabel(getDisplayName(columnName, x));

			children.add(child);
		}

		if (x.getRelationshipNames() != null) {
			for (String relationshipName : x.getRelationshipNames().split(",")) {
				// TODO Display dropdown
				Node child = new Node();
				String columnNameForRelationship = getColumnNameForRelationship(relationshipName, x);
				child.setId(columnNameForRelationship);
				child.setDatatype("select");
				child.setLabel(getColumnDisplayNameForRelationship(columnNameForRelationship));

				children.add(child);
			}
		}
		rootnode.setChildren(children);

		formData.setRootnode(rootnode);
		formData.setRuleDetails(x.getRuleDetails());

		return formData;
	}

	private String getColumnDisplayNameForRelationship(String columnNameForRelationship) {
		String[] split = columnNameForRelationship.split("__");

		String tabName = getDisplayName(split[0]).trim();
		if (split.length == 2) {
			String temp = getDisplayName(split[1]).trim();
			String part2 = temp.substring(0, temp.length() - 3);
			return tabName + " " + part2;
		} else {
			return tabName.substring(0, tabName.length() - 3);
		}
	}

	private String getColumnNameForRelationship(String relationshipName, TableDetail tableDetail) {
		String displayName = "";
		if (tableDetail.getRelationshipDisplayNames() != null) {
			Optional<String> opt = tableDetail.getRelationshipDisplayNames().stream()
					.filter(x -> x.getName().equals(relationshipName)).map(x -> x.getValue()).findAny();

			if (opt.isPresent()) {
				displayName = opt.get() + "__";
			}
		}
		return displayName + relationshipName + "_id";
	}

	private String getDataType(String columnName) {
		// TODO Auto-generated method stub
		if (columnName.endsWith("date")) {
			return "date";
		} else if (columnName.endsWith("id")) {
			return "number";
		} else {
			return "text";
		}
	}

	private String getDisplayName(String name, TableDetail tableDetail) {
		if (tableDetail.getColumnDisplayNames() != null) {
			Optional<String> opt = tableDetail.getColumnDisplayNames().stream().filter(x -> x.getName().equals(name))
					.map(x -> x.getValue()).findAny();

			if (opt.isPresent()) {
				return getDisplayName(opt.get()).trim();
			}
		}
		return getDisplayName(name).trim();
	}

	private String getDisplayName(String name) {
		// TODO Auto-generated method stub
		val split = name.split("_");
		val res = new StringBuffer();

		for (String str : split) {
			char[] stringArray = str.trim().toCharArray();
			if (stringArray.length > 0) {
				stringArray[0] = Character.toUpperCase(stringArray[0]);
				str = new String(stringArray);
			}
			res.append(str).append(" ");
		}

		return res.toString();
	}

	private String getTableName(String name) {
		String[] split = name.split("__");

		String tabName = split[0];
		if (split.length == 2) {
			tabName = split[1];
		}
		return tabName.substring(0, tabName.length() - 3);
	}
}
