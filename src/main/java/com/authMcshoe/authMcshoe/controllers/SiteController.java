package com.authMcshoe.authMcshoe.controllers;

import com.authMcshoe.authMcshoe.models.PostUser;
import com.authMcshoe.authMcshoe.models.SiteUser;
import com.authMcshoe.authMcshoe.repositories.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SiteController {


    @Autowired
    SiteUserRepository siteUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String getHomePage(Principal p, Model m) {
        if (p != null) {
            SiteUser siteUser = siteUserRepository.findByUsername(p.getName());
            m.addAttribute("siteUser", siteUser);
        }
        return "index.html";
    }

    @GetMapping("/allUsers")
    public String getAllUsersPage(Principal p, Model m) {
        if (p != null) {
            SiteUser siteUser = siteUserRepository.findByUsername(p.getName());
            m.addAttribute("siteUser", siteUser);
        }
        List<SiteUser> allUsers = siteUserRepository.findAll();
        m.addAttribute("allUsers", allUsers);
        return "allUsers.html";
    }
    @GetMapping("/feed")
    public String getMyFeed(Principal p, Model m) {
        if (p != null) {
            SiteUser siteUser = siteUserRepository.findByUsername(p.getName());
            m.addAttribute("siteUser", siteUser);
            List<PostUser> followingPosts = new ArrayList<>();
            for ( SiteUser user : siteUser.getFollowingSet()) {
                followingPosts.addAll(user.getPostList());
            }
            m.addAttribute("allUserPosts", followingPosts);
        }
        return "feed.html";
    }
}
