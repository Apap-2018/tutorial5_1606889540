package com.apap.tutorial5.service;

import com.apap.tutorial5.model.FlightModel;


public interface FlightService {
    FlightModel getFlightDetailById(long id);
    void addFlight(FlightModel flight);
    void deleteFlight(FlightModel flight);
}
