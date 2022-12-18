package com.wunderly.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
public class Visit {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime visit;

    @ManyToOne
    private Link link;

    public Visit() {}

    public static Visit of(LocalDateTime visit, Link link) {
        return new Visit(visit, link);
    }

    private Visit(LocalDateTime visit, Link link) {
        this.visit = visit;
        this.link = link;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getVisit() {
        return visit;
    }

    public void setVisit(LocalDateTime visit) {
        this.visit = visit;
    }
}
