package com.apap.tutorial5.controller;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class FlightController {
    @Autowired
    private FlightService flightService;

    @Autowired
    private PilotService pilotService;

    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
    private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
        PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        pilot.setPilotFlight(new ArrayList<FlightModel>());
        pilot.getPilotFlight().add(new FlightModel());

        model.addAttribute("pilot", pilot);
        return "addFlight";
    }

    @RequestMapping(value="/flight/add/{licenseNumber}", params={"add"})
    public String addRow(@ModelAttribute PilotModel pilot, BindingResult bindingResult, Model model) {
        pilot.getPilotFlight().add(new FlightModel());

        model.addAttribute("pilot", pilot);
        return "addFlight";
    }

    @RequestMapping(value = "/flight/add/{licenseNumber}", params={"submit"}, method = RequestMethod.POST)
    private String addFlightSubmit(@ModelAttribute PilotModel pilot) {
        PilotModel thisPilot = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber());
        for (FlightModel flight:pilot.getPilotFlight()) {
            flight.setPilot(thisPilot);
            flightService.addFlight(flight);
        }
        return "add";
    }


    @RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
    public String deleteFlight(@ModelAttribute PilotModel pilot, Model model){
        for(FlightModel flight : pilot.getPilotFlight()){
            flightService.deleteFlight(flight);
        }

        return "delete";
    }

    @RequestMapping(value="/flight/update", method = RequestMethod.POST)
    public String update(@ModelAttribute FlightModel flight){
        flightService.addFlight(flight);
        return "update";
    }

    @RequestMapping(value = "/flight/update/{Id}", method = RequestMethod.GET)
    private String updateFlight(@PathVariable(value = "Id") int id, Model model) {
        FlightModel f = flightService.getFlightDetailById(id);
        model.addAttribute("flight", f);
        return "updateFlight";
    }

    @RequestMapping("/flight/view")
    public String view(@RequestParam(value = "Id") int id, Model model){
        FlightModel flight = flightService.getFlightDetailById(id);

        if(flight!=null) {
            model.addAttribute("flight", flight);
            model.addAttribute("pilot", flight.getPilot());
            return "view-flight";
        }
        return "error";
    }




}
