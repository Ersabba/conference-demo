package com.pluralsight.conferencedemo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

// Conficurazione DataSource via Java
@Configuration
public class PersistentConfiguration {

    // Configurazione del data source via Java: creo un DataSurce come Bean che sarà iniettato
    // autamaticamente da Spring e usato come datasource
    @Bean
    public DataSource dataSource(){
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url("jdbc:postgresql://localhost:5432/conference_app");
        builder.username("postgres");
        builder.password("Welcome");
        System.out.println("Data source creata e impostata!!!!");
        return builder.build();
    }


}
