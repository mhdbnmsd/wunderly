package com.wunderly.service;

import com.wunderly.dto.GetLinkDTO;
import com.wunderly.dto.LinkDTO;

public interface LinkShortenerService {

    LinkDTO createShortLink(String link);

    void deleteLink(String key);

    GetLinkDTO findLink(String key);
}
