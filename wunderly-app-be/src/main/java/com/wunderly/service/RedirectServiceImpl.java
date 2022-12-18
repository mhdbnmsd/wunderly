package com.wunderly.service;

import com.wunderly.model.Visit;
import com.wunderly.repository.LinkRepository;
import com.wunderly.repository.VisitRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
public class RedirectServiceImpl implements RedirectService {

    private final LinkRepository linkRepository;

    private final VisitRepository visitRepository;

    private final Logger logger;

    public RedirectServiceImpl(LinkRepository linkRepository, VisitRepository visitRepository, Logger logger) {
        this.linkRepository = linkRepository;
        this.visitRepository = visitRepository;
        this.logger = logger;
    }

    @Override
    public String redirect(String key) {
        logger.info("Looking Url from key: %s".formatted(key));
        return linkRepository.findByKey(key)
                .map(link -> {
                    final var url = link.getUrl();
                    visitRepository.persist(Visit.of(LocalDateTime.now(), link));
                    logger.info("Redirecting to url : %s".formatted(url));
                    return url;
                })
                .orElseThrow(() -> {
                    logger.info("Url with key %s is not found".formatted(key));
                    return new WebApplicationException("Url not found", NOT_FOUND);
                });
    }
}
