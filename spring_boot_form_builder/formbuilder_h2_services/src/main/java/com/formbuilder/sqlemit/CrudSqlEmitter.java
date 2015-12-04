package com.formbuilder.sqlemit;


public class CrudSqlEmitter extends SqlEmitter {

	public void emit(String appName, String tableName, String[] column, String[] relationship, int orderBy, StringBuffer ddlScripts, StringBuffer dmlScripts) {

		// Create Script
		ddlScripts.append(String.format(openCreate, tableName) + System.lineSeparator());
		String nameColumn = "name";
		int i = 0;
		for (String col : column) {
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
		if (relationship != null) {
			for (String rel : relationship) {
				String[] st = rel.split(":");
				String relName = st.length == 1 ? st[0] : st[1];
				StringBuffer sb1 = new StringBuffer(String.format(openRelation, tableName, relName) + System.lineSeparator());
				sb1.append(String.format(relIdColumn, tableName) + "," + System.lineSeparator());
				sb1.append(String.format(relIdColumn, relName) + System.lineSeparator());
				sb1.append(closeCreate + System.lineSeparator());
				ddlScripts.append(sb1.toString());
			}
		}

		// Insert Script
		insertUiForm(dmlScripts, tableName, nameColumn, orderBy, "Form");
		if (relationship != null) {
			for (String rel : relationship) {
				String[] st = rel.split(":");
				String relName = st.length == 1 ? st[0] : st[1];
				String logicalName = st.length == 2 ? st[1] : "";
				insertUiFormLink(ddlScripts, tableName, relName, logicalName, true);
			}
		}
	}
}
