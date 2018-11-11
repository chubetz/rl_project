<%-- 
    Document   : themeProfile
    Created on : Jul 16, 2018, 4:20:13 PM
    Author     : d.gorshenin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url= "/includes/newheader.jsp" />
<c:import url= "/includes/icons.jsp" />
        <table border="1" width="700" cellpadding="5">
            <tr>
                <td colspan="2" style="color:blue;font-family:Courier New Cyr;">
                    Предметная область: <b>${theme.realm.description}</b>
                </td>
            </tr>
            <tr>
                <td colspan="2" bgcolor="black" style="color:white;font-family:Tahoma;font-weight:bold">
                    ${theme.numberStr}. ${theme.text}
                </td>
            </tr>
            <tr>
                <td bgcolor="E0B2B2" style="color:#224937;font-family:Palatino Linotype;font-weight:bold">
                    Карточек в теме: ${theme.getQuestionsHTMLLink("" + theme.getQuestionMap().size())}
                    <c:if test="${theme.invalidQuestionQty != 0}">
                        (${theme.invalidQuestionQty} нуждаются в исправлении)
                    </c:if>
                </td>
                <td bgcolor="green" align="center">
                    <form method="POST" action="doActive">
                        <input type="hidden" name="id" value="${theme.id}">
                        <input type="hidden" name="action" value="doTheme">
                        <input type="Submit" value="Проверить знания" ${theme.examinable ? "" : "disabled"}>
                    </form>
                </td>
            </tr>
            <tr>
                <td colspan="2" bgcolor="#FGFFFF">
                    <div style="font-size:14px;color:black;font-family:Tahoma;font-weight:bold">История изучения</div>
                    ${theme.themeExamsHTML}
                </td>
            </tr>
            
        </table>
            
<c:import url= "/includes/newfooter.jsp" />
