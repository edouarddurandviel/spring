package com.myspring.demo.session;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/session")
public class SessionController {

     @PostMapping("/save")
    public String saveSession(HttpSession session) {
        session.setAttribute("username", "alice");
        return "Session saved: " + session.getId();
    }

    @GetMapping("/get")
    public String readSession(HttpSession session) {
        return "Username: " + session.getAttribute("username");

    }
    
}
