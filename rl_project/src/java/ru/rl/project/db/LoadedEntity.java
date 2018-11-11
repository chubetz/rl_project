/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.db;

import java.util.HashMap;
import java.util.Map;
import ru.rl.project.edu.DBEntity;
import ru.rl.project.edu.Entity;

/**
 *
 * @author mithia
 */
class LoadedEntity implements DBEntity {
    
    private Map<String, Object> primaryKey, state;
    
    void setPrimaryKey(Map<String, Object> map) {
        this.primaryKey = map;
    }
    void setState(Map<String, Object> map) {
        this.state = map;
    }

    public String toString() {
        return "Loaded Entity " + getPrimaryKey().get("id");
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public Map<String, Object> getState() {
        return state;
    }

    @Override
    public Map<String, Object> getPrimaryKey() {
        return primaryKey;
    }

    @Override
    public boolean isPkAuto() {
        return false;
    }
    
    
}
