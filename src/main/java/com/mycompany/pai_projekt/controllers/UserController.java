/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pai_projekt.controllers;

import com.mycompany.pai_projekt.dao.userDao;
import com.mycompany.pai_projekt.entity.User;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Simon
 */
@Controller
public class UserController {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @Autowired
    private userDao dao;
 
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
 
    @GetMapping("/register")
    public String registerPage(Model m) {
        m.addAttribute("user", new User());
        return "register";
    }
 
    @PostMapping("/register")
    public String registerPagePOST(@ModelAttribute(value = "user") @Valid User user, BindingResult binding) {
        if (binding.hasErrors()) return "register";
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
        return "redirect:/login";
    }
    
    @GetMapping("/delete")
    public String deleteAccount(Principal principal) {
        User user = dao.findByLogin(principal.getName());
        dao.deleteById(user.getUserid());
        return "redirect:/logout";
    }
    
    @GetMapping("/editAccount")
    public String editAccount(Model m, Principal principal) {
       m.addAttribute("user", dao.findByLogin(principal.getName()));
       return "editAccount";
    }
    
    @PostMapping("/editAccount")
    public String editAccount(@ModelAttribute(value = "user") @Valid User user, BindingResult binding) {
        if (binding.hasErrors()) return "editAccount";
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
        return "redirect:/logout";
    }
}
