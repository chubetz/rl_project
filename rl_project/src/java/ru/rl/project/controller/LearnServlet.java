/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.rl.project.edu.ITreeElement;
import ru.rl.project.edu.Learn;
import ru.rl.project.edu.TreeUtils;
import ru.rl.project.users.State;
import ru.rl.project.users.User;
import ru.rl.project.util.Constants;

/**
 *
 * @author d.gorshenin
 */
public class LearnServlet extends ErrorHandlingServlet {

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
        switch (action) {
            case "learn":
                String nodeId = request.getParameter("nodeId");
                String doNext = request.getParameter("doNext");
                Learn learn = state.getLearn(TreeUtils.getById(nodeId));
                if (doNext != null && doNext.equals("true"))
                    learn.next();
                request.setAttribute("learnElement", learn.getCurrent());
                request.setAttribute("subtitle", Constants.LEARNING_MODULE + ": " + learn.getTitle());
                request.setAttribute("jsp_main", "/includes/learn_view.jsp");
                url = "/template.jsp";
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
