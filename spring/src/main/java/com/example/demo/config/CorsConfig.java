package com.example.demo.config;

import com.example.demo.mapper.EdgeMapper;
import com.example.demo.mapper.NodeMapper;
import com.example.demo.model.Generator;
import com.example.demo.model.clingo.ClingoConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//public class CorsConfig implements WebMvcConfigurer {
public class CorsConfig {
    //    static final String ORIGINS[] = new String[]{"GET", "POST", "PUT", "DELETE"};
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("*").allowCredentials(true).allowedMethods(ORIGINS).maxAge(3600);
//    }
    @Bean
    EdgeMapper createEdges() {
        return new EdgeMapper();
    }

    @Bean
    NodeMapper createNodes() {
        return new NodeMapper();
    }

    @Bean
    Generator createGenerator() {
        return new Generator("./data/trolley1act.lp");
    }

    @Bean
    ClingoConfig createClingoConfig() {
        return new ClingoConfig();
    }
}