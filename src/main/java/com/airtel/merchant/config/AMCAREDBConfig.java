/*
 *
 */
package com.airtel.merchant.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "amcareEntityManagerFactory", transactionManagerRef = "amcareTransactionManager", basePackages = {
		"com.airtel.merchant.repository" })
public class AMCAREDBConfig {

	private @Value("${amcare.datasource.jdbc-url}") String jdbcURL;
	private @Value("${amcare.datasource.username}") String username;
	private @Value("${amcare.datasource.password}") String password;
	private @Value("${amcare.datasource.driver-class-name}") String driverClass;
	private @Value("${db.password.encoder}") String dbPasswordEncoder;

	@Primary
	@Bean(name = "amcareDatasource")
	public DataSource dataSource() {
		final StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
		decryptor.setPassword(dbPasswordEncoder);
		final String decryptedPassword = decryptor.decrypt(password);
		return DataSourceBuilder.create().driverClassName(driverClass).url(jdbcURL).username(username)
				.password(decryptedPassword).build();
	}

	@Primary
	@Bean(name = "amcareEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean amcareEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("amcareDatasource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.airtel.merchant.model", "com.airtel.merchant.repository")
				.persistenceUnit("DBAuditLog").persistenceUnit("DBAuditLogRepository").build();
	}

	@Primary
	@Bean(name = "amcareTransactionManager")
	public PlatformTransactionManager amcareTransactionManager(
			@Qualifier("amcareEntityManagerFactory") EntityManagerFactory amcareEntityManagerFactory) {
		return new JpaTransactionManager(amcareEntityManagerFactory);
	}
}
