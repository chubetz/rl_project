/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.rl.project.edu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import ru.rl.project.db.JDBCUtils;
import static ru.rl.project.edu.Entity.getStorage;
import ru.rl.project.exception.JDBCException;
import ru.rl.project.exception.NoQuestionException;
import ru.rl.project.util.Utils;

/**
 *
 * @author d.gorshenin
 */
public class Answer extends Entity {
    
    Answer(int id) { // TODO! обязательно перенести весь класс Answer внутрь класса Question ::: а надо ли? все в одном пакете
        super("Answer", id);
    }
    
    private static Map<String, Object> defaultState, defaultPrimaryKey;
    
    public boolean isPkAuto() {
        return true;
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
        defaultPrimaryKey.put("id", -1);
        
        defaultState = new LinkedHashMap<String, Object>();
        defaultState.put("questionId", -1);
        defaultState.put("text", "");
        defaultState.put("correct", false);
        defaultState.put("comment", "");
    }

    public static Map<Integer, Answer> getMap() {
        return Collections.unmodifiableMap(getStorage().getAnswerMap());
    }

    public static Answer saveAnswer(int id, Map<String, Object> data) throws NoQuestionException, JDBCException {
        int questionId = (Integer)data.get("questionId");
        Question question = Question.getMap().get(questionId);
        if (question == null) {
            throw new NoQuestionException("В системе нет вопроса с id = " + questionId);
        }
        
        Answer answer = getMap().get(id);
        if (answer == null)
            answer = new Answer(-1);
        Utils.print("saveAnswer", data);
        answer.setState(data);

        Map<String, Object> pk = JDBCUtils.saveEntity(answer);
        if (pk != null) { // удалось записать объект в БД
            answer.setPrimaryKey(pk);
            getStorage().register(answer);
            Utils.print("Answer pk: ", pk);
        } else {
            return null;
        }
            
        return answer;
    }
    
    public static List<Answer> getGeneratedFromFrontendAnswerList(Map<String, ?> data) { //метод возвращает набор объектов ответов , созданных на основе данных, пришедших с окна редактирования вопроса
        Utils.print("getGeneratedFromFrontendAnswerList", data);
        String[] corrects = new String[0];
        if (data.get("correct") != null) {
            try {
                corrects = (String[])data.get("correct");
            } catch (ClassCastException cce) {
                corrects = new String[] {(String)data.get("correct")};
            }
            Arrays.sort(corrects);
        }
        Map<Integer, Map<String, Object>> answerDataMap = new LinkedHashMap<Integer, Map<String, Object>>();
        for (Map.Entry<String, ?> entry: data.entrySet()) {
            String[] fieldInfo = entry.getKey().split("_");
            if (fieldInfo.length == 2) {
                if (fieldInfo[0].equals("answer")) {
                    int answerId = Integer.parseInt(fieldInfo[1]);
                    Map <String, Object> answerData = answerDataMap.get(answerId);
                    if (answerData == null) {
                        answerData = new HashMap<String, Object>();
                        answerDataMap.put(answerId, answerData);
                    }
                    answerData.put("text", entry.getValue());
                }
                if (fieldInfo[0].equals("comment")) {
                    int answerId = Integer.parseInt(fieldInfo[1]);
                    Map <String, Object> answerData = answerDataMap.get(answerId);
                    if (answerData == null) {
                        answerData = new HashMap<String, Object>();
                        answerDataMap.put(answerId, answerData);
                    }
                    answerData.put("comment", entry.getValue());
                }
            }
        }
        List<Answer> answerList = new ArrayList<Answer>();
        for (int answerId : answerDataMap.keySet()) { // чтобы можно было удалять из мапы на лету
            Map<String, Object> answerData = answerDataMap.get(answerId);
            if (Arrays.binarySearch(corrects, ("" + answerId)) >= 0)
                answerData.put("correct", true);
            else
                answerData.put("correct", false);

            Utils.print("answerData", answerData);
            Answer answer = new Answer(answerId);
            answer.setState(answerData);
            answerList.add(answer);
        }
        
        return answerList;
        
    }
    
    public String getText() {
        return this.getStr("text");
    }

    public boolean getCorrect() {
        return this.getBool("correct");
    }
    
    public String getComment() {
        return this.getStr("comment");
    }

    public String getCommentHTML(boolean userAnswer) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellpadding=10 bgcolor=white border=1 width=\"400\"><tr><td>");
        String message = "";
        if (getCorrect() == false )
            if (userAnswer != getCorrect())
                message = "<p style=\"color:red\">Этот ответ не следовало выбирать</p>";
            else
                message = "<p style=\"color:green\">Вы правильно не отметили этот ответ</p>";
        else 
            if (userAnswer != getCorrect())
                message = "<p style=\"color:red\">Следовало выбрать этот ответ</p>";
            else
                message = "<p style=\"color:green\">Вы правильно отметили этот ответ</p>";
        
        sb.append("<b>"+message+"</b>");
        sb.append(getComment());
        sb.append("</td></tr></table>");
        return sb.toString();
    }
    
    public static Answer getById(Object id){
        if (id instanceof String)
            return getMap().get(Integer.parseInt((String)id));
        else
            return getMap().get((Integer)id);
    }

    public static Answer getById(int id){
        return Answer.getById(new Integer(id));
    }

}
