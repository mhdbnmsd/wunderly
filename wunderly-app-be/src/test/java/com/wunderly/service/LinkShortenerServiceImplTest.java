package com.wunderly.service;

import com.wunderly.config.LinkConfiguration;
import com.wunderly.model.Link;
import com.wunderly.repository.LinkRepository;
import com.wunderly.repository.VisitRepository;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.WebApplicationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class LinkShortenerServiceImplTest {

    @Mock
    private LinkRepository linkRepository;

    @Spy
    private LinkConfiguration linkConfiguration = new LinkConfiguration() {
        @Override
        public String baseUrl() {
            return "https://wunder.ly/";
        }

        @Override
        public String keyLength() {
            return "10";
        }

        @Override
        public String feUrl() {
            return "https://wunderly.com";
        }
    };

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private LinkShortenerServiceImpl linkShortenerService;


    private final static String KEY = "KEY";

    private final static String URL = "https://test-url.com";

    @Test
    @DisplayName("Create short link successfully")
    void createShortLink() {
        when(linkRepository.findByUrl(URL)).thenReturn(Optional.empty());
        doNothing().when(linkRepository).persist(any(Link.class));
        final var linkDTO = linkShortenerService.createShortLink(URL);
        assertNotNull(linkDTO);
        verify(linkRepository, times(1)).persist(any(Link.class));
        assertTrue(linkDTO.shortUrl().startsWith(linkConfiguration.baseUrl()));
        assertTrue(linkDTO.statsUrl().startsWith(linkConfiguration.feUrl()));
    }

    @Test
    @DisplayName("Create short link, already exists")
    void createShortLinkAlreadyExists() {
        when(linkRepository.findByUrl(URL)).thenReturn(Optional.of(Link.of(URL, KEY)));
        final var linkDTO = linkShortenerService.createShortLink(URL);
        assertNotNull(linkDTO);
        assertTrue(linkDTO.shortUrl().startsWith(linkConfiguration.baseUrl()));
        assertTrue(linkDTO.statsUrl().startsWith(linkConfiguration.feUrl()));
    }


    @Test
    @DisplayName("Find link")
    void findLink() {
        when(linkRepository.findByKey(KEY)).thenReturn(Optional.of(Link.of(URL, KEY)));
        final var linkDTO = linkShortenerService.findLink(KEY);
        assertNotNull(linkDTO);
        assertEquals(linkDTO.url(), URL);
    }

    @Test
    @DisplayName("Find link, throws an exception")
    void findLinkThrowsException() {
        when(linkRepository.findByKey(KEY)).thenReturn(Optional.empty());
        assertThrows(WebApplicationException.class, () -> linkShortenerService.findLink(KEY));

    }
}