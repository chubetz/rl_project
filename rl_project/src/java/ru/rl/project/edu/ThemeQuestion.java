/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.edu;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import ru.rl.project.db.JDBCUtils;
import ru.rl.project.exception.JDBCException;
import ru.rl.project.util.Utils;

/**
 *
 * @author mithia
 */
class ThemeQuestion extends Entity {

    ThemeQuestion(int themeId, int questionId) {
        super("ThemeQuestion", themeId, questionId);
    }

    private static Map<String, Object> defaultState, defaultPrimaryKey;

    public boolean isPkAuto() {
        return false;
    }

    @Override
    protected Map<String, Object> getDefaultState() {
        return defaultState;
    }
    @Override
    protected Map<String, Object> getDefaultPrimaryKey() {
        return defaultPrimaryKey;
    }
    
    static {
        defaultPrimaryKey = new LinkedHashMap<String, Object>();
        defaultPrimaryKey.put("themeId", -1);
        defaultPrimaryKey.put("questionId", -1);

        defaultState = new LinkedHashMap<String, Object>(); //нет полей, кроме PK
    }
    

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ThemeQuestion) && 
                ((ThemeQuestion)obj).getPrimaryKey().equals(this.getPrimaryKey());
    }
    
    ThemeQuestion save() throws JDBCException {
        Map<String, Object> pk = JDBCUtils.saveEntity(this);
        if (pk != null) { // удалось записать объект в БД
            setPrimaryKey(pk);
            Utils.print("ThemeQuestion pk: ", pk);
        } else {
            return null;
        }
        
        return this;
    }

    boolean delete() throws JDBCException {
        return JDBCUtils.deleteEntity(this);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37*result + getPKInt("themeId");
        result = 37*result + getPKInt("questionId");
        return result;
        //return getPrimaryKey().hashCode();
    }

    @Override
    public String toString() {
        return "ThemeQuestion {" + this.getPKInt("themeId") + "} {" + this.getPKInt("questionId") + "}"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
