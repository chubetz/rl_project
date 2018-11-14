<%-- 
    Document   : profile
    Created on : Oct 5, 2018, 3:29:54 PM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url= "/includes/newheader.jsp" />
<c:import url= "/includes/icons.jsp" />

<c:if test="${rule == null}">
    <c:import url= "/includes/icons.jsp" />

    Правила с таким идентификатором не существует
</c:if>

    <%--table><tr>
            <td>
<form style="visibility: ${rule == null || rule.theme == null ? 'hidden' : 'visible'}" name="new" action="viewProfile" method="GET">
    <input type="hidden" name="theme" value="${rule.theme.id}">
    <input class="calibri_new" type="submit" value="Переход к профилю темы" />
</form>
            </td>    
            <td>
<form style="visibility: ${rule == null ? 'hidden' : 'visible'}" name="new" action="view" method="GET">
    <input type="hidden" name="info" value="rules">
    <input class="calibri_new" type="submit" value="Переход к списку правил" />
</form>
            </td>    
    </tr></table--%>
<table style="visibility: ${rule == null ? 'hidden' : 'visible'}" border="0" cellpadding="1" cellspacing="0" bgcolor="black" width="700"><tr><td>

<table border="0" cellpadding="3" cellspacing="1" width="100%">

    <form name="new" action="viewProfile" method="${mode == 'edit' ? 'POST' : 'GET'}">
        <input type="hidden" name="rule" value="${rule.id}">
    
    <tr>
        <td bgcolor="${rule.treeSign.tableBgcolor}" rowspan="2" width="15%">
           
        </td>
        <td  colspan="3" class="profile_realm_1" bgcolor="${rule.treeSign.tdBgcolor}">
            <span class="profile_realm_label border" style="background: ${rule.treeSign.tableBgcolor};"><b>Правило</b></span>
            <p style="font-size: 5px;"> </p>
            <c:choose>
                <c:when test="${mode == 'edit'}">
                    <textarea name="text" rows="4" cols="40">${rule.getStrLtGt("text")}</textarea>
                    <%--input size="40" type="text" name="text" value="${rule.text}" required="true"--%>
                </c:when>
                <c:otherwise>
                    ${rule.text}
                </c:otherwise>
            </c:choose>
            
        </td>
    </tr>
    <tr>
        <td  colspan="3" align="center" class="profile_realm_2" bgcolor="${rule.treeSign.tdBgcolor}">
            <span class="profile_realm_label border" style="background: ${rule.treeSign.tableBgcolor};"><b>Тема</b></span>
            <%--c:choose>
                <c:when test="${mode == 'edit'}">
                    ${rule.themesHTML}
                </c:when>
                <c:otherwise--%>
                    <b class="calibri_link_th">${rule.theme.getProfileLink(rule.theme.text)}</b>
                    <input type="hidden" name="themeId" value="${rule.theme.id}">
                <%--/c:otherwise>
            </c:choose--%>
            
        </td>
    </tr>
    <tr >
        <td  class="profile_realm_2" bgcolor="${rule.treeSign.tdBgcolor}">
            <span class="profile_realm_label border" style="background: ${rule.treeSign.tableBgcolor};"><b>ID</b></span>
            
            <span>${rule.id < 0 ? '&lt;NEW&gt;' : rule.id}</span>
        </td>
        <td class="profile_realm_2" bgcolor="${rule.treeSign.tdBgcolor}">
            <span class="profile_realm_label border" style="background: ${rule.treeSign.tableBgcolor};"><b>Номер</b></span>
            <c:choose>
                <c:when test="${mode == 'edit'}">
                    <input size="10" class="profile_realm_2 center " type="text" name="number" value="${rule.number}" required="true">
                </c:when>
                <c:otherwise>
                    <span >${rule.number}</span>
                </c:otherwise>
            </c:choose>
        </td>
        <td align="center" bgcolor="${rule.treeSign.tdBgcolor}">
            <c:choose>
                <c:when test="${mode == 'edit'}">
                    <input type="hidden" name="action" value="save">
                    <input class="calibri_new" style="background:black; color:white;" type="submit" value="Сохранить" />
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="action" value="edit">
                    <input class="calibri_new" style="background:#E1E3E1; color:black;" type="submit" value="Редактировать" />
                </c:otherwise>
            </c:choose>
       </td>
    </tr>

    </form>

    <tr ${rule.id < 0 ? 'style=\'display: none;\'' : ''}>
        <td width="10%" bgcolor="${rule.treeSign.tdBgcolor}" colspan="2" valign="top" class="calibri_link_th" style="font-size: 17px;" align="center">
            <br/>
            <img src="images/flashcard.png" width="80" border="1" style="border-color: ${rule.treeSign.tableBgcolor};">
            <div style="margin-top: -80px;">
                <span class="profile_realm_label border" style="background: ${rule.treeSign.tableBgcolor};"><b>Задания</b></span>
            </div>
            <span style="font-size:10px;">&nbsp;</span>
            <div>
                <c:choose>
                    <c:when test="${rule.questionsQty > 0}">
                        <b>${rule.getQuestionsHTMLLink('' + rule.questionsQty)}</b>
                    </c:when>
                    <c:otherwise>
                        ${rule.questionsQty}
                    </c:otherwise>
                </c:choose>
            </div>
            <p/>
            <form name="add_question" action="controller" method="GET">
                <%--c:if test="${rule.invalidQuestionQty != 0}">
                    <span style="font-size:11px; color: #DA5600;">(${rule.invalidQuestionQty} нуждаются в исправлении)</span>
                    <br>
                </c:if--%>
                <b style="color:red;">+</b>
                <input type="hidden" name="action" value="new_question">
                <input type="hidden" name="rule" value="${rule.id}">
                <input class="calibri_new" style="background:#E1E3E1; color:black; font-size:10px" type="submit" value="Создать новое" />
            </form>
        </td>
        <td bgcolor="${rule.treeSign.tdBgcolor}" colspan="2" valign="top"  class="calibri_link" style="font-size: 17px;" align="center">
            <%--br/>
            <img src="images/exam.png" width="80" border="1" style="border-color: ${theme.treeSign.tableBgcolor};">
            <div style="margin-top: -80px;">
                <span class="profile_realm_label border" style="background: ${theme.treeSign.tableBgcolor};"><b>Проверка</b></span>
            </div>
            <span style="font-size:10px;">&nbsp;</span>
            <div style="font-size: 17px; margin-top: -10px; margin-right: -30px;">${theme.themeExamsQty}</div>
            <br>
            <form method="POST" action="doActive">
                <b style="color:red;">+</b>
                <input type="hidden" name="id" value="${theme.id}">
                <input type="hidden" name="action" value="doTheme">
                <input class="calibri_new" style="background:#E1E3E1; color:black; font-size:10px" type="Submit" value="Проверить знания" ${theme.examinable ? "" : "disabled"}>
            </form>
            <div align="left" style="font-size: 14px;"><b>${theme.examsTableHTML}</b></div>
        </td--%>
                    
    </tr>
                
</table>

            
</td></tr></table>

<c:import url= "/includes/newfooter.jsp" />
