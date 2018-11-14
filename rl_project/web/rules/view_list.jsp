<%-- 
    Document   : view_list
    Created on : Oct 4, 2018, 5:14:10 PM
    Author     : d.gorshenin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url= "/includes/newheader.jsp" />
<c:import url= "/includes/icons.jsp" />

<h3>(всего ${rules.size()} элементов)</h3>

<%--form name="new" action="viewProfile" method="GET">
    <input type="hidden" name="rule" value="new">
    <input type="hidden" name="action" value="edit">
    <input class="calibri_new" type="submit" value="Создать" />
</form--%>
<table border="0" cellpadding="1" cellspacing="0" bgcolor="black"><tr><td>

<table border="0" cellpadding="5" cellspacing="1">
    <tr class="tdbg6 calibri_hdr" align="center">
        <td>
            ID
        </td>
        <td>
            №
        </td>
        <td>
            Текст
        </td>
        <td>
            Тема
        </td>
        <td>
            Карточки
        </td>
    </tr>
    <% boolean contrast = true;%>
<c:forEach var="r" items="${rules}">
    <tr class="calibri_cell <%= contrast ? "tdbg5" : "tdbg51" %>">
        <td>${r.id}</td>
        <td><b>${r.number}</b></td>
        <td><a class="realm_link" href="${r.profileURL}"><b>${r.text}</b></a></td>
        <td class="calibri_link_th"><b>${r.theme.getProfileLink(r.theme.text)}</b></td>
        <td align="center" class="calibri_link_th" style="font-size: 17px;">
                <c:choose>
                    <c:when test="${r.questionsQty > 0}">
                        <b>${r.getQuestionsHTMLLink('' + r.questionsQty)}</b>
                    </c:when>
                    <c:otherwise>
                        ${r.questionsQty}
                    </c:otherwise> 
                </c:choose>
        </td>
    </tr>
    <% contrast = !contrast; %>
</c:forEach>
</table>
    
</td></tr></table>



<c:import url= "/includes/newfooter.jsp" />
