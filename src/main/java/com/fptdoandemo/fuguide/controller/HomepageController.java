package com.fptdoandemo.fuguide.controller;

import com.fptdoandemo.fuguide.repository.NewsRepository;
import com.fptdoandemo.fuguide.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Controller
public class HomepageController {
    @Autowired
    private ProgramRepository programRepo;

    @Autowired
    private NewsRepository newsRepo;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("programs", programRepo.findAll());
        model.addAttribute("news", newsRepo.findAll());

        model.addAttribute("englishLocale", Locale.ENGLISH);

        return "index";
    }
}
