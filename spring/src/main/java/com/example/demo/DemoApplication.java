package com.example.demo;

import com.example.demo.mapper.EdgeMapper;
import com.example.demo.model.Carriage;
import com.example.demo.model.Generator;
import com.example.demo.model.clingo.ClingoCausal;
import com.example.demo.model.clingo.ClingoResult;
import com.example.demo.model.clingo.ClingoTrace;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
//        ClingoTrace ct = new ClingoTrace("./data/ria/traceTroll.lp");
//        System.out.println(ct);
//        Generator generator= new Generator("./data/trolley1act.lp");
//        generator.save("./data/test.lp");
//        System.out.println(System.getProperty("user.dir"));
//        try {
//            Runtime rt = Runtime.getRuntime();
//            Process proc = rt.exec("bash run.bat");
//            InputStream stderr = proc.getErrorStream();
//            InputStreamReader isr = new InputStreamReader(stderr);
//            BufferedReader br = new BufferedReader(isr);
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }
//            proc.waitFor();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
