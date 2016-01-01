package com.formbuilder.sqlemitwithjson;

import static com.formbuilder.sqlemitwithjson.Util.createGenericTables;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.val;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formbuilder.dto.QuickFormInformation;

//import org.apache.log4j.Logger;

public class Main {
	// private static Logger logger = Logger.getLogger(Main.class);
	public static Map<String, Integer> tableNameLookup = new LinkedHashMap<String, Integer>();

	public static void main(String[] args) throws Exception {
		val mapper = new ObjectMapper();
		String quickData = readFile(args[0]);
		val quickFormInformation = mapper.readValue(quickData, QuickFormInformation.class);

		StringBuffer ddlScripts = new StringBuffer();
		StringBuffer dmlScripts = new StringBuffer();
		createGenericTables(quickFormInformation.getApplicationName(), ddlScripts, dmlScripts);

		AtomicInteger ai = new AtomicInteger();
		quickFormInformation.getTableDetails().stream().forEach(x -> {
			try {
				SqlEmitter emitter;
				emitter = (SqlEmitter) Class.forName("com.formbuilder.sqlemitwithjson." + x.getTableType() + "SqlEmitter").newInstance();

				emitter.emit(quickFormInformation.getApplicationName(), x, ai.incrementAndGet(), ddlScripts, dmlScripts);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		AtomicInteger ai1 = new AtomicInteger();
		quickFormInformation.getUiRules().stream().forEach(x -> {
			SqlEmitter.insertUiRules(x, dmlScripts, ai1.incrementAndGet());
		});

		System.out.println("Create Script:" + ddlScripts.toString());
		System.out.println("Insert Script:" + dmlScripts.toString());

		File file = new File(".");
		System.out.println("file path:" + file.getAbsolutePath());

		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				"../formbuilder_h2_services/src/main/resources/schema.sql"), "utf-8"))) {
			writer.write(ddlScripts.toString());
		}

		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				"../formbuilder_h2_services/src/main/resources/data.sql"), "utf-8"))) {
			writer.write(dmlScripts.toString());
		}

		System.out.println("Create Script:" + ddlScripts.toString());
		System.out.println("Insert Script:" + dmlScripts.toString());
	}

	private static String readFile(String file) throws IOException {
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}
			return stringBuilder.toString();
		}
	}
}
