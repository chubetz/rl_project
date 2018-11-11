/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.rl.project.edu.Exam;
import ru.rl.project.edu.Theme;
import ru.rl.project.exception.ExamException;
import ru.rl.project.exception.JDBCException;
import ru.rl.project.exception.NoMoreQuestionException;
import ru.rl.project.users.State;
import ru.rl.project.users.User;

/**
 *
 * @author d.gorshenin
 */
public class Activator extends HttpServlet {
    
    private void stopTheme(Theme theme, HttpServletRequest request) throws JDBCException {
        State state = User.getDefaultUser().getState();
        Exam exam = state.stopExam(theme);
        request.setAttribute("exam", exam);
        request.setAttribute("theme", theme);
        request.setAttribute("title", "Итоги проверки");
        request.setAttribute("examResult", "Проверка завершена");
    }

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
        String url = null;
        State state = User.getDefaultUser().getState();
        String action = request.getParameter("action");
        String subAction = request.getParameter("subAction");
        switch (action) {
            case "doTheme":
                Theme theme = Theme.getById(request.getParameter("id"));
                try {
                    Exam exam = state.getExam(theme);
                    request.setAttribute("exam", exam);
                    request.setAttribute("theme", theme);
                    exam.processWorkflow(subAction, request.getParameterMap());
                    request.setAttribute("title", "Проверка знаний");
                    request.setAttribute("exam_mode", "on");
                    url = "/exam.jsp";
                } catch (NoMoreQuestionException nmqe) { // все карточки отработаны
                    try {
                        stopTheme(theme, request);
                        url = "/examFinished.jsp";
                    } catch (JDBCException ex) {
                        Logger.getLogger(Activator.class.getName()).log(Level.SEVERE, null, ex);
                        url = "/db_error.jsp";
                        request.setAttribute("exception", ex);
                        break;
                    }
                }
                break;
            case "stopTheme":
                theme = Theme.getById(request.getParameter("id"));
                try {
                    stopTheme(theme, request);
                    url = "/examFinished.jsp";
                } catch (JDBCException ex) {
                    Logger.getLogger(Activator.class.getName()).log(Level.SEVERE, null, ex);
                    url = "/db_error.jsp";
                    request.setAttribute("exception", ex);
                    break;
                }
                break;
            case "cancelTheme":
                theme = Theme.getById(request.getParameter("id"));
                Exam exam = state.cancelExam(theme);
                request.setAttribute("exam", exam);
                request.setAttribute("theme", theme);
                request.setAttribute("title", "Итоги проверки");
                request.setAttribute("examResult", "Проверка отменена");
                url = "/examFinished.jsp";
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
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }

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
        return "Servlet which performs an examination";
    }// </editor-fold>

}
