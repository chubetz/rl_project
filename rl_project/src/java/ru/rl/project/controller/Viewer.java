/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.rl.project.edu.ITreeElement;
import ru.rl.project.edu.Realm;
import ru.rl.project.edu.Storage;
import ru.rl.project.edu.Theme;
import ru.rl.project.exception.JDBCException;
import ru.rl.project.util.Utils;

/**
 *
 * @author mithia
 */
public class Viewer extends ErrorHandlingServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("info");
        String url = null;
        switch (action) {
            case "questions":
                url = "/view_list.jsp";
                request.setAttribute("title", "Список загруженных вопросов");
                try {
                    ViewUtils.fillAttributesQuestions(request);
                } catch (JDBCException e) {
                    request.setAttribute("exception", e);
                    getServletContext().getRequestDispatcher("/db_error.jsp").forward(request, response);                    
                }
                break;
            case "realms":
                url = "/realms/view_list.jsp";
                request.setAttribute("title", "Список загруженных областей");
                request.setAttribute("realms", Realm.getMap().values());
                break;
            case "themes":
                url = "/themes/view_list.jsp";
                request.setAttribute("title", "Список имеющихся тем");
                request.setAttribute("themes", Theme.getMap().values());
                break;
            case "images":
                url = "/view_list.jsp";
                request.setAttribute("title", "Библиотека изображений");
                try {
                    ViewUtils.fillAttributesImages(request, getServletContext());
                } catch (JDBCException e) {
                    request.setAttribute("exception", e);
                    getServletContext().getRequestDispatcher("/db_error.jsp").forward(request, response);                    
                }
                break;
            case "tree":
                url = "/tree.jsp";
                request.setAttribute("title", "Дерево сущностей");
                request.setAttribute("treeSign", ITreeElement.MAIN_TREE.getTreeSign());
                Map<String, Object> addInfo = Utils.translateWebData(request.getParameterMap());
                addInfo.put("isRoot", true);
                request.setAttribute("treeHTML", ITreeElement.MAIN_TREE.getTreeHTML(addInfo));
                try {
                    ViewUtils.fillAttributesImages(request, getServletContext());
                } catch (JDBCException e) {
                    request.setAttribute("exception", e);
                    getServletContext().getRequestDispatcher("/db_error.jsp").forward(request, response);                    
                }
                break;
        }
            
        if (Storage.getJdbcException() != null) {
            request.setAttribute("exception", Storage.getJdbcException());
            url = "/db_error.jsp";                   
        }
        if (url != null) 
            getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Generating HTML View";
    }// </editor-fold>

}
