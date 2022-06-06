package com.example.demo.controller;

import com.example.demo.model.clingo.ClingoResult;
import org.python.util.PythonInterpreter;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clingo")
public class ClingoController {
    @GetMapping
    String getClingoResult() {
        Process proc;
        String result="";
        try {
            proc = Runtime.getRuntime().exec("python ./data/ethicalFullProcess.py ./data/trolley.conf");
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                result=result+line;
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("result")
    Map getClingoResults(@RequestBody Map params){
        ClingoResult cr = new ClingoResult(params.get("name").toString());
        Map map = cr.calculateMap();
        return map;
    }

}
