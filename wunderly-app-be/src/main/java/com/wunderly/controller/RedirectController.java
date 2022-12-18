package com.wunderly.controller;

import com.wunderly.service.RedirectService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import java.net.URI;

import static javax.ws.rs.core.Response.Status.MOVED_PERMANENTLY;

@Path("")
@Produces
public class RedirectController {

    private final RedirectService redirectService;

    public RedirectController(RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    @GET
    @Path("{key}")
    public Response redirect(@PathParam("key") String key) {
        return Response
                .status(MOVED_PERMANENTLY)
                .location(URI.create(redirectService.redirect(key)))
                .build();
    }

}
