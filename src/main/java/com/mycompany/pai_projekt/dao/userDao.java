/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pai_projekt.dao;

import com.mycompany.pai_projekt.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Simon
 */
public interface userDao extends CrudRepository<User, Integer> {
    public User findByLogin(String login);
}
