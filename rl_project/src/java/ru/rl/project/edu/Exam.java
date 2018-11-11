/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.edu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import ru.rl.project.exception.JDBCException;
import ru.rl.project.exception.NoMoreQuestionException;
import ru.rl.project.util.Utils;

/**
 *
 * @author d.gorshenin
 */
public class Exam implements Iterator<Question> {
    
    private List<Question> questionSequence;
    private Iterator<Question> iterator;
    private Question current;
    private QuestionState qState;
    private int counter;
    private Theme theme;
    private Map<Integer, Boolean> answersFromFront, userAnswers = new HashMap<Integer, Boolean>();
    private List<Answer> answers;
    
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Question next() {
        counter++;
        try {
            current = iterator.next();
        } catch (NoSuchElementException ex) {
            throw new NoMoreQuestionException();
        }
        qState = QuestionState.New;
        answersFromFront = new HashMap<Integer, Boolean>();
        answers = current.getAnswersShuffled();
        return current;

    }
    
    public int getNum() {
        return counter;
    }
    
    public int getRemaining() {
        return questionSequence.size() - counter;
    }
    
    public Question getQuestion() {
        return current;
    }

    public Exam(Theme theme) {
        this.theme = theme;
        questionSequence = new ArrayList(theme.getValidQuestions());
        Collections.shuffle(questionSequence);
        iterator = questionSequence.iterator();
    }
    
    public Theme getTheme() {
        return theme;
    }
    
