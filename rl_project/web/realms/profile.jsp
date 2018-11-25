<%-- 
    Document   : realmProfile
    Created on : Sep 29, 2018, 12:11:24 PM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url= "/includes/newheader.jsp" />
<c:import url= "/includes/icons.jsp" />

<%--form name="new" action="view" method="GET">
    <input type="hidden" name="info" value="realms">
    <input class="calibri_new" type="submit" value="Переход к списку областей" />
</form--%>

<c:if test="${realm == null}">
    Области с таким идентификатором не существует
</c:if>

<table style="visibility: ${realm == null ? 'hidden' : 'visible'}" border="0" cellpadding="1" cellspacing="0" bgcolor="black" width="500"><tr><td>

<table border="0" cellpadding="3" cellspacing="1" width="100%">

    <form name="new" action="viewProfile" method="${mode == 'edit' ? 'POST' : 'GET'}">
        <input type="hidden" name="realm" value="${realm.id}">
    
    <tr>
        <td bgcolor="${realm.treeSign.tableBgcolor}" rowspan="2" width="15%">
           
        </td>
        <td  colspan="3" align="center" class="profile_realm_1" bgcolor="${realm.treeSign.tdBgcolor}">
            <span class="profile_realm_label border" style="background: ${realm.treeSign.tableBgcolor};"><b>Раздел</b></span>
            <p style="font-size: 5px;"> </p>
            <c:choose>
                <c:when test="${mode == 'edit'}">
                    <input size="30" class="profile_realm_1 center bold" type="text" name="description" value="${realm.description}" required="true">
                </c:when>
                <c:otherwise>
                    <b>${realm.description}</b>
                </c:otherwise>
            </c:choose>
            
        </td>
    </tr>
    <tr >
        <td  class="profile_realm_2" bgcolor="${realm.treeSign.tdBgcolor}">
            <span class="profile_realm_label border" style="background: ${realm.treeSign.tableBgcolor};"><b>ID</b></span>
            
            <span>${realm.id < 0 ? '&lt;NEW&gt;' : realm.id}</span>
        </td>
        <td class="profile_realm_2" bgcolor="${realm.treeSign.tdBgcolor}">
            <span class="profile_realm_label border" style="background: ${realm.treeSign.tableBgcolor};"><b>Код</b></span>
            <c:choose>
                <c:when test="${mode == 'edit'}">
                    <input size="10" class="profile_realm_2 center " type="text" name="text" value="${realm.text}" required="true">
                </c:when>
                <c:otherwise>
                    <span >${realm.text}</span>
                </c:otherwise>
            </c:choose>
            <span class="profile_realm_label border" style="background: ${realm.treeSign.tableBgcolor};"><b>Номер</b></span>
            <c:choose>
                <c:when test="${mode == 'edit'}">
                    <input size="10" class="profile_realm_2 center " type="text" name="number" value="${realm.number}" required="true">
                </c:when>
                <c:otherwise>
                    <span >${realm.number}</span>
                </c:otherwise>
            </c:choose>
        </td>
        <td align="center" bgcolor="${realm.treeSign.tdBgcolor}">
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

    <tr ${realm.id < 0 ? 'style=\'display: none;\'' : ''}">
        <td width="10%" bgcolor="${realm.treeSign.tdBgcolor}" colspan="2" valign="top" class="calibri_link" style="font-size: 17px;" align="center">
            <br/>
            <img src="images/flashcard.png" width="80" border="1" style="border-color: ${realm.treeSign.tableBgcolor};">
            <div style="margin-top: -80px;">
                <span class="profile_realm_label border" style="background: ${realm.treeSign.tableBgcolor};"><b>Задания</b></span>
            </div>
            <span style="font-size:10px;">&nbsp;</span>
            <div>
                <c:choose>
                    <c:when test="${realm.questionsQty > 0}">
                        <b>${realm.getQuestionsHTMLLink('' + realm.questionsQty)}</b>
                    </c:when>
                    <c:otherwise>
                        ${realm.questionsQty}
                    </c:otherwise>
                </c:choose>
            </div>
            <p size="5px"/>
            <%--form name="add_question" action="controller" method="GET">
                <b style="color:red;">+</b>
                <input type="hidden" name="action" value="new_question">
                <input type="hidden" name="realm" value="${realm.id}">
                <input class="calibri_new" style="background:#E1E3E1; color:black; font-size:10px" type="submit" value="Создать новую" />
            </form--%>
        </td>
        <td bgcolor="${realm.treeSign.tdBgcolor}" colspan="2" valign="top"  class="calibri_link" style="font-size: 17px;" align="center">
            <br/>
            <img src="images/theme.png" width="80" border="1" style="border-color: ${realm.treeSign.tableBgcolor};">
            <div style="margin-top: -80px;">
                <span class="profile_realm_label border" style="background: ${realm.treeSign.tableBgcolor};"><b>Темы</b></span>
            </div>
            <span style="font-size:10px;">&nbsp;</span>
            <div style="font-size: 17px;">${realm.themesQty}</div>
            <br>
            <div align="left" style="font-size: 14px;"><b>${realm.themesTableHTML}</b></div>
            <form action="viewProfile" method="GET">
                <b style="color:red;">+</b>
                <input type="hidden" name="theme" value="new">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="realmId" value="${realm.id}">
                <input class="calibri_new" style="background:#E1E3E1; color:black; font-size:10px" type="submit" value="Создать новую" />
            </form>
        </td>
                    
    </tr>
                
</table>

            
</td></tr></table>

<c:import url= "/includes/newfooter.jsp" />
