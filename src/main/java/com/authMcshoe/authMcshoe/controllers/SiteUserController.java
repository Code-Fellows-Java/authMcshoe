package com.authMcshoe.authMcshoe.controllers;

import com.authMcshoe.authMcshoe.models.SiteUser;
import com.authMcshoe.authMcshoe.repositories.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;

@Controller
public class SiteUserController {

    @Autowired
    SiteUserRepository siteUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup.html";
    }

    @GetMapping("/login")
    public String getLoginPage(Principal p, Model m) {
        if (p != null) {
            SiteUser siteUser = siteUserRepository.findByUsername(p.getName());
            m.addAttribute("username", p.getName());
        }
        return "login.html";
    }


    @GetMapping("/profile/{userID}")
    public String getUserProfilePage(@PathVariable long userID, Principal p, Model m) {
        SiteUser publicUser = siteUserRepository.getById(userID);
        if (p != null) {
            SiteUser siteUser = siteUserRepository.findByUsername(p.getName());
            if (siteUser != null)
                m.addAttribute("siteUser", siteUser);
            boolean isFollowing = siteUser.getFollowingSet().contains(publicUser);
            m.addAttribute("isFollowing", isFollowing);
        }
        try {
            publicUser.getFirstName();
        } catch (EntityNotFoundException notfound) {
            m.addAttribute("errorMessage", "Could not find a user for that id!");
            return "index.html";
        }
        m.addAttribute("publicUser", publicUser);
        return "profile.html";
    }

    @GetMapping("/my-profile")
    public String getMyProfile(Principal p, Model m) {
        if (p != null) {
            SiteUser siteUser = (SiteUser) siteUserRepository.findByUsername(p.getName());
            m.addAttribute("siteUser", siteUser);
        }
        return "userProfile.html";
    }

    @PostMapping("/logout")
    public RedirectView logoutUser(Principal p) {
        if (p != null) {
            try {
                request.logout();
            } catch (ServletException e) {
                System.out.println("Error logging out.");
                e.printStackTrace();
            }
        }
        return new RedirectView("/");
    }

    @PostMapping("/create-account")
    public RedirectView addNewAccount(String username, String password, String firstname, String lastname, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, String bio) {
        if (siteUserRepository.findByUsername(username) != null)
            return new RedirectView("/");
        String encryptedPassword = passwordEncoder.encode(password);
        SiteUser siteUser = new SiteUser(username, encryptedPassword, firstname, lastname, date, bio);
        siteUserRepository.save(siteUser);
        authWithHttpServletRequest(username, password);
        return new RedirectView("/");
    }

    public void authWithHttpServletRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch(ServletException se) {
            System.out.println("Error logging in");
            se.printStackTrace();
        }
    }

    @PostMapping("/login")
    public RedirectView loginToApp(String username, String password) {
        return new RedirectView("/");
    }

    @PutMapping("/follow/{profileId}")
    RedirectView followUser(@PathVariable long profileId, Principal p, Model m) {
        if (p != null) {
            SiteUser siteUser = siteUserRepository.findByUsername(p.getName());
            SiteUser publicUser = siteUserRepository.getById(profileId);
            if (siteUser != null && publicUser != null) {
                m.addAttribute("siteUser", siteUser);
                siteUser.getFollowingSet().add(publicUser);
                siteUserRepository.save(siteUser);
            }
        }
        return new RedirectView("/profile/" + profileId);
    }
}