package com.example.demo.controller;

import com.example.demo.model.clingo.ClingoResult;
import org.python.util.PythonInterpreter;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clingo")
public class ClingoController {
    @GetMapping
    String getClingoResult() {
        Process proc;
        String result = "";
        String cmd = "python ethicalFullProcess.py trolley.conf";
        String path = System.getProperty("user.dir")+"\\data\\ria";
        runtimeExec(cmd, path);
        return result;
    }

    @PostMapping("/result")
    Map getClingoResults(@RequestBody Map params) {
        ClingoResult cr = new ClingoResult(params.get("name").toString());
        Map map = cr.calculateMap();
        return map;
    }

    public static boolean runtimeExec(String cmd, String path) {
        Process proc;
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("cmd /c ");
            builder.append(path);
            builder.append(File.separator);
            builder.append(cmd);
            Runtime rt = Runtime.getRuntime();
            proc = rt.exec(builder.toString());
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            int exitVal = proc.waitFor();
            return exitVal == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
