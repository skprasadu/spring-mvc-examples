package com.example.bookstore.config;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.bookstore.domain.support.InitialDataSetup;

/**
 * Configures the test context
 * 
 * <ul>
 * <li>Starts an internal H2 database with an attached TCP connector so you can connect externally to it</li>
 * <li>Creates a datasource to in memory started database</li>
 * <li>Loads the database with test data using {@link InitialDataSetup}
 * </ul>
 * 
 * <b>Note:</b> After starting the container, you can use the following URL (with your favoriate JDBC client) to connect
 * to the database: <i>jdbc:h2:tcp://localhost/mem:testdb</i>
 * 
 * 
 * 
 */

@Configuration
public class TestDataContextConfiguration {

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Bean(initMethod = "initialize")
	public InitialDataSetup setupData() {
		return new InitialDataSetup(new TransactionTemplate(this.transactionManager));
	}

	@Bean(initMethod = "start", destroyMethod = "shutdown")
	@DependsOn("dataSource")
	public Server dataSourceTcpConnector() {
		try {
			return Server.createTcpServer();
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		}
	}
}
