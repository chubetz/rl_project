/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.edu;

import java.util.Map;

/**
 *
 * @author d.gorshenin
 */
public interface DBEntity {
    
    String getTableName();

    Map<String, Object> getState();
    
    Map<String, Object> getPrimaryKey();
    
    boolean isPkAuto();
}
