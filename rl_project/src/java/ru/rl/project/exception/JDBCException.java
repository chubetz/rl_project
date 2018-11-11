/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.exception;

import org.apache.catalina.tribes.util.Arrays;

/**
 *
 * @author mithia
 */
public class JDBCException extends Exception {
    
    private Exception orig;
    private String request;
    private String addInfo;
    
    public JDBCException(Exception orig, String request, String addInfo) {
        super();
        this.orig = orig;
        this.request = request;
        this.addInfo = addInfo;
    }
    
    public Exception getException() {
        return this.orig;
    }
    
    public String getExceptionInfo() {
        return Arrays.toString(this.orig.getStackTrace());
    }
    
    public String getRequest() {
        return this.request;
    }

    public String getExceptionInfoHTML() {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement ste: this.orig.getStackTrace()) {
            sb.append(ste.toString());
            sb.append("<br>");
        }
        return sb.toString();
    }

    public String toString() {
        return "Ошибка JDBC: " + orig.toString();
    }
    
}
