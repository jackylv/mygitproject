package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MyController {
    @RequestMapping("/")
    public String index(){
        System.out.println("index funciton");
        return "index" ;
    }

    @RequestMapping("/aaa")
    public String test(Model model){
        model.addAttribute("name", "Dear");
        return "aaa";
    }




//    @RequestMapping("/error")
//    public String error(){
//        System.out.println("error funciton");
//        return "error" ;
//    }
}
