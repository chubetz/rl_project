<%-- 
    Document   : new_question
    Created on : May 15, 2018, 12:18:01 PM
    Author     : d.gorshenin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url= "/includes/newheader.jsp" />
<%--c:import url= "/includes/icons.jsp" /--%>
        <table>
            <tr>
                <td>
                    <form name="back_to_edit" action="controller" method="POST">
                        <input type="hidden" name="action" value="load_edit_form">        
                        <input type="hidden" name="id" value="${question.getId()}">        
                        <input type="submit" value="Вернуться к редактированию" /> 
                    </form>                    
                </td>
                <td>
                    <form name="back_to_realm" action="viewProfile" method="GET">
                        <input type="hidden" name="rule" value="${question.rule.id}">        
                        <input type="submit" value="Переход к правилу" /> 
                    </form>                    
                </td>
                <td>
                    <form name="back_to_list" action="view" method="GET">
                        <input type="hidden" name="info" value="questions">        
                        <input type="hidden" name="ruleId" value="${question.rule.id}">        
                        <input type="submit" value="Переход к списку заданий данного правила" /> 
                    </form>                    
                </td>
            </tr>
        </table>
        ${question}<br>
        <table>
            <tr>
                <td colspan="${question.getAnswerMap().size()}">
                    ${question.getStr("text")}
                </td>
            </tr>
        ${question.getAnswersTableHTML()}
        </table>
        <br>
        ${question.isValid() ? "Вопрос составлен корректно" : "<font color=red>Вопрос составлен некорректно</font>"}
        <%--p>
        Выбранные темы:
        ${question.getThemesHTML()}
        <p>
        Доступные темы:
        
        <form name="link_themes" action="controller" method="POST">
            <input type="hidden" name="action" value="link_themes">        
            <input type="hidden" name="id" value="${question.getId()}">        
            ${question.getRealm().getThemesForSelectHTML()}
            <input type="submit" value="Отправить" /> 
            
        </form--%>
<c:import url= "/includes/newfooter.jsp" />
