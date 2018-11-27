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
import ru.rl.project.util.Constants;
import ru.rl.project.util.Utils;

/**
 *
 * @author d.gorshenin
 */
public class LearnElement {
    
    private Realm realm;
    private Theme theme;
    private Rule rule;
    private Question question;
    
    private int successQuestionQuantity = 3; //сколько раз надо корректно выполнить задание данного типа, чтобы перейти к следующему
    private int alreadyAnswered;
    private Question generatedQuestion;
    private QuestionState qState;
    private List<Answer> answers;
    private Map<Integer, Boolean> answersFromFront;

    
    private Iterator<Question> generatedQuestions;
    
    private String title, subtitle;
    private String form = "";
    private String titleBgcolor = "", subtitleBgcolor = "";
    
    private Learn parentLearn;
    
    private Map<String, Object> requestMap;
   
    
    public LearnElement(ITreeElement node, Learn parentLearn) {
        this.parentLearn = parentLearn;
        if (node instanceof Realm) {
            this.realm = (Realm)node;
            this.title = "Раздел <b>" + realm.getDescription() + "</b>";
        }
        if (node instanceof Question) {
            this.question = (Question)node;
            this.title = "Тема <b>" + this.question.getRule().getTheme().getText() + "</b>";
            this.titleBgcolor = Constants.MAIN_BGCOLOR;
            this.subtitleBgcolor = Constants.MAIN_BGCOLOR;
            StringBuilder sb = new StringBuilder();
            sb.append("Правило для изучения");
            sb.append("<p>");
            sb.append("<b>" + this.question.getRule().getNumber() + "</b>. " + this.question.getRule().getText());
            this.subtitle = sb.toString();
            qState = QuestionState.New;
        }
        if (node instanceof Rule) {
            this.rule = (Rule)node;
            this.title = "Тема <b>" + this.rule.getTheme().getText() + "</b>";
            this.titleBgcolor = Constants.MAIN_BGCOLOR;
            StringBuilder sb = new StringBuilder();
            sb.append("Правило для изучения");
            sb.append("<p>");
            sb.append("<b>" + this.rule.getNumber() + "</b>. " + this.rule.getText());
            this.subtitle = sb.toString();
            this.form = "                            <form name=\"start\" action=\"learn\" method=\"POST\">\n" +
"                                <input type=\"hidden\" name=\"action\" value=\"learn\"/>\n" +
"                                <input type=\"hidden\" name=\"nodeId\" value=\"" + parentLearn.getMainNode().getTreeSign().getId() + "\"/>\n" +
"                                <input type=\"hidden\" name=\"doNext\" value=\"true\"/>\n" +
"                                <input type=\"Submit\" value=\"Продолжить\"/>\n" +
"                            </form>\n" +
"";
        }
        if (node instanceof Theme) {
            this.theme = (Theme)node;
            this.title = "Тема <b>" + theme.getText() + "</b>";
            this.form = "                            <form name=\"start\" action=\"learn\" method=\"POST\">\n" +
"                                <input type=\"hidden\" name=\"action\" value=\"learn\"/>\n" +
"                                <input type=\"hidden\" name=\"nodeId\" value=\"" + parentLearn.getMainNode().getTreeSign().getId() + "\"/>\n" +
"                                <input type=\"hidden\" name=\"doNext\" value=\"true\"/>\n" +
"                                <input type=\"Submit\" value=\"Продолжить\"/>\n" +
"                            </form>\n" +
"";
        }
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getTitleBgcolor() {
        return this.titleBgcolor;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public String getSubtitleBgcolor() {
        return this.subtitleBgcolor;
    }

    public String getForm() {
        if (question != null) {
            StringBuilder sb = new StringBuilder();
            switch (qState) {
                case New:
                    this.generatedQuestion = question.generateQuestion();
                    sb.append("<b>Задание.</b> " + this.generatedQuestion.getText() + "<br>");
                    answersFromFront = new HashMap<Integer, Boolean>();
                    answers = generatedQuestion.getAnswersShuffled();
                    sb.append("<form method=\"POST\" action=\"learn\">\n");
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
"                        <input type=\"hidden\" name=\"action\" value=\"learn\">\n" +
"                        <input type=\"hidden\" name=\"nodeId\" value=\"" + parentLearn.getMainNode().getTreeSign().getId() + "\"/>\n" +
"                        <input type=\"hidden\" name=\"subAction\" value=\"testAnswer\">\n");
                    sb.append("</form>\n");
                    this.form = sb.toString();
                    this.qState = QuestionState.Generated;
                    break;
                case Generated:
                    Utils.print(requestMap);
                    
                    String subAction = (String)requestMap.get("subAction");
                    if (subAction != null && subAction.equals("testAnswer")) { //был дан ответ на задание
                        answersFromFront = new HashMap<Integer, Boolean>();
                        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
                            String[] splitted = entry.getKey().split("_");
                            if (splitted.length == 2 && splitted[0].equals("answer")) {
                                int answerId = Integer.parseInt(splitted[1]);
                                if (((String)entry.getValue()).equals("on")) {
                                    answersFromFront.put(answerId, Boolean.TRUE);
                                }
                            }
                        }
                        boolean userAnsweredRight = true;
                        sb.append("<b>Задание.</b> " + this.generatedQuestion.getText() + "<br>");
                        sb.append("<b>Получен ответ пользователя</b><br>");
                        sb.append("<table bgcolor=#CD7A18 width=\"100%\" cellpadding=10 cellspacing=3>\n");
                        counter = 0;
                        row = new StringBuilder();
                        answerIterator = answers.iterator();
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
                                row.append("<td bgcolor=#3EAA08 onclick=\"getCommentHTML('" + answer.getId() + "', " + userAnswer + ")\" width=\"5%\">\n");
                            } else {
                                row.append("<td bgcolor=#DA1617 onclick=\"getCommentHTML('" + answer.getId() + "', " + userAnswer + ")\" width=\"5%\">\n");
                                userAnsweredRight = false;
                            }
                            row.append("<input type=\"checkbox\" " + (userAnswer ? "checked" : "") + " disabled>");
                            row.append("</td>\n");
                            row.append("<td width=\"45%\" bgcolor=#FAF5F5 style=\"font-family:Arial; color:#48050C\">\n");
                            row.append(answer.getText());
                            //row.append("<a href=\"sdf\" target=\"_blank\">" +  answer.getText() + "</a>");
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
                        
                        String doNext="false";
                        String buttonString = "Далее";
                        if (userAnsweredRight) {
                            alreadyAnswered++;
                            if (alreadyAnswered == successQuestionQuantity) {
                                sb.append("Задание выполнено <b style='color:green;'>ВЕРНО</b>.");
                                if (parentLearn.isCurrentLast()) {
                                    sb.append("Поздравляем! Сеанс обучения окончен!");
                                    buttonString = "Завершить";
                                    subAction = "stop";
                                } else {
                                    subAction = "";
                                    doNext = "true";
                                }
                            }
                            else {
                                sb.append("Задание выполнено <b style='color:green;'>ВЕРНО</b>. Предлагаем закрепить успех и выполнить еще одно задание данного типа!");
                                subAction = "nextQuestion"  ;
                            }
                        } else {
                            sb.append("Задание выполнено <b style='color:red;'>НЕВЕРНО</b>. Внимательно перечитайте правило и текст задания. Предлагаем выполнить еще одно задание данного типа!");
                                subAction = "nextQuestion"  ;
                        }
                        
                sb.append("                    <form method=\"POST\" action=\"learn\">\n" +
"                        <input type=\"hidden\" name=\"action\" value=\"learn\">\n" +
"                        <input type=\"hidden\" name=\"nodeId\" value=\"" + parentLearn.getMainNode().getTreeSign().getId() + "\"/>\n" +
        "                        <input type=\"hidden\" name=\"subAction\" value=\"" + subAction + "\">\n" +
        "                        <input type=\"hidden\" name=\"doNext\" value=\"" + doNext + "\">\n" +
        "                        <input type=\"Submit\" value=\"" + buttonString + "\">\n" +
        "                    </form>\n" +
        "");                        
                        this.form = sb.toString();
                        this.qState = QuestionState.Answered;
                    }
                    break;
                case Answered:
                    subAction = (String)requestMap.get("subAction");
                    if (subAction != null && subAction.equals("nextQuestion")) { //пользователь хочет получить еще одно задание данного типа
                        this.qState = QuestionState.New;
                        getForm();
                    }
                    
                    break;
            }
        }
        return this.form;
    }
    
    public void setParameterMap(Map<String, String[]> requestMap) {
        this.requestMap = Utils.translateWebData(requestMap);
    }

    private enum QuestionState {
        New, Generated, Answered
    }
    
}
