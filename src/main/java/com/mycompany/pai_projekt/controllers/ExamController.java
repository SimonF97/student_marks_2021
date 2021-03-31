/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pai_projekt.controllers;

import com.mycompany.pai_projekt.dao.userDao;
import com.mycompany.pai_projekt.dao.examDao;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.mycompany.pai_projekt.entity.User;
import com.mycompany.pai_projekt.entity.Exam;

/**
 *
 * @author Simon
 */
@Controller
public class ExamController {
    
    @Autowired
    private userDao daoU;
    
    @Autowired
    private examDao daoE;
    
    @GetMapping("/add-exam")
    public String addExamView(Model model) {
        model.addAttribute("exam", new Exam());
        return "addExam";
    }

    @PostMapping("/add-exam")
    public String addExam(Principal principal, @ModelAttribute("exam") @Valid Exam exam, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "addExam";
        else {
            User user = daoU.findByLogin(principal.getName());
            exam.setUser(user);
            daoE.save(exam);
            return "redirect:/exams";
        }
    }

    @GetMapping("/exam/{id}")
    public String viewOneExam(@PathVariable Integer id, Model model) {
        Exam exam = daoE.findById(id).orElseThrow();
        model.addAttribute("exam", exam);
        return "exam";
    }
    
    @GetMapping("/delete-exam/{id}")
    public String deleteExam(@PathVariable Integer id) {
        System.out.println("Test 1");
        Exam exam = daoE.findByExamid(id);
        System.out.println("Test 2");
        daoE.delete(exam);
        System.out.println("Test 3");
        return "redirect:/exams";
    }
    
    @GetMapping("/edit-exam/{id}")
    public String editExamProces(@PathVariable Integer id, Model model) {
        Exam exam = daoE.findByExamid(id);
        model.addAttribute("exam", exam);
        return "editExam";
    }
    
    @GetMapping("/exams")
    public String getUsers(Model m, Principal principal) {
        System.out.println("Test");
        User user = daoU.findByLogin(principal.getName());
        m.addAttribute("exams", daoE.findAllByUser(user));
        return "exams";
    }
    
    @PostMapping("/exams")
    public String getAverage(Model m, Principal principal) {
        User user = daoU.findByLogin(principal.getName());
        int idUser = user.getUserid();
        try {
            Double marks = Double.parseDouble(daoE.sumMarks(idUser));
            int ects = daoE.sumEcts(idUser);
            Double average = marks/ects;
            average = average*100;
            average = (double)Math.round(average);
            average = average /100;
            String averageString = Double.toString(average);
            m.addAttribute("exams", daoE.findAllByUser(user));
            m.addAttribute("average", averageString);
            return "exams";
        } catch(NullPointerException ex) {
            Double average = (double)0;
            String averageString = Double.toString(average);
            m.addAttribute("exams", daoE.findAllByUser(user));
            m.addAttribute("average", averageString);
            return "exams";
        }
    }
    
    @PostMapping("/edit-exam")
    public String editExamProcess(Principal principal, @ModelAttribute("exam") @Valid Exam exam, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "editExam";
        else {
            User user = daoU.findByLogin(principal.getName());
            exam.setUser(user);
            daoE.save(exam);
            return "redirect:/exams";
        }
    }
}
