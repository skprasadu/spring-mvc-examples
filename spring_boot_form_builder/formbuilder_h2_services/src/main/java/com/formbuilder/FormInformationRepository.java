package com.formbuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.formbuilder.dto.FormInformation;
import com.formbuilder.dto.Node;
import com.formbuilder.jdbcstream.JdbcStream;
import com.formbuilder.jdbcstream.JdbcStream.SqlRow;

@Repository
public class FormInformationRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	private static Logger logger = Logger.getLogger(FormInformationRepository.class);

	@Autowired
	JdbcStream jdbcStream;

	@Autowired
	FormInformationRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Stream<FormInformation> findAllFormTemplates() {
		String sql = "select * from ui_form";

		try (JdbcStream.StreamableQuery query = jdbcStream.streamableQuery(sql)) {
			logger.info("Queried streaming records: ");
			return query.stream().map(row -> getFormTemplate(row));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	private FormInformation getFormTemplate(SqlRow row) {
		FormInformation template = new FormInformation();
		Node node = new Node();
		node.setId(row.getString("id"));
		node.setLabel(row.getString("display_name"));
		template.setRootnode(node);
		return template;
	}

	public FormInformation findTemplateByName(String name) {
		return null;
	}

	public Stream<FormInformation> findAllFormData(String formName) {
		return null;
	}

	public FormInformation findFormData(String formName, String id) {
		return null;
	}

	public void deleteAll() {

	}

	public void save(FormInformation formTemplate) {

	}
}
