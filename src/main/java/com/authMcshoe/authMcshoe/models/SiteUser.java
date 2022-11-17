package com.authMcshoe.authMcshoe.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class SiteUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String username;
    String password;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    @Column(columnDefinition = "text")
    String bio;
    @OneToMany(mappedBy = "siteUser", cascade = CascadeType.ALL)
    List<PostUser> postList;

    @ManyToMany(mappedBy = "followingSet")
    Set<SiteUser> followerSet;


    @ManyToMany
    @JoinTable(
            name = "userName_to_following",
            joinColumns = {@JoinColumn(name="userName")},
            inverseJoinColumns = {@JoinColumn(name = "following")}
    )
    Set<SiteUser> followingSet;

    public SiteUser() {

    }

    public SiteUser(String username, String password, String firstName, String lastName, LocalDate dateOfBirth, String bio) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<PostUser> getPostList() {
        return postList;
    }

    public Set<SiteUser> getFollowerSet() {
        return followerSet;
    }

    public void setFollowerSet(Set<SiteUser> followerSet) {
        this.followerSet = followerSet;
    }

    public Set<SiteUser> getFollowingSet() {
        return followingSet;
    }

    public void setFollowingSet(Set<SiteUser> followingSet) {
        this.followingSet = followingSet;
    }
}
