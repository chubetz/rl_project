<%-- 
    Document   : profile_learning
    Created on : Nov 25, 2018, 11:15:52 PM
    Author     : mithia
--%>

<%@page import="ru.rl.project.edu.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%



%>
<table border="0"  width="100%">
    <tr>
        <td width="100%" align="center" valign="middle">
            
            <table border="1" cellpadding="5" width="100%" bgcolor="#FCF5C0">
                <tr>
                    <td>
                        ${subtitle}
                    </td>
                </tr>
                <tr>
                    <td>
                        Всего заданий: <b>${test.totalQuestionsQty}</b><br>
                        Правильно решенных заданий: <b>${test.correctAnswerQty}</b> (${test.correctPercentStr}%)<br>
                        Приблизительная школьная оценка: <b>${test.schoolMark}</b>   :)<p>
                        <c:if test="${show_details}">
                            <c:forEach var="c" items="${test.elements}">
                                ${c.detailsHTML}
                            <p>
                            </c:forEach>
                            <form name="final" action="test" method="POST">
                                <input type="hidden" name="action" value="test">
                                <input type="hidden" name="nodeId" value="${test.node.treeSign.id}">
                                <input type="hidden" name="subAction" value="stop">
                                <input type="submit" value="Завершить проверку">
                            </form>
                        </c:if>
                        <c:if test="${!show_details}">
                            <form name="final" action="test" method="POST">
                                <input type="hidden" name="action" value="test">
                                <input type="hidden" name="nodeId" value="${test.node.treeSign.id}">
                                <input type="hidden" name="subAction" value="show_details">
                                <input type="submit" value="Показать подробные сведения">
                            </form>
                        </c:if>
                    </td>
                </tr>
                
            </table>
            
            
        </td>
    </tr>
</table>