package com.formbuilder.sqlemit;

public abstract class SqlEmitter {
	protected String openCreate = "CREATE Table %s (id as Integer,";
	protected String closeCreate = ");";
	protected String openRelation = "CREATE Table %s_%s_relationship (";
	protected String idColumn = "%s as integer";
	protected String dateColumn = "%s as Date";
	protected String stringColumn = "%s as Varchar(50)";
	protected String relIdColumn = "%s_id as integer";
	protected String insertUiFormStr = "insert into Ui_Form values(%s, %s, %s, %s );" + System.lineSeparator();
	protected String insertUiFormLinkStr = "insert into Ui_Form_Link values(%s, %s, %s);" + System.lineSeparator();

	public abstract String emit(String tableName, String[] column, String[] relationship, int orderBy);
	
	protected void setVal(StringBuffer sb, String name) {
		if (name.contains("id")) {
			sb.append(String.format(idColumn, name));
		} else if (name.contains("date")) {
			sb.append(String.format(dateColumn, name));
		} else {
			sb.append(String.format(stringColumn, name));
		}
	}
	
	protected void insertUiFormLink(StringBuffer sb, String tableName, String relName, String logicalName) {
		if(!Main.tableNameLookup.containsKey(relName)){
			Main.tableNameLookup.put(relName, Main.tableNameLookup.size()+1);
		}
		int tabId = Main.tableNameLookup.get(tableName);
		int relId = Main.tableNameLookup.get(relName);
		sb.append(String.format(insertUiFormLinkStr, tabId, relId, "'" + logicalName + "'"));		

	}

	protected void insertUiForm(StringBuffer sb, String tableName, String nameColumn, int orderBy) {
		if(!Main.tableNameLookup.containsKey(tableName)){
			Main.tableNameLookup.put(tableName, Main.tableNameLookup.size()+1);
		}
		int tabId = Main.tableNameLookup.get(tableName);
		sb.append(String.format(insertUiFormStr, tabId, "'" + tableName + "'", orderBy, "'" + nameColumn + "'"));
	}

}
