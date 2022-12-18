package com.wunderly.dto;

import javax.validation.constraints.Pattern;

public record CreateLinkDTO(@Pattern(regexp = LINK_REGEX) String url) {
    private static final String LINK_REGEX = "^(http|https)://[-a-zA-Z0-9+&@#/%?=~_|,!:.;]*[-a-zA-Z0-9+@#/%=&_|]";
}
