package com.hdisolutions.oltpservices.config;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"com.hdisolutions"}) // NOTE: multitenantdatasource is from internal multi-teneant-datasource artifact
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", basePackages = { "com.hdisolutions.repository" })
@EntityScan("com.hdisolutions.model.domain")
@EnableTransactionManagement
@PropertySource(value = { "classpath:database.properties" })
public class DaoLayerConfig {

	@Inject
    private Environment env;
  
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource) {
    	
    	Map<String, Object> jpaPropMap = new HashMap<String, Object>();
    	jpaPropMap.put("spring.jpa.hibernate.ddl-auto", "none");
    	jpaPropMap.put("spring.jpa.hibernate.show_sql", env.getProperty("spring.jpa.hibernate.show_sql"));
    	jpaPropMap.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
    	jpaPropMap.put("spring.jpa.hibernate.naming.physical-strategy", env.getProperty("spring.jpa.hibernate.naming.physical-strategy"));
    	jpaPropMap.put("spring.jpa.hibernate.naming.implicit-strategy", env.getProperty("spring.jpa.hibernate.naming.implicit-strategy"));
    	jpaPropMap.put("spring.jpa.hibernate.naming-strategy", "org.hibernate.cfg.DefaultComponentSafeNamingStrategy");

    	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.hdisolutions.model.domain");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(jpaPropMap);
        
        return em;
    }
    
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        
        return transactionManager;
    }

}
