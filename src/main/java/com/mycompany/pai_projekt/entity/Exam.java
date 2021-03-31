/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pai_projekt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Simon
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Exams")
public class Exam {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_exam")
    private Integer examid;
    
    @NotBlank(message="Nazwa przedmiotu nie może być pusta")
    @Size(min = 6, max = 50, message = "Nazwa przedmiotu powinna mieć od 6 do 50 znaków")
    @Column(name = "name_subject")
    private String nameSubject;
    
    @NotBlank(message="Nazwisko prowadzącego nie może być puste")
    @Pattern(regexp = "^[^0-9]+$",
            message="Nazwisko nie może zawierać cyfr")
    @Size(min = 3, max = 60, message = "Nazwisko prowadzącego powinno mieć od 3 do 60 znaków")
    @Column(name = "name_lecturer")
    private String nameLecturer;
    
    @NotBlank(message="Rodzaj zajęć nie może być pusty")
    @Pattern(regexp = "(Wykład|Ćwiczenia|Laboratorium|Seminarium)",
            message="Podaj poprawną formę")
    private String type;
    
    @NotBlank(message="Ocena nie może być pusta")
    @Pattern(regexp = "(2|3|3.5|4|4.5|5)", message="Podaj poprawną ocenę")
    private String mark;
    
    @Min(value = 0, message = "Przedmiot nie może mieć mniej niż 0 punktów ECTS")
    @Max(value = 15, message = "Przedmiot nie może mieć więcej niż 15 punktów ECTS")
    private Integer ects;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;
}
