/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.edu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import ru.rl.project.util.Utils;

/**
 *
 * @author mithia
 */
public class TestElement {
    private Question question;
    private Map<String, Object> requestMap;
    private Map<Integer, Boolean> answersFromFront;
    private List<Answer> answers;
    private QuestionState qState;
    private String form;
    private Test parentTest;
    private boolean userAnsweredRight;
    
    
    public TestElement(Question question, Test parentTest) {
        this.question = question;
        this.parentTest = parentTest;
        StringBuilder sb = new StringBuilder();
        answersFromFront = new HashMap<Integer, Boolean>();
        answers = question.getAnswersShuffled();
        sb.append("<form method=\"POST\" action=\"test\">\n");
        sb.append("<table bgcolor=#CD7A18 width=\"100%\" cellpadding=10 cellspacing=3>\n");
        int counter = 0;
        StringBuilder row = new StringBuilder();
        Iterator<Answer> answerIterator = answers.iterator();
        while (answerIterator.hasNext()) {
            counter++;
            Answer answer = answerIterator.next();
            row.append("<td bgcolor=#FAF5F5 width=\"5%\">\n");
            row.append("<input type=\"checkbox\" name=\"answer_" + answer.getId() + "\">");
            row.append("</td>\n");
            row.append("<td width=\"45%\" bgcolor=#FAF5F5 style=\"font-family:Arial; color:#48050C\">\n");
            row.append(answer.getText());
            row.append("</td>\n");
            if (counter % 2 == 0 || !answerIterator.hasNext()) {
                if (counter % 2 != 0) {
                    row.append("<td width=\"50%\" colspan=\"2\" bgcolor=#FAF5F5></td>");
                }
                row.insert(0, "<tr>\n");
                row.append("</tr>\n");
                sb.append(row);
                row = new StringBuilder();
            }
        }
        sb.append("</table>\n");
        sb.append("<table>\n");
        sb.append("<tr>\n");
        sb.append("<td>\n");
        sb.append("<input type=\"Submit\" value=\"Отправить ответ\">\n");
        sb.append("</td>\n");
        sb.append("</tr>\n");
        sb.append("</table>\n");
        sb.append("" +
"                        <input type=\"hidden\" name=\"action\" value=\"test\">\n" +
"                        <input type=\"hidden\" name=\"nodeId\" value=\"" + parentTest.getNode().getTreeSign().getId() + "\"/>\n" +
"                        <input type=\"hidden\" name=\"subAction\" value=\"testAnswer\">\n");
        sb.append("</form>\n");
        this.form = sb.toString();
    }
    
    public Test getParentTest() {
        return this.parentTest;
    }
    
    public void sendAnswer(Map<String, String[]> requestMap) {
        this.requestMap = Utils.translateWebData(requestMap);
        Utils.print(requestMap);
        answersFromFront = new HashMap<Integer, Boolean>();
        for (Map.Entry<String, Object> entry : this.requestMap.entrySet()) {
            String[] splitted = entry.getKey().split("_");
            if (splitted.length == 2 && splitted[0].equals("answer")) {
                int answerId = Integer.parseInt(splitted[1]);
                if (entry.getValue().equals("on")) {
                    answersFromFront.put(answerId, Boolean.TRUE);
                }
            }
        }
        this.userAnsweredRight = true;
        Iterator<Answer> answerIterator = answers.iterator();
        answerIterator = answers.iterator();
        while (answerIterator.hasNext()) {
            Answer answer = answerIterator.next();
            boolean userAnswer;
            try {
                userAnswer =  answersFromFront.get(answer.getId());
            } catch (NullPointerException ex) {
                userAnswer = false;
            }
            if (userAnswer == answer.getCorrect()) { //пользователь ответил верно

            } else {
                this.userAnsweredRight = false;
            }
        }
        
        
    }
    
    public String getForm() {
        return form;
    }

    public String getTitle() {
        return "Задание <b>" + question.getNumberOnExam() + "</b>";
    }
    
    public Question getQuestion() {
        return this.question;
    }

    private enum QuestionState {
        New, Asked, Answered
    }
    
    public boolean isUserAnsweredRight() {
        return this.userAnsweredRight;
    }
    
    public String getDetailsHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<b>Задание " + this.question.getNumberOnExam() + ".</b> " + this.question.getText() + "<br>");
        sb.append("<table bgcolor=#CD7A18 width=\"100%\" cellpadding=10 cellspacing=3>\n");
        int counter = 0;
        StringBuilder row = new StringBuilder();
        Iterator<Answer> answerIterator = answers.iterator();
        while (answerIterator.hasNext()) {
            counter++;
            Answer answer = answerIterator.next();
            boolean userAnswer;
            try {
                userAnswer =  answersFromFront.get(answer.getId());
            } catch (NullPointerException ex) {
                userAnswer = false;
            }
            if (userAnswer == answer.getCorrect()) { //пользователь ответил верно
                row.append("<td "); 
                if (userAnswer) // подсветить выбранный правильный ответ
                    row.append("bgcolor=#3EAA08 ");
                else
                    row.append("bgcolor=#FAF5F5 ");
                row.append("width=\"5%\">\n");

            } else {
                row.append("<td ");
                if (userAnswer) // подсветить выбранный правильный ответ
                    row.append("bgcolor=#DA1617 ");
                else
                    row.append("bgcolor=#3EAA08 ");
                row.append("width=\"5%\">\n");
            }
            row.append("<input type=\"checkbox\" " + (userAnswer ? "checked" : "") + " disabled>");
            row.append("</td>\n");
            row.append("<td width=\"45%\" bgcolor=#FAF5F5 style=\"font-family:Arial; color:#48050C\">\n");
            row.append(answer.getText());
            row.append("</td>\n");
            if (counter % 2 == 0 || !answerIterator.hasNext()) {
                if (counter % 2 != 0) {
                    row.append("<td width=\"50%\" colspan=\"2\" bgcolor=#FAF5F5></td>");
                }
                row.insert(0, "<tr>\n");
                row.append("</tr>\n");
                sb.append(row);
                row = new StringBuilder();
            }
        }
        sb.append("</table>\n");
        
        return sb.toString();
    }
}
