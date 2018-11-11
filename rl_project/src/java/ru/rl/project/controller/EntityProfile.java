/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.rl.project.edu.Realm;
import ru.rl.project.edu.Theme;
import ru.rl.project.exception.JDBCException;
import ru.rl.project.util.Utils;

/**
 *
 * @author d.gorshenin
 */
public class EntityProfile extends ErrorHandlingServlet {

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
        request.setCharacterEncoding ("UTF-8");
        String url = null;
        String action = null;
        Enumeration<String> parNames = request.getParameterNames();
        while (parNames.hasMoreElements()) {
            String parName = parNames.nextElement();
            switch (parName) {
                case "theme":
                    //url = "/themeProfile.jsp";
                    url = "/themes/profile.jsp";
                    if (request.getParameter(parName).equals("new"))
                        request.setAttribute("theme", Theme.getMock(request.getParameter("realmId")));
                    else
                        request.setAttribute("theme", Theme.getById(request.getParameter(parName)));
                    action = request.getParameter("action");
                    if (action != null) {
                        switch (action) {
                            case "edit":
                                request.setAttribute("mode", "edit");
                                break;
                            case "save":
                                Theme theme;
                                try {
                                    theme = Theme.saveTheme(request.getParameter("theme"), request.getParameterMap());
                                } catch (JDBCException ex) {
                                    Logger.getLogger(EntityProfile.class.getName()).log(Level.SEVERE, null, ex);
                                    url = "/db_error.jsp";
                                    request.setAttribute("exception", ex);
                                    break;
                                }
                                url = null;
                                response.sendRedirect("viewProfile?theme=" + theme.getId());
                                break;
                        }
                    }
                    request.setAttribute("title", "Профиль темы");
                    break;
                case "realm":
                    url = "/realms/profile.jsp";
                    if (request.getParameter(parName).equals("new"))
                        request.setAttribute("realm", Realm.getMock());
                    else
                        request.setAttribute("realm", Realm.getById(request.getParameter(parName)));
                    action = request.getParameter("action");
                    if (action != null) {
                        switch (action) {
                            case "edit":
                                request.setAttribute("mode", "edit");
                                break;
                            case "save":
                                Realm realm;
                                try {
                                    realm = Realm.saveRealm(request.getParameter("realm"), request.getParameterMap());
                                } catch (JDBCException ex) {
                                    Logger.getLogger(EntityProfile.class.getName()).log(Level.SEVERE, null, ex);
                                    url = "/db_error.jsp";
                                    request.setAttribute("exception", ex);
                                    break;
                                }
                                url = null;
                                response.sendRedirect("viewProfile?realm=" + realm.getId());
                                break;
                        }
                    }
                    request.setAttribute("title", "Профиль предметной области");
                    break;
                
            }
            
            break;
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
        return "Short description";
    }// </editor-fold>

}
