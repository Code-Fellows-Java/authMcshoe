package com.authMcshoe.authMcshoe.controllers;

import com.authMcshoe.authMcshoe.models.PostUser;
import com.authMcshoe.authMcshoe.models.SiteUser;
import com.authMcshoe.authMcshoe.repositories.PostRepository;
import com.authMcshoe.authMcshoe.repositories.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Date;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    SiteUserRepository siteUserRepository;

    @PostMapping("/add-post")
    public RedirectView addNewPost(Principal p, Model m, String body, String subject) {
        if (p != null) {
            String username = p.getName();
            SiteUser siteUser = (SiteUser) siteUserRepository.findByUsername(username);
            m.addAttribute("siteUser", siteUser);
            PostUser post = new PostUser();
            post.setCreatedAt(new Date());
            post.setSiteUser(siteUser);
            postRepository.save(post);
        }
        return new RedirectView("/my-profile");
    }

}
