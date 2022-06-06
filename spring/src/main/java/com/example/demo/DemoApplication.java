package com.example.demo;

import com.example.demo.mapper.EdgeMapper;
import com.example.demo.model.Carriage;
import com.example.demo.model.Generator;
import com.example.demo.model.clingo.ClingoCausal;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.demo.mapper")
public class DemoApplication {
    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
//        ClingoCausal c = new ClingoCausal("./data/ria/traceTrollC.lp");
//        System.out.println(c.getCausalTrees());
    }

}
