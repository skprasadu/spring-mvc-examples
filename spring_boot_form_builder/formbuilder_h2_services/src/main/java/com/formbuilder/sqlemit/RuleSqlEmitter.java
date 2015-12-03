package com.formbuilder.sqlemit;

public class RuleSqlEmitter extends SqlEmitter {
	
	@Override
	public String emit(String tableName, String[] column, String[] relationship, int orderBy) {
		StringBuffer sb = new StringBuffer(String.format(openCreate, tableName) + System.lineSeparator());
		String nameColumn = "name";
		for (String col : column) {
			String[] st = col.split(":");
			if(st.length == 2){
				nameColumn = st[1];
			}
			setVal(sb, st[0]);
			sb.append("," + System.lineSeparator());
		}
		
		int i =0;
		for (String rel : relationship) {
			String[] st = rel.split(":");
			String relName = st.length == 1 ? st[0] : st[1];
			sb.append(String.format(relIdColumn, relName) );
			if (i != relationship.length - 1) {
				sb.append(",");
			}
			sb.append(System.lineSeparator());
			i++;
		}

		sb.append(closeCreate + System.lineSeparator());
		
		//Insert Script
		insertUiForm(sb, tableName, nameColumn, orderBy);
		for (String rel : relationship) {
			String[] st = rel.split(":");
			String relName = st.length == 1 ? st[0] : st[1];
			String logicalName = st.length == 2 ? st[1] : "";
			insertUiFormLink(sb, tableName, relName, logicalName);
		}
		
		return sb.toString();
	}
}
