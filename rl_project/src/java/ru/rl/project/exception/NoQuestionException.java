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
public class NoQuestionException extends Exception{
    
    public NoQuestionException() {
        
    }
    
    public NoQuestionException(String info) {
        super(info);
    }
    
}
