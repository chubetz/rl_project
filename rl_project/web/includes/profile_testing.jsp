<%-- 
    Document   : profile_learning
    Created on : Nov 25, 2018, 11:15:52 PM
    Author     : mithia
--%>

<%@page import="ru.rl.project.users.User"%>
<%@page import="ru.rl.project.users.State"%>
<%@page import="ru.rl.project.edu.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String details = request.getParameter("details");
    Realm realm = null;
    Theme theme = null;
    State state = User.getDefaultUser().getState();;
    Test test = null;
    String[] type_and_id = details.split("_");
    switch (type_and_id[0]) {
        case "Realm":
            realm = Realm.getById(type_and_id[1]);
            request.setAttribute("nodeId", realm.getTreeSign().getId());
            request.setAttribute("hasTest", state.hasTest(realm));
            break;
//        case "Theme":
//            theme = Theme.getById(type_and_id[1]);
//            request.setAttribute("nodeId", theme.getTreeSign().getId());
//            request.setAttribute("hasLearn", state.hasLearn(theme));
//            break;
            
    }
    request.setAttribute("realm", realm);
    //request.setAttribute("theme", theme);



%>
<table border="0"  width="100%">
    <tr>
        <td width="100%" align="center" valign="middle">
            
            <table border="1" cellpadding="5" width="80%" height="80%" bgcolor="#FCF5C0">
                <tr>
                    <td>
                        <c:if test="${realm != null}">
                            Раздел: <b>${realm.description}</b><br>
                            Количество тем: <b>${realm.getThemeMap().size()}</b><br>
                            Количество правил: <b>${realm.getRuleMap().size()}</b><br>
                            Количество проверочных заданий: <b>${realm.getTestQuestionsNumber()}</b><br>
                        </c:if>
                        
                        <%--c:if test="${theme != null}">
                            Тема: <b>${theme.text}</b><br>
                            (Раздел: <b>${theme.realm.description}</b>)<br>
                            Количество правил: <b>${theme.getRuleMap().size()}</b><br>
                            Количество заданий: <b>${theme.getQuestionMap().size()}</b><br>
                        </c:if--%>
                            <br>
                            <c:if test="${!hasTest}">
                                <form name="start" action="test" method="POST">
                                    <input type="hidden" name="action" value="test"/>
                                    <input type="hidden" name="nodeId" value="${nodeId}"/>
                                    <input type="Submit" value="Начать проверку"/>
                                </form>
                            </c:if>
                            <c:if test="${hasTest}">
                                <table border="0">
                                    <tr>
                                        <td>
                                <form name="start" action="test" method="POST">
                                    <input type="hidden" name="action" value="test"/>
                                    <input type="hidden" name="nodeId" value="${nodeId}"/>
                                    <input type="Submit" value="Продолжить начатую проверку"/>
                                </form>
                                            
                                        </td>
                                        <td>
                                <form name="start" action="test" method="POST">
                                    <input type="hidden" name="action" value="test"/>
                                    <input type="hidden" name="nodeId" value="${nodeId}"/>
                                    <input type="hidden" name="subAction" value="forced_new"/>
                                    <input type="Submit" value="Начать новую проверку"/>
                                </form>
                                            
                                        </td>
                                    </tr>
                                </table>
                                
                                
                            </c:if>
                    </td>
                </tr>
                
            </table>
            
            
        </td>
    </tr>
</table>