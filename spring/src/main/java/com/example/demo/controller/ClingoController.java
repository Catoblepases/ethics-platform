package com.example.demo.controller;

import com.example.demo.model.clingo.ClingoResult;
import org.python.util.PythonInterpreter;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*Le classe ClingoController transmet les données du resultat de clingo avec le front-end. */
@RestController
@RequestMapping("/clingo")
public class ClingoController {

    @PostMapping("/update")
    String getClingoResult() {
        Process proc;
        String result="";
        try {
            proc = Runtime.getRuntime().exec("python ethicalFullProcess.py trolley.conf");
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
