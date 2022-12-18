package com.wunderly.repository;

import com.wunderly.model.Visit;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Transactional
public class VisitRepository implements PanacheRepository<Visit> {

    public List<Visit> findByKey(String key, LocalDateTime from, LocalDateTime to) {
       return find("select v from Visit v where v.visit between :from and :to and link.urlKey = :key",
               Parameters.with("from", from).and("to", to).and("key", key))
               .list();
    }
}
