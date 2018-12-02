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
import ru.rl.project.edu.Learn;
import ru.rl.project.edu.Test;
import ru.rl.project.edu.TreeUtils;
import ru.rl.project.users.State;
import ru.rl.project.users.User;
import ru.rl.project.util.Constants;
import ru.rl.project.util.Utils;

/**
 *
 * @author d.gorshenin
 */
public class TestServlet extends ErrorHandlingServlet {

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
        Utils.print(request.getParameterMap());
        String url = null;
        State state = User.getDefaultUser().getState();
        String action = request.getParameter("action");
        String subAction = request.getParameter("subAction");
        if (subAction == null) {
            subAction = "";
        }
        switch (action) {
            case "test":
                String nodeId = request.getParameter("nodeId");
                String doNext = request.getParameter("doNext");
                if (subAction != null && subAction.equals("forced_new")) {
                    //request.setAttribute("jsp_main", "/includes/learning_module.jsp");
                    state.stopTest(TreeUtils.getById(nodeId));
                }
                Test test = state.getTest(TreeUtils.getById(nodeId));
                if (subAction.equals("testAnswer")) {
                    test.getCurrent().sendAnswer(request.getParameterMap());
                    test.next();
                }
                if (subAction.equals("show_details")) {
                    request.setAttribute("show_details", true);
                }
//                if (doNext != null && doNext.equals("true"))
//                    learn.next();
//                if (subAction != null && subAction.equals("stop")) {
//                    //request.setAttribute("jsp_main", "/includes/learning_module.jsp");
//                    state.stopLearn(learn.getMainNode());
//                    url = "/learning.jsp";
//                    break;
//                }
//                
                request.setAttribute("exam_mode", "on");
                
                if (test.getCurrent() == null) { //задания кончились
                    if (subAction.equals("stop")) {
                        state.stopTest(TreeUtils.getById(nodeId));
                        url="/testing.jsp";
                        break;
                    } else {
                        request.setAttribute("test", test);
                        request.setAttribute("subtitle", Constants.TEST_RESULTS + ": " + test.getTitle());
                        request.setAttribute("jsp_main", "/includes/test_view_result.jsp");
                        
                    }
                    
                } else {
                    request.setAttribute("testElement", test.getCurrent());
                    request.setAttribute("subtitle", Constants.TEST_MODULE + ": " + test.getTitle());
                    request.setAttribute("jsp_main", "/includes/test_view.jsp");
                    
                }
                
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
