package com.formbuilder;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.formbuilder.dto.FormInformation;


public interface FormInformationRepository extends MongoRepository<FormInformation, String> {

	@Query(value = "{\"type\": \"template\", \"application\": ?0}, {\"_id\":1, \"rootnode.id\" : 1, \"rootnode.label\" : 1 }")
	Stream<FormInformation> findAllFormTemplates(String appName);
	
	@Query(value = "{\"type\": \"template\", \"application\": ?0, \"rootnode._id\" : ?1} }")
	FormInformation findTemplateByName(String appName, String name);
	
	@Query(value = "{\"type\": \"data\", \"application\": ?0, \"rootnode._id\" : ?1}, {\"_id\":1, \"rootnode.id\" : 1, \"rootnode.label\" : 1  }")
	Stream<FormInformation> findAllFormData(String appName, String formName);
	
	@Query(value = "{\"type\": \"data\", \"application\": ?0, \"rootnode._id\" : ?1, \"_id\": ?2}, {\"_id\":1, \"rootnode.id\" : 1 }")
	FormInformation findFormData(String appName, String formName, String id);
}
