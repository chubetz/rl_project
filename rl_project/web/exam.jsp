<%-- 
    Document   : exam
    Created on : Jul 18, 2018, 3:31:45 PM
    Author     : d.gorshenin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url= "/includes/newheader.jsp" />
<%--c:import url= "/includes/icons.jsp" /--%>
        <table width="75%">
            <tr>
                <td width="90%" align="right">
                    <form method="POST" action="doActive">
                        <input type="hidden" name="id" value="${theme.id}">
                        <input type="hidden" name="action" value="cancelTheme">
                        <input type="hidden" name="subAction" value="cancel">
                        <input type="submit" value="Отменить">
                    </form>
                </td>
                <td width="10%">
                    ${exam.stopBtn}
                </td>
            </tr>
        </table>
        <table width="75%" border="1">
            <tr>
                <td width="50%" bgcolor="black" style="color:white">
                    Область: <b>${theme.realm.description}</b><br>
                    Тема: <b>${theme.getProfileLink(theme.text, "\"color:white\"")}</b>
                </td>
                <td align="center" width="30%" bgcolor="e7fdf2" style="font-family: Tahoma;font-size: 16px; color: #c8073d"><b>Карточка ${exam.num}</b> <font style="font-size:12px;">(осталось ${exam.remaining})</font></td>
                <td align="center" width="20%" bgcolor="FCDEDE" style="font-family: Courier New;font-size: 16px; color: #075F18"><b>${exam.question.typeText}</b></td>
            </tr>
            <tr>
                <td colspan="3">
                    ${exam.question.examHTML}
                </td>
            </tr>
            <tr>
                <td colspan="3" align="center">
                    ${exam.answerHTML}
                </td>
            </tr>
            <!--tr>
                <td colspan="3" align="center">
                    ${exam.nextButtonHTML}
                </td>
            </tr-->
        </table>
<c:import url= "/includes/newfooter.jsp" />
