package com.wunderly.dto;

import java.time.LocalDate;
import java.util.Map;

public record StatsDTO(Map<LocalDate, Integer> visits) {}
