<%-- 
    Document   : profile
    Created on : Oct 5, 2018, 3:29:54 PM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url= "/includes/newheader.jsp" />
<c:import url= "/includes/icons.jsp" />

<c:if test="${theme == null}">
    <c:import url= "/includes/icons.jsp" />

    Темы с таким идентификатором не существует
</c:if>

    <%--table><tr>
            <td>
<form style="visibility: ${theme == null || theme.realm == null ? 'hidden' : 'visible'}" name="new" action="viewProfile" method="GET">
    <input type="hidden" name="realm" value="${theme.realm.id}">
    <input class="calibri_new" type="submit" value="Переход к профилю области" />
</form>
            </td>    
            <td>
<form style="visibility: ${theme == null ? 'hidden' : 'visible'}" name="new" action="view" method="GET">
    <input type="hidden" name="info" value="themes">
    <input class="calibri_new" type="submit" value="Переход к списку тем" />
</form>
            </td>    
    </tr></table--%>
<table style="visibility: ${theme == null ? 'hidden' : 'visible'}" border="0" cellpadding="1" cellspacing="0" bgcolor="black" width="700"><tr><td>

<table border="0" cellpadding="3" cellspacing="1" width="100%">

    <form name="new" action="viewProfile" method="${mode == 'edit' ? 'POST' : 'GET'}">
        <input type="hidden" name="theme" value="${theme.id}">
    
    <tr>
        <td bgcolor="${theme.treeSign.tableBgcolor}" rowspan="2" width="15%">
           
        </td>
        <td  colspan="3" align="center" class="profile_realm_1" bgcolor="${theme.treeSign.tdBgcolor}">
            <span class="profile_realm_label border" style="background: ${theme.treeSign.tableBgcolor};"><b>Тема</b></span>
            <p style="font-size: 5px;"> </p>
            <c:choose>
                <c:when test="${mode == 'edit'}">
                    <input size="30" class="profile_realm_1 center bold" type="text" name="text" value="${theme.text}" required="true">
                </c:when>
                <c:otherwise>
                    <b>${theme.text}</b>
                </c:otherwise>
            </c:choose>
            
        </td>
    </tr>
    <tr>
        <td  colspan="3" align="center" class="profile_realm_2" bgcolor="${theme.treeSign.tdBgcolor}">
            <span class="profile_realm_label border" style="background: ${theme.treeSign.tableBgcolor};"><b>Раздел</b></span>
            <c:choose>
                <c:when test="${theme.realm == null}">
                    <%--input size="30" class="profile_realm_1 center bold" type="text" name="description" value="${theme.text}" required="true"--%>
                    ${theme.realmsHTML}
                </c:when>
                <c:otherwise>
                    <c:if test="${theme.id < 0}">
                        <input type="hidden" name="realmId" value="${theme.realm.id}">
                    </c:if>
                    <b class="calibri_link_th">${theme.realm.getProfileLink(theme.realm.description)}</b>
                </c:otherwise>
            </c:choose>
            
        </td>
    </tr>
    <tr >
        <td  class="profile_realm_2" bgcolor="${theme.treeSign.tdBgcolor}">
            <span class="profile_realm_label border" style="background: ${theme.treeSign.tableBgcolor};"><b>ID</b></span>
            
            <span>${theme.id < 0 ? '&lt;NEW&gt;' : theme.id}</span>
        </td>
        <td class="profile_realm_2" bgcolor="${theme.treeSign.tdBgcolor}">
            <span class="profile_realm_label border" style="background: ${theme.treeSign.tableBgcolor};"><b>Номер</b></span>
            <c:choose>
                <c:when test="${mode == 'edit'}">
                    <input size="10" class="profile_realm_2 center " type="text" name="number" value="${theme.numberStr}" required="true">
                </c:when>
                <c:otherwise>
                    <span >${theme.numberStr}</span>
                </c:otherwise>
            </c:choose>
        </td>
        <td align="center" bgcolor="${theme.treeSign.tdBgcolor}">
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

    <tr ${theme.id < 0 ? 'style=\'display: none;\'' : ''}>
        <td width="10%" bgcolor="${theme.treeSign.tdBgcolor}" colspan="2" valign="top" class="calibri_link_th" style="font-size: 17px;" align="center">
            <br/>
            <img src="images/flashcard.png" width="80" border="1" style="border-color: ${theme.treeSign.tableBgcolor};">
            <div style="margin-top: -80px;">
                <span class="profile_realm_label border" style="background: ${theme.treeSign.tableBgcolor};"><b>Карточки</b></span>
            </div>
            <span style="font-size:10px;">&nbsp;</span>
            <div>
                <c:choose>
                    <c:when test="${theme.questionsQty > 0}">
                        <b>${theme.getQuestionsHTMLLink('' + theme.questionsQty)}</b>
                    </c:when>
                    <c:otherwise>
                        ${theme.questionsQty}
                    </c:otherwise>
                </c:choose>
            </div>
            <p/>
            <!--form name="add_question" action="controller" method="GET">
                <c:if test="${theme.invalidQuestionQty != 0}">
                    <span style="font-size:11px; color: #DA5600;">(${theme.invalidQuestionQty} нуждаются в исправлении)</span>
                    <br>
                </c:if>
                <b style="color:red;">+</b>
                <input type="hidden" name="action" value="new_question">
                <input type="hidden" name="theme" value="${theme.id}">
                <input class="calibri_new" style="background:#E1E3E1; color:black; font-size:10px" type="submit" value="Создать новую" />
            </form-->
        </td>
        <td bgcolor="${theme.treeSign.tdBgcolor}" colspan="2" valign="top"  class="calibri_link" style="font-size: 17px;" align="center">
            <br/>
            <img src="images/theme.png" width="80" border="1" style="border-color: ${theme.treeSign.tableBgcolor};">
            <div style="margin-top: -80px;">
                <span class="profile_realm_label border" style="background: ${theme.treeSign.tableBgcolor};"><b>Правила</b></span>
            </div>
            <span style="font-size:10px;">&nbsp;</span>
            <div style="font-size: 17px;">${theme.rulesQty}</div>
            <br>
            <div align="left" style="font-size: 14px;"><b>${theme.rulesTableHTML}</b></div>
            <form action="viewProfile" method="GET">
                <b style="color:red;">+</b>
                <input type="hidden" name="rule" value="new">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="themeId" value="${theme.id}">
                <input class="calibri_new" style="background:#E1E3E1; color:black; font-size:10px" type="submit" value="Создать новое" />
            </form>
        </td>
        <%--td bgcolor="${theme.treeSign.tdBgcolor}" colspan="2" valign="top"  class="calibri_link" style="font-size: 17px;" align="center">
            <br/>
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
