package com.example.demo;

import com.example.demo.mapper.EdgeMapper;
import com.example.demo.model.Carriage;
import com.example.demo.model.Generator;
import com.example.demo.model.clingo.ClingoCausal;
import com.example.demo.model.clingo.ClingoResult;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@MapperScan("com.example.demo.mapper")
public class DemoApplication {
    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
//        ClingoCausal c = new ClingoCausal("./data/ria/traceTrollC.lp");
//        System.out.println(c.getCausalTrees());
//        ClingoResult cr = new ClingoResult("./data/ria/resultTroll.lp");
//        List<List<String>> f = cr.calculateForm();
//        ClingoResult.printMatrice(f);
    }

}
