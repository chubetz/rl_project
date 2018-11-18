/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import ru.rl.project.db.JDBCUtils;
import ru.rl.project.edu.Answer;
import ru.rl.project.edu.EduAccess;
import ru.rl.project.edu.Entity;
import ru.rl.project.edu.Image;
import ru.rl.project.edu.Question;
import ru.rl.project.edu.Realm;
import ru.rl.project.edu.Rule;
import ru.rl.project.edu.Storage;
import ru.rl.project.edu.Theme;
import ru.rl.project.edu.ThemeExam;
import ru.rl.project.edu.UserAnswer;
import ru.rl.project.exception.JDBCException;
import ru.rl.project.util.Utils;

/**
 *
 * @author d.gorshenin
 */
public class MainServlet extends ErrorHandlingServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    @Override
    public void init()
            throws ServletException {
        
        
        super.init(); //To change body of generated methods, choose Tools | Templates.
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding ("UTF-8");
        String action = request.getParameter("action");
        String url = null;
        Question question;
        Utils.print("Servlet.BEFORE!!!", request.getParameterMap());
//        try {
//            Utils.print("Servlet.GET_PAR", request.getPart("file"));
//            
//        } catch (Exception e) {
//            Utils.print("Servlet.GET_PAR_EXCEPTION", e);
//        }

        switch (action) {
            case "new_question":
                url = "/edit_question.jsp";
                Utils.print("Servlet.new_question", request.getParameterMap());
                //String realmId = request.getParameter("realm");
                String ruleId = request.getParameter("rule");
                Question mockQuestion = null;
                if (ruleId != null) {
                    mockQuestion = Question.getMockQuestion(ruleId);
                } else
                    mockQuestion = Question.getMockQuestion();
                request.setAttribute("action", "update_question");
                request.setAttribute("title", "Редактировать вопрос");
                request.setAttribute("edit_mode", "on");
                request.setAttribute("question", mockQuestion);
                break;
            case "add_answer":
                url = "/edit_question.jsp";
                Utils.print("Servlet.add_answer", request.getParameterMap());
                request.setAttribute("question", Question.getQuestionFromParameterMap(request.getParameterMap()));
                request.setAttribute("action", "update_question");
                request.setAttribute("title", "Редактировать вопрос");
                request.setAttribute("edit_mode", "on");
                break;
            case "load_edit_form":
                url = "/edit_question.jsp";
                Utils.print("Servlet.load_edit_form", request.getParameterMap());
                Utils.print(request.getParameter("id"));
                request.setAttribute("question", Question.getById(request.getParameter("id")));
                request.setAttribute("action", "update_question");
                request.setAttribute("title", "Редактировать вопрос");
                request.setAttribute("edit_mode", "on");
                request.setAttribute("disabled", "disabled");
                break;
            case "update_question":
                Utils.print("Servlet.update_question", request.getParameterMap());
                try {
                    request.setAttribute("title", Integer.parseInt(request.getParameter("id")) < 0 ? "Вопрос добавлен" : "Вопрос отредактирован");
                    question = Question.saveQuestion(request.getParameter("id"), request.getParameterMap());
                    String newThemeId = request.getParameter("newThemeId");
                    if (newThemeId != null) {
                        question.linkThemes(new String[]{newThemeId});
                    }
                } catch (JDBCException ex) {
                    Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
                    url = "/db_error.jsp";
                    request.setAttribute("exception", ex);
                    break;
                }
                url = "/new_question.jsp";
                request.setAttribute("question", question);
                break;

            case "new_realm":
                url = "/edit_realm.jsp";
                Utils.print("Servlet.new_realm", request.getParameterMap());
                request.setAttribute("realm", Realm.getMock());
                request.setAttribute("action", "update_realm");
                request.setAttribute("title", "Редактировать область");
                break;
            case "edit_realm":
                url = "/edit_realm.jsp";
                Utils.print("Servlet.edit_realm", request.getParameterMap());
                Utils.print(request.getParameter("id"));
                request.setAttribute("realm", Realm.getById(request.getParameter("id")));
                request.setAttribute("action", "update_realm");
                request.setAttribute("title", "Редактировать область");
                break;
            case "update_realm":
                Realm realm;
                Utils.print("Servlet.update_realm", request.getParameterMap());
                try {
                    realm = Realm.saveRealm(request.getParameter("rid"), request.getParameterMap());
                } catch (JDBCException ex) {
                    Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
                    url = "/db_error.jsp";
                    request.setAttribute("exception", ex);
                    break;
                }
                url = "/saved_realm.jsp";
                request.setAttribute("realm", realm);
                request.setAttribute("title", "Область сохранена");
                break;

            case "new_theme":
                url = "/edit_theme.jsp";
                Utils.print("Servlet.new_theme", request.getParameterMap());
                request.setAttribute("theme", Theme.getMock());
                request.setAttribute("action", "update_theme");
                request.setAttribute("title", "Редактировать тему");
                break;
            case "edit_theme":
                url = "/edit_theme.jsp";
                Utils.print("Servlet.edit_theme", request.getParameterMap());
                Utils.print(request.getParameter("id"));
                request.setAttribute("theme", Theme.getById(request.getParameter("id")));
                request.setAttribute("action", "update_theme");
                request.setAttribute("title", "Редактировать тему");
                break;
            case "update_theme":
                Theme theme;
                Utils.print("Servlet.update_theme", request.getParameterMap());
                try {
                    theme = Theme.saveTheme(request.getParameter("tid"), request.getParameterMap());
                } catch (JDBCException ex) {
                    Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
                    url = "/db_error.jsp";
                    request.setAttribute("exception", ex);
                    break;
                }
                url = "/saved_theme.jsp";
                request.setAttribute("title", "Тема сохранена");
                request.setAttribute("theme", theme);
                break;

            case "link_themes":
                Utils.print("Servlet.link_themes", request.getParameterMap());
                Utils.print("Servlet.link_themes", request.getParameterMap().get("themes"));
                Question qq = Question.getById(request.getParameter("id"));
                try {
                    qq.linkThemes(request.getParameterMap().get("themes"));
                } catch (JDBCException ex) {
                    Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
                    url = "/db_error.jsp";
                    request.setAttribute("exception", ex);
                    break;
                }
                request.setAttribute("question", qq);
                request.setAttribute("title", "Добавлена связь с темами");
                url = "/new_question.jsp";
                break;

            case "new_image":
                url = "/upload_image.jsp";
                Utils.print("Servlet.new_image", request.getParameterMap());
                break;
            case "upload_image":
                url = "/view?info=images";
                Part filePart = request.getPart("file");
                String fileFullName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                InputStream fileContent = filePart.getInputStream();
                Utils.print("Servlet.upload_image", request.getParameterMap());
                Utils.print("Servlet.fileName", fileFullName);
                Utils.print("Servlet.fileContent", fileContent);
                Image image;
                if (!fileFullName.equals("")) { //пришел непустой файл
                    String[] fileNameParts = fileFullName.split("\\.");
                    String extension = fileNameParts[fileNameParts.length-1];
                    String[] name = new String[fileNameParts.length-1];
                    for (int i=0; i < fileNameParts.length-1 ; i++) {
                        name[i] = fileNameParts[i];
                    }
                    String fileName = String.join(".", name);
                    Map<String, Object> data = new HashMap<String, Object>();
                    data.put("filename", fileName);
                    data.put("extension", extension);
                    try {
                        image = Image.saveImage(-1, data);
                    } catch (JDBCException ex) {
                        Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
                        url = "/db_error.jsp";
                        request.setAttribute("exception", ex);
                        break;
                    }

                    int myByte = fileContent.read();
                    if (myByte != -1) {
                        String pathStr = this.getServletContext().getRealPath(File.separator) + getServletContext().getInitParameter("upload.location");
                        //Path path = Paths.get(pathStr);
                        File uploadsDir = new File(pathStr);
                        //if (Files.notExists(path)){
                        if (!uploadsDir.exists()) {
                            uploadsDir.mkdir();
                            // If you require it to make the entire directory path including parents,
                            // use uploadsDir.mkdirs(); here instead.
                        }
                        File file = new File(uploadsDir, "" + image.getId());
                        FileOutputStream fos = new FileOutputStream(file);
                        try {
                            while (myByte != -1) {
                                fos.write(myByte);
                                myByte = fileContent.read();
                            }
                        } catch (IOException e) {
                            image.delete();
                            throw new RuntimeException(e);
                            
                        } finally {
                            fos.close();
                        }
                        
                    }
                }
                break;

            case "export":
                Storage.init(); // реинициализация, чтобы выгрузка была строго из БД
                try {
                    if (Storage.getJdbcException() != null) {
                        throw Storage.getJdbcException();
                    }
                    StringBuilder sb = new StringBuilder();
                        sb.append("DROP TABLE Realm IF EXISTS;\r\n");
                        sb.append("CREATE TABLE Realm (id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, text VARCHAR(20), description VARCHAR(2000), number int);\r\n");
                        sb.append("DROP TABLE Theme IF EXISTS;\r\n");
                        sb.append("CREATE TABLE Theme (id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, realmId int, text VARCHAR(2000), number int);\r\n");
                        sb.append("DROP TABLE Rule IF EXISTS;\r\n");
                        sb.append("CREATE TABLE Rule (id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, themeId int, text VARCHAR(2000), number int);\r\n");
                        sb.append("DROP TABLE Question IF EXISTS;\r\n");
                        sb.append("CREATE TABLE Question (id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, realmId int, type int, text VARCHAR(2000), number int, ruleId int);\r\n");
                        sb.append("\tDROP TABLE Answer IF EXISTS;\r\n");
                        sb.append("\tCREATE TABLE Answer (id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, questionId int, correct boolean, text VARCHAR(2000), comment VARCHAR(2000));\r\n");
                        sb.append("DROP TABLE ThemeQuestion IF EXISTS;\r\n");
                        sb.append("CREATE TABLE ThemeQuestion (themeId int, questionId int, PRIMARY KEY(themeId, questionId));\r\n");
                        sb.append("DROP TABLE Image IF EXISTS;\r\n");
                        sb.append("CREATE TABLE Image (id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, filename VARCHAR(2000), extension VARCHAR(10));\r\n");
                        sb.append("DROP TABLE UserAnswer IF EXISTS;\r\n");
                        sb.append("CREATE TABLE UserAnswer (id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, questionId int, correct boolean);\r\n");
                        sb.append("DROP TABLE ThemeExam IF EXISTS;\r\n");
                        sb.append("CREATE TABLE ThemeExam (id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, themeId int, percentage DOUBLE, date TIMESTAMP);\r\n");
                    for (Realm r: Realm.getMap().values()) {
                        Map<String, Object> state = r.getState();
                        sb.append("INSERT INTO Realm (id");
                        for (String s: state.keySet()) {
                            sb.append("," + s);
                        }
                        sb.append(") OVERRIDING SYSTEM VALUE VALUES (" + r.getId());
                        for (Object o: state.values()) {
                            String ooo;
                            if (o instanceof String) {
                                o = ((String)o).replace("'","''");
                                ooo = "'" + o + "'";
                            } else if (o == null)
                                ooo = "NULL";
                            else
                                ooo=o.toString();
                            sb.append("," + ooo);
                        }
                        sb.append(");\r\n");

                    }

                    for (Theme t: Theme.getMap().values()) {
                        Map<String, Object> state = t.getState();
                        sb.append("INSERT INTO Theme (id");
                        for (String s: state.keySet()) {
                            sb.append("," + s);
                        }
                        sb.append(") OVERRIDING SYSTEM VALUE VALUES (" + t.getId());
                        for (Object o: state.values()) {
                            String ooo;
                            if (o instanceof String) {
                                o = ((String)o).replace("'","''");
                                ooo = "'" + o + "'";
                            } else if (o == null)
                                ooo = "NULL";
                            else
                                ooo=o.toString();
                            sb.append("," + ooo);
                        }
                        sb.append(");\r\n");

                    }

                    for (Rule r: Rule.getMap().values()) {
                        Map<String, Object> state = r.getState();
                        sb.append("INSERT INTO Rule (id");
                        for (String s: state.keySet()) {
                            sb.append("," + s);
                        }
                        sb.append(") OVERRIDING SYSTEM VALUE VALUES (" + r.getId());
                        for (Object o: state.values()) {
                            String ooo;
                            if (o instanceof String) {
                                o = ((String)o).replace("'","''");
                                ooo = "'" + o + "'";
                            } else if (o == null)
                                ooo = "NULL";
                            else
                                ooo=o.toString();
                            sb.append("," + ooo);
                        }
                        sb.append(");\r\n");

                    }

                    for (Question q: Question.getMap().values()) {
                        Map<String, Object> state = q.getState();
                        sb.append("INSERT INTO Question (id");
                        for (String s: state.keySet()) {
                            sb.append("," + s);
                        }
                        sb.append(") OVERRIDING SYSTEM VALUE VALUES (" + q.getId());
                        for (Object o: state.values()) {
                            String ooo;
                            if (o instanceof String) {
                                o = ((String)o).replace("'","''");
                                ooo = "'" + o + "'";
                            } else if (o == null)
                                ooo = "NULL";
                            else
                                ooo=o.toString();
                            sb.append("," + ooo);
                        }
                        sb.append(");\r\n");
                        
                        for (Answer a: q.getAnswerMap().values()) {
                            Map<String, Object> stateA = a.getState();
                            sb.append("\tINSERT INTO Answer (id");
                            for (String s: stateA.keySet()) {
                                sb.append("," + s);
                            }
                            sb.append(") OVERRIDING SYSTEM VALUE VALUES (" + a.getId());
                            for (Object o: stateA.values()) {
                                String ooo;
                                if (o instanceof String) {
                                    o = ((String)o).replace("'","''");
                                    ooo = "'" + o + "'";
                                } else if (o == null)
                                    ooo = "NULL";
                                else
                                    ooo=o.toString();
                                sb.append("," + ooo);
                            }
                            sb.append(");\r\n");
                            
                        }
                    }

                    for (Entity themeQuestion :  EduAccess.getThemeQuestionSet()) {
                        Map<String, Object> pk = themeQuestion.getPrimaryKey();
                        sb.append("INSERT INTO ThemeQuestion (");
                        boolean first = true;
                        for (String s: pk.keySet()) {
                            if (first) {
                                sb.append(s);
                                first = false;
                            } else {
                                sb.append("," + s);
                            }
                        }
                        sb.append(") VALUES (");
                        first = true;
                        for (String s: pk.keySet()) {
                            Object o = pk.get(s);
                            String ooo;
                            if (o instanceof String) {
                                o = ((String)o).replace("'","''");
                                ooo = "'" + o + "'";
                            } else if (o == null)
                                ooo = "NULL";
                            else
                                ooo=o.toString();
                            if (first) {
                                sb.append(ooo);
                                first = false;
                            } else {
                                sb.append("," + ooo);
                            }
                        }
                        sb.append(");\r\n");

                    }

                    for (Image i: Image.getMap().values()) {
                        Map<String, Object> state = i.getState();
                        sb.append("INSERT INTO Image (id");
                        for (String s: state.keySet()) {
                            sb.append("," + s);
                        }
                        sb.append(") OVERRIDING SYSTEM VALUE VALUES (" + i.getId());
                        for (Object o: state.values()) {
                            String ooo;
                            if (o instanceof String) {
                                o = ((String)o).replace("'","''");
                                ooo = "'" + o + "'";
                            } else if (o == null)
                                ooo = "NULL";
                            else
                                ooo=o.toString();
                            sb.append("," + ooo);
                        }
                        sb.append(");\r\n");

                    }

                    for (UserAnswer i: UserAnswer.getMap().values()) {
                        Map<String, Object> state = i.getState();
                        sb.append("INSERT INTO UserAnswer (id");
                        for (String s: state.keySet()) {
                            sb.append("," + s);
                        }
                        sb.append(") OVERRIDING SYSTEM VALUE VALUES (" + i.getId());
                        for (Object o: state.values()) {
                            String ooo;
                            if (o instanceof String) {
                                o = ((String)o).replace("'","''");
                                ooo = "'" + o + "'";
                            } else if (o == null)
                                ooo = "NULL";
                            else
                                ooo=o.toString();
                            sb.append("," + ooo);
                        }
                        sb.append(");\r\n");

                    }

                    for (ThemeExam i: ThemeExam.getMap().values()) {
                        Map<String, Object> state = i.getState();
                        sb.append("INSERT INTO ThemeExam (id");
                        for (String s: state.keySet()) {
                            sb.append("," + s);
                        }
                        sb.append(") OVERRIDING SYSTEM VALUE VALUES (" + i.getId());
                        for (Object o: state.values()) {
                            sb.append("," + JDBCUtils.getSQLLiteral(o));
                        }
                        sb.append(");\r\n");

                    }

                    Utils.print(sb);
                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "attachment; filename=\"questions.sql\"");
                    PrintWriter out = response.getWriter();
                    out.print(sb.toString());
                } catch (JDBCException ex) {
                    Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
                    url = "/db_error.jsp";
                    request.setAttribute("exception", ex);
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
        
        String action = request.getParameter("action");
        Set<String> allowedActions= new HashSet<String>();
        allowedActions.add("new_theme");
        allowedActions.add("new_realm");
        allowedActions.add("new_question");
        allowedActions.add("export");
        allowedActions.add("new_question2");
        allowedActions.add("new_image");
        allowedActions.add("upload_image");
        allowedActions.add("edit_realm");
        allowedActions.add("edit_theme");
        allowedActions.add("load_edit_form");
        if (allowedActions.contains(action))
            processRequest(request, response);
        else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("Недопустимые параметры");
        }
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
