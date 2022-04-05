package com.kathi.github.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class GitController {

    @GetMapping("/kathi")
    public Map<String, Object> getSomeMapping(){
        Map<String, Object> map = new HashMap<>();

        map.put("status", "200");
        return map;
    }
}
