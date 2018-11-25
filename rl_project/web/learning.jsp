<%-- 
    Document   : index
    Created on : Sep 8, 2018, 6:36:02 PM
    Author     : mithia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="ru.rl.project.util.*" %>
<%@ page import="ru.rl.project.edu.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    request.setAttribute("jsp_main", "/includes/empty.jsp");
    request.setAttribute("title", Constants.MAIN_TITLE);
    request.setAttribute("subtitle", Constants.LEARNING_MODULE);
    
    request.setAttribute("treeSign", ITreeElement.LEARNING_TREE.getTreeSign());
    Map<String, Object> addInfo = Utils.translateWebData(request.getParameterMap());
    addInfo.put("isRoot", true);
    addInfo.put("lastBranch", "Theme");
    addInfo.put("url", "learning.jsp");
    if (addInfo.get("info") == null || !addInfo.get("info").equals("tree")) { //при первом открытии дерева надо развернуть все
        addInfo.put("expandAll", true);
    }
    
    if (addInfo.get("details") != null) {
        request.setAttribute("jsp_main", "/includes/profile_learning.jsp?details=" + addInfo.get("details"));
    }
    
    request.setAttribute("tree", ITreeElement.LEARNING_TREE.getTreeHTML_Learning(addInfo));
%>
<c:import url= "/includes/newheader.jsp" />
<%--c:import url= "/includes/icons.jsp" /--%>
<c:import url= "/includes/main.jsp" />

<c:import url= "/includes/newfooter.jsp" />
