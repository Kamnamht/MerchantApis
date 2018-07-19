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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "voltEntityManagerFactory", transactionManagerRef = "voltTransactionManager", basePackages = {
		"com.airtel.merchant.repository" })
public class VoltDBConfig {

	private @Value("${volt.datasource.jdbc-url}") String jdbcURL;
	private @Value("${volt.datasource.username}") String username;
	private @Value("${volt.datasource.password}") String password;
	private @Value("${volt.datasource.driver-class-name}") String driverClass;
	private @Value("${db.password.encoder}") String dbPasswordEncoder;

	@Bean(name = "voltDataSource")
	public DataSource dataSource() {
		final StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
		decryptor.setPassword(dbPasswordEncoder);
		final String decryptedPassword = decryptor.decrypt(password);
		return DataSourceBuilder.create().driverClassName(driverClass).url(jdbcURL).username(username)
				.password(decryptedPassword).build();
	}

	@Bean(name = "voltEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean voltEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("voltDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.airtel.merchant.model").persistenceUnit("VoltTxnLog")
				.build();
	}

	@Bean(name = "voltTransactionManager")
	public PlatformTransactionManager voltTransactionManager(
			@Qualifier("voltEntityManagerFactory") EntityManagerFactory voltEntityManagerFactory) {
		return new JpaTransactionManager(voltEntityManagerFactory);
	}
}
