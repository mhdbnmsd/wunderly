package com.wunderly.repository;

import com.wunderly.model.Link;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class LinkRepository implements PanacheRepository<Link> {

    public Optional<Link> findByKey(String key) {
        return find("urlKey", key).firstResultOptional();
    }

    public Optional<Link> findByUrl(String url) {
        return find("url", url).firstResultOptional();
    }

    public void deleteByKey(String key) {
        delete("urlKey", key);
    }
}
