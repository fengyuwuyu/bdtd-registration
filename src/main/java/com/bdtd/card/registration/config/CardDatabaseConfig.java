package com.bdtd.card.registration.config;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

@SpringBootConfiguration  
@EnableTransactionManagement  
@EnableJpaRepositories(  
        entityManagerFactoryRef="cardEntityManagerFactory",  
        transactionManagerRef="cardTransactionManager",  
        basePackages= { "com.bdtd.card.registration.scmmain.repository" }) 
public class CardDatabaseConfig {
	
	@Autowired
	CardDatabaseProperties cardDatabaseProperties;
	

    private DruidDataSource cardDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        cardDatabaseProperties.config(dataSource);
        return dataSource;
    }
	
    @Bean(name = "cardEntityManager")  
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {  
        return entityManagerFactoryPrimary(builder).getObject().createEntityManager();  
    }  
    
    @Bean(name = "cardEntityManagerFactory")  
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary (EntityManagerFactoryBuilder builder) {  
        return builder  
                .dataSource(cardDataSource())  
                .properties(getVendorProperties(cardDataSource()))  
                .packages("com.bdtd.card.registration.scmmain.model") //设置实体类所在位置  
                .persistenceUnit("cardPersistenceUnit")  
                .build();  
    }  
    @Autowired  
    private JpaProperties jpaProperties;  
    private Map<String, String> getVendorProperties(DataSource dataSource) {  
        return jpaProperties.getHibernateProperties(dataSource);  
    }  
    @Primary  
    @Bean(name = "cardTransactionManager")  
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {  
        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());  
    }  
    
}
