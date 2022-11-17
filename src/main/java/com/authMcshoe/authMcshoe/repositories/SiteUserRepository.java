package com.authMcshoe.authMcshoe.repositories;

import com.authMcshoe.authMcshoe.models.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
    public SiteUser findByUsername(String username);
}
