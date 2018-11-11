/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.edu;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import ru.rl.project.db.JDBCUtils;
import static ru.rl.project.edu.Entity.getStorage;
import ru.rl.project.exception.JDBCException;
import ru.rl.project.util.Utils;

/**
 *
 * @author mithia
 */
public class UserAnswer extends  Entity{

    UserAnswer(int id) {
        super("UserAnswer", id);
    }

    private static Map<String, Object> defaultState, defaultPrimaryKey;

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
        defaultPrimaryKey.put("id", -1);

        defaultState = new LinkedHashMap<String, Object>();
        defaultState.put("questionId", -1);
        defaultState.put("correct", false);
    }

    @Override
    public boolean isPkAuto() {
        return true;
    }
    
    public static Map<Integer, UserAnswer> getMap() {
        return Collections.unmodifiableMap(getStorage().getUserAnswerMap());
    }

    public static UserAnswer saveUserAnswer(String id, Map<String, ?> data) throws JDBCException {
        return saveUserAnswer(Integer.parseInt(id), data);
    }
    public static UserAnswer saveUserAnswer(int id, Map<String, ?> data) throws JDBCException {
        UserAnswer userAnswer = getMap().get(id);
        Utils.print("saveUserAnswer", data);
        if (userAnswer == null) 
            userAnswer = new UserAnswer(-1);
        if (data != null && !data.isEmpty()) {
            if (data.get(data.keySet().toArray()[0]).getClass().isArray()) { //параметры пришли с фронта
                data = Utils.translateWebData( (Map<String, String[]>)data );
            }
            Utils.print("saveUserAnswerAfterTranslation", data);
            userAnswer.setState(data);

        }
        Map<String, Object> pk = JDBCUtils.saveEntity(userAnswer);
        if (pk != null) { // удалось записать объект в БД
            userAnswer.setPrimaryKey(pk);
            getStorage().register(userAnswer);
            Utils.print("UserAnswer pk: ", pk);
        } else {
            return null;
        }
            
        return userAnswer;
    }
}
