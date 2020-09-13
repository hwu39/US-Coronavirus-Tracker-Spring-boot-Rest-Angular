package com.sample.demo.controller;

import com.sample.demo.service.CoronavirusTracker;
import com.sample.demo.service.LocationStats;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
public class CoronavirusController {

    @Autowired
    CoronavirusTracker coronavirusTracker;

    @GetMapping("/covid")
    public List<LocationStats> covid(Model model) {
      model.addAttribute("locationStats", coronavirusTracker.getAllStats());
      return coronavirusTracker.getAllStats();
    }

}