    public String getNextButtonHTML() {
        
        
        String disabled = "";
        if ((current.getType() == Question.COMMON_TYPE && qState == QuestionState.New) ||
                (qState == QuestionState.AnswerShowed)) {
            disabled = "disabled";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("                    <form method=\"POST\" action=\"doActive\">\n" +
"                        <input type=\"hidden\" name=\"id\" value=\"" + theme.getId() + "\">\n" +
"                        <input type=\"hidden\" name=\"action\" value=\"doTheme\">\n" +
"                        <input type=\"hidden\" name=\"subAction\" value=\"" + (hasNext() ? "next" : "stop") + "\">\n" +
"                        <input type=\"Submit\" value=\"" + (hasNext() ? "Далее" : "Завершить") + "\" " + disabled +">\n" +
"                    </form>\n" +
"");
        return sb.toString();
        
    }

    public String getAnswerHTML() {
        StringBuilder sb = new StringBuilder();
        switch (current.getType()) {
            case Question.NB_TYPE:
                sb.append("                    <form method=\"POST\" action=\"doActive\">\n" +
        "                        <input type=\"hidden\" name=\"id\" value=\"" + theme.getId() + "\">\n" +
        "                        <input type=\"hidden\" name=\"action\" value=\"doTheme\">\n" +
        "                        <input type=\"hidden\" name=\"subAction\" value=\"next\">\n" +
        "                        <input type=\"Submit\" value=\"Далее\">\n" +
        "                    </form>\n" +
        "");
                break;
            case Question.COMMON_TYPE:
                switch (qState) {
                    case New:
                        sb.append("<table width=\"100%\">\n");
                        sb.append("<tr>\n");
                        sb.append("<td align=center>");
                        sb.append("                    <form method=\"POST\" action=\"doActive\">\n" +
"                        <input type=\"hidden\" name=\"id\" value=\"" + getTheme().getId() + "\">\n" +
"                        <input type=\"hidden\" name=\"action\" value=\"doTheme\">\n" +
"                        <input type=\"hidden\" name=\"subAction\" value=\"showAnswer\">\n" +
"                        <input type=\"Submit\" value=\"Показать ответ\">\n" +
"                    </form>\n" +
"");
                        sb.append("</td>\n");
                        sb.append("</tr>\n");
                        sb.append("</table>\n");
                        break;
                    case AnswerShowed:
                        sb.append("<table bgcolor=green width=\"100%\" cellpadding=10 cellspacing=3>\n");
                        sb.append("<tr>\n");
                        sb.append("<td bgcolor=#E6FDFE style=\"font-family:Arial; color:#48050C\">\n");
                        sb.append(current.getAnswerMap().values().toArray(new Answer[0])[0].getText());
                        sb.append("</td>\n");
                        sb.append("</tr>\n");
                        sb.append("<tr>\n");
                        sb.append("<td bgcolor=white align=center>\n");
                        sb.append("<b style=\"font-size:18px;\">Верно ли Вы ответили?</b>");
                        sb.append("</td>\n");
                        sb.append("</tr>\n");
                        sb.append("</table>\n");
                        sb.append("<table>\n");
                        sb.append("<tr>\n");
                        sb.append("<td width=\"50%\">\n");
                        sb.append("                    <form method=\"POST\" action=\"doActive\">\n" +
"                        <input type=\"hidden\" name=\"id\" value=\"" + getTheme().getId() + "\">\n" +
"                        <input type=\"hidden\" name=\"action\" value=\"doTheme\">\n" +
"                        <input type=\"hidden\" name=\"subAction\" value=\"rightAnswer\">\n" +
"                        <input type=\"Submit\" value=\"Да\">\n" +
"                    </form>\n" +
"");
                        sb.append("</td>\n");
                        sb.append("<td width=\"50%\">\n");
                        sb.append("                    <form method=\"POST\" action=\"doActive\">\n" +
"                        <input type=\"hidden\" name=\"id\" value=\"" + getTheme().getId() + "\">\n" +
"                        <input type=\"hidden\" name=\"action\" value=\"doTheme\">\n" +
"                        <input type=\"hidden\" name=\"subAction\" value=\"wrongAnswer\">\n" +
"                        <input type=\"Submit\" value=\"Нет\">\n" +
"                    </form>\n" +
"");
                        sb.append("</td>\n");
                        sb.append("</tr>\n");
                        sb.append("</table>\n");
                    break;
                }
                break;
            case Question.TEST_TYPE:
                switch (qState) {
                    case New:
                        sb.append("<form method=\"POST\" action=\"doActive\">\n");
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
                        sb.append("<input type=\"hidden\" name=\"id\" value=\"" + getTheme().getId() + "\">\n" +
"                        <input type=\"hidden\" name=\"action\" value=\"doTheme\">\n" +
"                        <input type=\"hidden\" name=\"subAction\" value=\"testAnswer\">\n");
                        sb.append("</form>\n");
                        
                        break;
                    case Answered:
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
                sb.append("                    <form method=\"POST\" action=\"doActive\">\n" +
        "                        <input type=\"hidden\" name=\"id\" value=\"" + theme.getId() + "\">\n" +
        "                        <input type=\"hidden\" name=\"action\" value=\"doTheme\">\n" +
        "                        <input type=\"hidden\" name=\"subAction\" value=\"next\">\n" +
        "                        <input type=\"Submit\" value=\"Далее\">\n" +
        "                    </form>\n" +
        "");
                        
                        break;
                }
                break;
        }
        return sb.toString();
        
    }
    
    public void processWorkflow(String subAction, Map<String, String[]> requestMap) {
        if (subAction == null)
            return;
        switch (subAction) {
            case "next":
                this.next();
                break;
            case "showAnswer": //показать ответ в общем вопросе
                qState = QuestionState.AnswerShowed;
                break;
            case "rightAnswer": //подтверждение верного ответа на общий вопрос
                userAnswers.put(current.getId(), Boolean.TRUE);
                qState = QuestionState.Answered;
                next();
                break;
            case "wrongAnswer": //подтверждение неверного ответа на общий вопрос
                userAnswers.put(current.getId(), Boolean.FALSE);
                qState = QuestionState.Answered;
                next();
                break;
            case "testAnswer": //произведен ответ на тестовый вопрос
                Map<String, Object> answerData = Utils.translateWebData(requestMap);
                for (Map.Entry<String, Object> entry : answerData.entrySet()) {
                    String[] splitted = entry.getKey().split("_");
                    if (splitted.length == 2 && splitted[0].equals("answer")) {
                        int answerId = Integer.parseInt(splitted[1]);
                        if (((String)entry.getValue()).equals("on")) {
                            answersFromFront.put(answerId, Boolean.TRUE);
                        }
                    }
                }
                boolean userAnsweredRight = true;
                for (Answer answer : current.getAnswerMap().values()) {
                    boolean usAns = answersFromFront.get(answer.getId()) != null && answersFromFront.get(answer.getId());
                    if (answer.getCorrect() != usAns) {
                       userAnsweredRight = false;
                       break;
                    }
                }
                userAnswers.put(current.getId(), userAnsweredRight);
                qState = QuestionState.Answered;
                break;
        }
    }
    
    public String getStopBtn() {
        return "                    <form method=\"POST\" action=\"doActive\">\n" +
"                        <input type=\"hidden\" name=\"id\" value=\"" + getTheme().getId() + "\">\n" +
"                        <input type=\"hidden\" name=\"action\" value=\"stopTheme\">\n" +
"                        <input type=\"submit\" value=\"Завершить\">\n" +
"                    </form>\n" +
"";
    }
    
    public void saveStatistics() throws JDBCException {
        int correctAnswersQty = 0;
        for (Map.Entry<Integer, Boolean> entry : userAnswers.entrySet()) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("questionId", entry.getKey());
            data.put("correct", entry.getValue());
            if (entry.getValue())
                correctAnswersQty++;
            UserAnswer.saveUserAnswer(-1, data);
        }
        if ((qState == QuestionState.Answered || current.getType() == Question.NB_TYPE) && !hasNext()) { // экзамен пройден до конца, следует записать по нему статистику
            int realQuestionsQty = 0;
            for (Question q : questionSequence) {
                if (q.isReal())
                    realQuestionsQty++;
            }
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("themeId", theme.getId());
            data.put("date", new java.sql.Date(System.currentTimeMillis()));
            if (realQuestionsQty > 0) {
                data.put("percentage", ((double)correctAnswersQty / realQuestionsQty)*100);
                ThemeExam.saveThemeExam(-1, data);
            }
        }
    }
    
    private enum QuestionState {
        New, AnswerShowed, Answered
    }
    
    
}
