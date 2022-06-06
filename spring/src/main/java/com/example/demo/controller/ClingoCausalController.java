package com.example.demo.controller;

import com.example.demo.model.Carriage;
import com.example.demo.model.clingo.ClingoCausal;
import com.example.demo.model.menu.InfoCarriage;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/causal")
public class ClingoCausalController {
    @PostMapping
    ClingoCausal getClingoCausal(@RequestBody Map params) {
        ClingoCausal clingoCausal = new ClingoCausal(params.get("name").toString());
        return clingoCausal;
    }

    @PostMapping("/test")
    String postTest(@RequestBody Map params) {
        return params.get("name").toString();
    }

    @GetMapping
    String getTest() {
        return "sucess";
    }
}
