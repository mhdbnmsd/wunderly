package com.wunderly.controller;

import com.wunderly.dto.CreateLinkDTO;
import com.wunderly.dto.GetLinkDTO;
import com.wunderly.dto.LinkDTO;
import com.wunderly.service.LinkShortenerService;

import javax.validation.Valid;
import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("link")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class LinkController {

    private final LinkShortenerService linkShortenerService;

    public LinkController(LinkShortenerService linkShortenerService) {
        this.linkShortenerService = linkShortenerService;
    }

    @POST
    @Path("")
    public LinkDTO shortenLink(@Valid CreateLinkDTO dto) {
        return linkShortenerService.createShortLink(dto.url());
    }

    @DELETE
    @Path("{key}")
    public void deleteLink(@PathParam("key") String key) {
        linkShortenerService.deleteLink(key);
    }

    @GET
    @Path("{key}")
    public GetLinkDTO getLink(@PathParam("key") String key) {
        return linkShortenerService.findLink(key);
    }
}
