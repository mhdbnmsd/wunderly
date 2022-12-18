package com.wunderly.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "wunderly")
public interface LinkConfiguration {

    String baseUrl();

    String keyLength();

    String feUrl();
}
