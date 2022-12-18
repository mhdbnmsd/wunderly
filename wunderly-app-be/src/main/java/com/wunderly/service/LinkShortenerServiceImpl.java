package com.wunderly.service;

import com.wunderly.config.LinkConfiguration;
import com.wunderly.dto.GetLinkDTO;
import com.wunderly.dto.LinkDTO;
import com.wunderly.model.Link;
import com.wunderly.repository.LinkRepository;
import com.wunderly.repository.VisitRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@ApplicationScoped
public class LinkShortenerServiceImpl implements LinkShortenerService {


    private final LinkRepository linkRepository;

    private final LinkConfiguration linkConfiguration;

    private final VisitRepository visitRepository;

    private final Logger logger;

    public LinkShortenerServiceImpl(LinkRepository linkRepository,
                                    LinkConfiguration linkConfiguration,
                                    VisitRepository visitRepository,
                                    Logger logger) {
        this.linkRepository = linkRepository;
        this.linkConfiguration = linkConfiguration;
        this.visitRepository = visitRepository;
        this.logger = logger;
    }

    @Override
    public LinkDTO createShortLink(String url) {
        return linkRepository.findByUrl(url).map(link -> {
            logger.info("Link for url %s already exists, returning that instead of generating a new one".formatted(url));
            return createLinkDTO(link.getUrlKey());
        }).orElseGet(() -> {
            final var hash = hashLink(url);
            final var key = hash.substring(0, Integer.parseInt(linkConfiguration.keyLength()));
            final var link = Link.of(url, key);
            linkRepository.persist(link);
            logger.info("The following link has been added to the database : %s".formatted(link));
            return createLinkDTO(key);
        });
    }

    @Override
    @Transactional
    public void deleteLink(String key) {
        linkRepository.findByKey(key).ifPresentOrElse(link -> {
            logger.info("Deleting Link with key %s".formatted(key));
            link.getVisits().forEach(visitRepository::delete);
            linkRepository.delete(link);
        },() -> {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        });
    }

    @Override
    public GetLinkDTO findLink(String key) {
      return this.linkRepository.findByKey(key)
              .map(link -> new GetLinkDTO(link.getUrl()))
              .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    private LinkDTO createLinkDTO(final String key) {
        return new LinkDTO(linkConfiguration.baseUrl() + key, linkConfiguration.feUrl() + "stats/" + key);
    }

    private String hashLink(String link) {
        final MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            final var messageDigest = md.digest(link.getBytes());
            final var no = new BigInteger(1, messageDigest);
            return Base64.getUrlEncoder().encodeToString(no.toByteArray());
        } catch (NoSuchAlgorithmException e) {
            throw new WebApplicationException("Internal Error: Unable to find MD5 impl");
        }
    }
}
