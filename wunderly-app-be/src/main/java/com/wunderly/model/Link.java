package com.wunderly.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "links",
       uniqueConstraints = {@UniqueConstraint(columnNames = { "url", "urlKey" })
})
public class Link implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String url;

    private String urlKey;

    @OneToMany(mappedBy = "link", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Visit> visits = new HashSet<>();

    public static Link of(String url, String urlKey) {
        return new Link(url, urlKey);
    }


    private Link(String url, String urlKey) {
        this.url = url;
        this.urlKey = urlKey;
    }


    public Link() {}

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlKey(String key) {
        this.urlKey = key;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlKey() {
        return urlKey;
    }

    @Override
    public String toString() {
        return "Link{" +
                "url='" + url + '\'' +
                ", key='" + urlKey + '\'' +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Set<Visit> getVisits() {
        return visits;
    }
}
