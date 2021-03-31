/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pai_projekt.dao;

import com.mycompany.pai_projekt.entity.User;
import com.mycompany.pai_projekt.entity.Exam;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Simon
 */
public interface examDao extends CrudRepository<Exam, Integer> {
    public Exam findByExamid(int examid);
    public List<Exam> findAllByUser(User user);
    
    @Query(value = "SELECT sum(e.mark*e.ects) FROM exams e where e.id_user = :id", nativeQuery = true)
    public String sumMarks(@Param("id") int id);
    
    @Query(value = "SELECT sum(e.ects) FROM exams e where e.id_user = :id", nativeQuery = true)
    public int sumEcts(@Param("id")  int id);
}
