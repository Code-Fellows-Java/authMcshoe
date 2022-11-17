package com.authMcshoe.authMcshoe.models;


import javax.persistence.*;
import java.util.Date;

@Entity
public class PostUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(columnDefinition = "text")
    String body;
    String subject;
    Date createdAt;

    @ManyToOne
    SiteUser siteUser;

    public void Post() {
    }

    public void Post(String postContent, String subject) {
        this.body = postContent;
        this.subject = subject;
    }

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String postContent) {
        this.body = postContent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public SiteUser getSiteUser() {
        return siteUser;
    }

    public void setSiteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
    }
}
