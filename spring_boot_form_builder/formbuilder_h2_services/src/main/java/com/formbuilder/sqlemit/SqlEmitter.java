package com.formbuilder.sqlemit;

public abstract class SqlEmitter {
	protected String openCreate = "CREATE Table %s (id Integer,";
	protected String closeCreate = ");";
	protected String openRelation = "CREATE Table %s_%s_relationship (";
	protected String idColumn = "%s integer";
	protected String dateColumn = "%s Date";
	protected String stringColumn = "%s Varchar(50)";
	protected String relIdColumn = "%s_id integer";
	protected String insertUiFormStr = "insert into Ui_Form values(%s, %s, %s, %s, %s, %s, %s );" + System.lineSeparator();
	protected String insertUiFormLinkStr = "insert into Ui_Form_Link values(%s, %s, %s, %b);" + System.lineSeparator();

	public abstract String emit(String appName, String tableName, String[] column, String[] relationship, int orderBy);
	
	protected void setVal(StringBuffer sb, String name) {
		if (name.contains("id")) {
			sb.append(String.format(idColumn, name));
		} else if (name.contains("date")) {
			sb.append(String.format(dateColumn, name));
		} else {
			sb.append(String.format(stringColumn, name));
		}
	}
	
	protected void insertUiFormLink(StringBuffer sb, String tableName, String relName, String logicalName, boolean multiselect) {
		if(!Main.tableNameLookup.containsKey(relName)){
			Main.tableNameLookup.put(relName, Main.tableNameLookup.size()+1);
		}
		int tabId = Main.tableNameLookup.get(tableName);
		int relId = Main.tableNameLookup.get(relName);
		sb.append(String.format(insertUiFormLinkStr, tabId, relId, "'" + logicalName + "'", multiselect));

	}

	//insert into ui_form values (22, 'ldapuser_citizenship', 19, 'Ldapuser Citizenship', '', 'Rule', 1);
	protected void insertUiForm(StringBuffer sb, String tableName, String nameColumn, int orderBy, String type) {
		if(!Main.tableNameLookup.containsKey(tableName)){
			Main.tableNameLookup.put(tableName, Main.tableNameLookup.size()+1);
		}
		int tabId = Main.tableNameLookup.get(tableName);
		sb.append(String.format(insertUiFormStr, tabId, "'" + tableName + "'", orderBy, "'" + Util.getDisplayName(tableName).trim() + "'" ,"''",  "'" + type + "'", "1"));
	}

}
