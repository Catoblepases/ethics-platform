package com.example.demo.controller;

import ch.qos.logback.classic.util.LogbackMDCAdapter;
import com.example.demo.mapper.FichierMapper;
import com.example.demo.model.Generator;
import com.example.demo.model.menu.Fichierclingo;
import com.example.demo.model.menu.Node;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/Fichierc")

public class FichierController {
    FichierMapper fichiermapper;

    @PostMapping
    void addFichier(@RequestBody String f) {
        fichiermapper.addFichier(new Fichierclingo(f));
        System.out.println("success\n");
    }

    @GetMapping
    String affresultat(){
        fichiermapper.creatfichierconf();

        Process proc;
        String result="";
        try {
            proc = Runtime.getRuntime().exec("python ./data/ethicalFullProcess.py ./data/"+fichiermapper.getFconf());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "./data/newresultTroll.lp" ;
    }

    @GetMapping("/{second}")
    void runon(int s) throws IOException {
        FileInputStream inputStream = new FileInputStream("D:/IN013/ethics-platform/spring/data");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList<ArrayList<Generator>> senarios=new ArrayList<>();
        String str = null;
        while((str = bufferedReader.readLine()) != null)
        {
            System.out.println(str);
            while
        }

        //close
        inputStream.close();
        bufferedReader.close();
    }
}
