/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.exception;

/**
 *
 * @author d.gorshenin
 */
public class ExceptionViewer {
    
    private Exception exception;
    
    public ExceptionViewer(Exception e) {
        exception = e;
    }
    
    public String getStackTrace() {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement ste: this.exception.getStackTrace()) {
            sb.append(ste.toString());
            sb.append("<br>");
        }
        return sb.toString();
    }
    
    public String getMessage() {
        return exception.toString();
    }
    
}
