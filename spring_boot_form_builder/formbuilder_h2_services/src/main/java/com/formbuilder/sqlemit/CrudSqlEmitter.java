package com.formbuilder.sqlemit;


public class CrudSqlEmitter extends SqlEmitter {

	public String emit(String tableName, String[] column, String[] relationship, int orderBy) {

		// Create Script
		StringBuffer sb = new StringBuffer(String.format(openCreate, tableName) + System.lineSeparator());
		String nameColumn = "name";
		int i = 0;
		for (String col : column) {
			String[] st = col.split(":");
			if (st.length == 2) {
				nameColumn = st[1];
			}

			setVal(sb, st[0]);
			if (i != column.length - 1) {
				sb.append(",");
			}
			sb.append(System.lineSeparator());
			i++;
		}
		sb.append(closeCreate + System.lineSeparator());
		if (relationship != null) {
			for (String rel : relationship) {
				String[] st = rel.split(":");
				String relName = st.length == 1 ? st[0] : st[1];
				StringBuffer sb1 = new StringBuffer(String.format(openRelation, tableName, relName) + System.lineSeparator());
				sb1.append(String.format(relIdColumn, tableName) + "," + System.lineSeparator());
				sb1.append(String.format(relIdColumn, relName) + System.lineSeparator());
				sb1.append(closeCreate + System.lineSeparator());
				sb.append(sb1.toString());
			}
		}

		// Insert Script
		insertUiForm(sb, tableName, nameColumn, orderBy);
		if (relationship != null) {
			for (String rel : relationship) {
				String[] st = rel.split(":");
				String relName = st.length == 1 ? st[0] : st[1];
				String logicalName = st.length == 2 ? st[1] : "";
				insertUiFormLink(sb, tableName, relName, logicalName);
			}
		}
		return sb.toString();
	}
}
