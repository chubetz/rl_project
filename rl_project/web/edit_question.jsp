<%-- 
    Document   : edit_question
    Created on : Jun 16, 2018, 6:36:24 PM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url= "/includes/newheader.jsp" />
<%--c:import url= "/includes/icons.jsp" /--%>
        <p>${question.getId() < 0  ? "<Новый вопрос>" : question}</p>
        <form name="${action}" action="controller" method="POST">
            <input id="actiontag" type="hidden" name="action" value="${action}">        
            <input type="hidden" name="id" value="${question.id}">        
            <c:if test="${question.newThemeId >= 0}">
            <input type="hidden" name="newThemeId" value="${question.newThemeId}">
            </c:if>
            <label>Область</label>
            ${question.getRealmsHTML()}
            <br>
            <label>Тип вопроса</label>
            <select name="type">
                ${question.getTypesHTML()}
            </select>
            <br>
            <label>Порядковый номер</label>
            <input type="text" name="number" value="${question.getNumber()}">
            <br>
            <table width="800" cellpadding="10">
                <tr>
                    <td>
                        <p>Текст вопроса</p>
                        <textarea name="text" rows="10" cols="80">${question.getStrLtGt("text")}</textarea>
                    </td>
                    <td style="vertical-align: top">
                        <p>Правильные ответы</p>
                        <select name="correct" size="${question.getAnswersSizeForHTML()}" multiple>
                            ${question.getAnswersCorrectHTML()}
                        </select>
                        
                    </td>
                </tr>
            </table>
            <br>
            ${question.getAnswersEditHTML()}
            <input type="submit" onclick="document.getElementById('actiontag').value = 'add_answer';" value="Добавить ответ"/> 
            <input type="submit" value="Отправить" /> 
        </form>
<c:import url= "/includes/newfooter.jsp" />
