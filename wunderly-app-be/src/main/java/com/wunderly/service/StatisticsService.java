package com.wunderly.service;

import com.wunderly.dto.StatsDTO;

import java.time.LocalDate;

public interface StatisticsService {

    StatsDTO findVisits(String key, LocalDate from, LocalDate to);
}
