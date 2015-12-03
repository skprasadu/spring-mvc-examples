package com.formbuilder.sqlemit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

//import org.apache.log4j.Logger;

public class Main {
	//private static Logger logger = Logger.getLogger(Main.class);
	public static Map<String, Integer> tableNameLookup = new LinkedHashMap<String, Integer>();

	public static void main(String[] args) throws FileNotFoundException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		System.out.println("Hello");

		Properties prop = new Properties();
		prop.load(new FileInputStream(args[0]));
		StringBuffer sb = new StringBuffer();
		int i = 1;
		for (Enumeration e = prop.keys(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			String []appSplit = key.split("\\.");
			if(i == 1){
				sb.append(Util.createGenericTables(appSplit[0]));
			}
			String sVal = prop.getProperty(key);

			System.out.println("sVal=" + sVal);
			String[] split = sVal.split("#");
			//assertTrue(split.length > 1);
			
			SqlEmitter emitter = (SqlEmitter) Class.forName("com.formbuilder.sqlemit." + split[0] + "SqlEmitter").newInstance();
			
			//assertNotNull(emitter);

			sb.append(emitter.emit(appSplit[0], appSplit[1], split[1].split(","), split.length == 2 ? null : split[2].split(","), i));
			i++;
		}
		
		System.out.println("Script:" + sb.toString());
	}

}
