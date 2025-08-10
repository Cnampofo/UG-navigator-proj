package com.ugmaps.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    @Value("${app.name}")
    private String appName;
    
    @Value("${app.version}")
    private String appVersion;
    
    @Value("${map.center.latitude}")
    private Double centerLatitude;
    
    @Value("${map.center.longitude}")
    private Double centerLongitude;
    
    @Value("${map.zoom.default}")
    private Integer defaultZoom;
    
    @Value("${map.bounds.north}")
    private Double boundsNorth;
    
    @Value("${map.bounds.south}")
    private Double boundsSouth;
    
    @Value("${map.bounds.east}")
    private Double boundsEast;
    
    @Value("${map.bounds.west}")
    private Double boundsWest;
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("centerLat", centerLatitude);
        model.addAttribute("centerLon", centerLongitude);
        model.addAttribute("defaultZoom", defaultZoom);
        model.addAttribute("boundsNorth", boundsNorth);
        model.addAttribute("boundsSouth", boundsSouth);
        model.addAttribute("boundsEast", boundsEast);
        model.addAttribute("boundsWest", boundsWest);
        return "index";
    }
    
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("appName", appName + " - Admin");
        return "admin";
    }
}
