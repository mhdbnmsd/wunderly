package com.wunderly.controller;

import com.wunderly.dto.StatsDTO;
import com.wunderly.service.StatisticsService;

import javax.ws.rs.*;
import java.time.LocalDate;

@Path("stats")
@Produces
public class StatisticsController {


    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GET
    @Path("{key}/{from}/{to}") //yyyy-MM-dd
    public StatsDTO getVisits(@PathParam("key") String key, @PathParam("from") String from, @PathParam("to") String to) {
        return statisticsService.findVisits(key, LocalDate.parse(from), LocalDate.parse(to));
    }
}
