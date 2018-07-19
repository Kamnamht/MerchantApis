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
@EnableJpaRepositories(entityManagerFactoryRef = "cbsEntityManagerFactory", transactionManagerRef = "cbsTransactionManager", basePackages = {
		"com.airtel.merchant.repository" })
public class CBSDBConfig {

	private @Value("${cbs.datasource.jdbc-url}") String jdbcURL;
	private @Value("${cbs.datasource.username}") String username;
	private @Value("${cbs.datasource.password}") String password;
	private @Value("${cbs.datasource.driver-class-name}") String driverClass;
	private @Value("${db.password.encoder}") String dbPasswordEncoder;

	@Bean(name = "cbsDataSource")
	public DataSource dataSource() {
		final StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
		decryptor.setPassword(dbPasswordEncoder);
		final String decryptedPassword = decryptor.decrypt(password);
		return DataSourceBuilder.create().driverClassName(driverClass).url(jdbcURL).username(username)
				.password(decryptedPassword).build();
	}

	@Bean(name = "cbsEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean cbsEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("cbsDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.airtel.merchant.model").persistenceUnit("SettlementReport")
				.build();
	}

	@Bean(name = "cbsTransactionManager")
	public PlatformTransactionManager cbsTransactionManager(
			@Qualifier("cbsEntityManagerFactory") EntityManagerFactory cbsEntityManagerFactory) {
		return new JpaTransactionManager(cbsEntityManagerFactory);
	}
}
