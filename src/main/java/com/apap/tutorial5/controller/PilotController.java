package com.apap.tutorial5.controller;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PilotController {
    @Autowired
    private PilotService pilotService;

    @RequestMapping("/")
    private String home(){
        return "home";
    }

    @RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
    private String add(Model model){
        model.addAttribute("pilot", new PilotModel());
        return "addPilot";
    }

    @RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
    private String addPilotSubmit(@ModelAttribute PilotModel pilotModel){
        pilotService.addPilot(pilotModel);
        return "add";
    }

    @RequestMapping(value="/pilot/view", method = RequestMethod.GET)
    public String view(@RequestParam("licenseNumber") String licenseNumber, Model model){
        PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);

        if(archive!=null) {
            model.addAttribute("pilot", archive);
            return "view-pilot";
        }
        return "error";
    }

    @RequestMapping("/pilot/delete")
    public String deletePilot(@RequestParam(value = "licenseNumber") String licenseNumber, Model model){
        PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);

        if(archive!=null) {
            pilotService.deletePilot(archive);
            return "delete";
        }
       return "error";
    }

    @RequestMapping(value="/pilot/update", method = RequestMethod.POST)
    public String update(@ModelAttribute PilotModel pilot){
        pilotService.addPilot(pilot);
        return "update";
    }

    @RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.GET)
    private String updatePilot(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
        PilotModel p1 = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        model.addAttribute("pilot", p1);
        return "updatePilot";
    }
}
