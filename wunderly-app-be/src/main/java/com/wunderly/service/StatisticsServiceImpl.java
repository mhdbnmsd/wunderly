package com.wunderly.service;

import com.wunderly.dto.StatsDTO;
import com.wunderly.repository.VisitRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.HashMap;

@ApplicationScoped
public class StatisticsServiceImpl implements StatisticsService {

    private final VisitRepository visitRepository;

    private final Logger logger;

    public StatisticsServiceImpl(VisitRepository visitRepository,
                                 Logger logger) {
        this.visitRepository = visitRepository;
        this.logger = logger;
    }

    @Override
    public StatsDTO findVisits(String key, LocalDate from, LocalDate to) {
        final var map = new HashMap<LocalDate, Integer>();
        visitRepository.findByKey(key, from.atStartOfDay(), to.atStartOfDay())
               .forEach(visit -> {
                   final var localDate = visit.getVisit().toLocalDate();
                   if (map.containsKey(localDate)) {
                       map.put(localDate, map.get(localDate) + 1);
                   } else  {
                       map.put(localDate, 1);
                   }
               });
        logger.info("Visits to this website are %s".formatted(map));
        return new StatsDTO(map);
    }
}
