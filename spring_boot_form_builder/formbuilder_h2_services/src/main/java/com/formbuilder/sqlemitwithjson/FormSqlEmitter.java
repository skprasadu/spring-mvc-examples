package com.formbuilder.sqlemitwithjson;

import com.formbuilder.dto.TableDetail;

public class FormSqlEmitter extends SqlEmitter {

	public void emit(String appName, TableDetail tableDetail, int orderBy, StringBuffer ddlScripts, StringBuffer dmlScripts) {

		// Create Script
		ddlScripts.append(String.format(openCreate, tableDetail.getTableName()) + System.lineSeparator());
		String nameColumn = "name";
		int i = 0;
		String[] column = tableDetail.getColumnNames().split(",");
		for (String col : tableDetail.getColumnNames().split(",")) {
			String[] st = col.split(":");
			if (st.length == 2) {
				nameColumn = st[1];
			}

			setVal(ddlScripts, st[0]);
			if (i != column.length - 1) {
				ddlScripts.append(",");
			}
			ddlScripts.append(System.lineSeparator());
			i++;
		}
		ddlScripts.append(closeCreate + System.lineSeparator());
		if (tableDetail.getRelationshipNames() != null) {
			String[] relationship = tableDetail.getRelationshipNames().split(",");
			if (relationship != null) {
				for (String rel : relationship) {
					String[] st = rel.split(":");
					String relName = st.length == 1 ? st[0] : st[1];
					StringBuffer sb1 = new StringBuffer(String.format(openRelation, tableDetail.getTableName(), relName)
							+ System.lineSeparator());
					sb1.append(String.format(relIdColumn, tableDetail.getTableName()) + "," + System.lineSeparator());
					sb1.append(String.format(relIdColumn, relName) + System.lineSeparator());
					sb1.append(closeCreate + System.lineSeparator());
					ddlScripts.append(sb1.toString());
				}
			}
		}
		// Insert Script
		insertUiForm(dmlScripts, tableDetail.getTableName(), nameColumn, orderBy, "Form", tableDetail.getRuleDetails());
		if (tableDetail.getRelationshipNames() != null) {
			String[] relationship = tableDetail.getRelationshipNames().split(",");
			for (String rel : relationship) {
				String[] st = rel.split(":");
				String relName = st.length == 1 ? st[0] : st[1];
				String logicalName = st.length == 2 ? st[1] : "";
				insertUiFormLink(dmlScripts, tableDetail.getTableName(), relName, logicalName, true);
			}
		}
	}
}
