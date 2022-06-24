package com.example.demo.config;

import com.example.demo.mapper.EdgeMapper;
import com.example.demo.mapper.NodeMapper;
import com.example.demo.model.Generator;
import com.example.demo.model.clingo.ClingoConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfig {
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
        return new Generator("./trolley1act.lp");
    }
    @Bean
    ClingoConfig createClingoConfig() {
        return new ClingoConfig();
    }
}