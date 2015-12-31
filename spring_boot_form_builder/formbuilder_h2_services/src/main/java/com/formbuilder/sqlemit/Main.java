package com.formbuilder.sqlemit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONObject;

//import org.apache.log4j.Logger;

public class Main {
	// private static Logger logger = Logger.getLogger(Main.class);
	public static Map<String, Integer> tableNameLookup = new LinkedHashMap<String, Integer>();

	public static void main(String[] args) throws FileNotFoundException, IOException, InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		System.out.println("Hello");
		
		JSONObject json = new JSONObject();
		
		json.put("test", "out");
		
		System.out.println("test=" + json.get("test"));

		Properties prop = new Properties();
		prop.load(new FileInputStream(args[0]));
		StringBuffer ddlScripts = new StringBuffer();
		StringBuffer dmlScripts = new StringBuffer();
		int i = 1;
		for (Enumeration e = prop.keys(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			String[] appSplit = key.split("\\.");
			if (i == 1) {
				Util.createGenericTables(appSplit[0], ddlScripts, dmlScripts);
			}
			String sVal = prop.getProperty(key);

			System.out.println("sVal=" + sVal);
			String[] split = sVal.split("#");
			// assertTrue(split.length > 1);

			SqlEmitter emitter = (SqlEmitter) Class.forName("com.formbuilder.sqlemit." + split[0] + "SqlEmitter").newInstance();

			// assertNotNull(emitter);

			emitter.emit(appSplit[0], appSplit[1], split[1].split(","), split.length == 2 ? null : split[2].split(","), i, ddlScripts,
					dmlScripts);
			i++;
		}

		System.out.println("Create Script:" + ddlScripts.toString());
		System.out.println("Insert Script:" + dmlScripts.toString());
		
		File file = new File(".");
		System.out.println("file path:" + file.getAbsolutePath());

		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("../formbuilder_h2_services/src/main/resources/schema.sql"), "utf-8"))) {
			writer.write(ddlScripts.toString());
		}

		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("../formbuilder_h2_services/src/main/resources/data.sql"), "utf-8"))) {
			writer.write(dmlScripts.toString());
		}

		System.out.println("Create Script:" + ddlScripts.toString());
		System.out.println("Insert Script:" + dmlScripts.toString());
	}

}
