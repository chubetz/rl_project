<%-- 
    Document   : newheader
    Created on : Sep 10, 2018, 6:19:40 AM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${title}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newcss.css" type="text/css"/>
        <c:if test="${edit_mode.equals('on')}">
            <script type="text/javascript" src="tinymce/tinymce.js"></script>
            <!-- TinyMCE -->
            <script type="text/javascript">
            tinyMCE.init({
                //mode : "exact",
                //elements : "rrr"
                selector: 'textarea',
                forced_root_block : false,
                menubar: false
             });
            </script>
            <!-- /TinyMCE -->
        </c:if>
        <c:if test="${exam_mode.equals('on')}">
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
            <script src="colorbox/jquery.colorbox-min.js"></script>
            <script type="text/javascript">
                function getCommentHTML(id, userAnswer) {
                     url = "directInfo?entity=answer&field=commentHTML&id=" + id + "&userAnswer=" + userAnswer;
                     jQuery.colorbox({href:url});
                }
            </script>
        </c:if>
        
    </head>
    <body bgcolor="#d8cFaA">
        <table width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td height="2" colspan="2" bgcolor="black"/>
            </tr>
            <tr>
                <td width="65"/>
                <td>
                    <div class="header_palatino">${title}</div>
                </td>
            </tr>
            <tr>
                <td height="2" colspan="2" bgcolor="black"/>
            </tr>
        </table>
                    <div class="home_logo"><a href="index.jsp"><img  src="${pageContext.request.contextPath}/images/logo.png"><a></div>
