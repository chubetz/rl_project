/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.users;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ru.rl.project.edu.Exam;
import ru.rl.project.edu.ITreeElement;
import ru.rl.project.edu.Learn;
import ru.rl.project.edu.Storage;
import ru.rl.project.edu.Test;
import ru.rl.project.edu.Theme;
import ru.rl.project.exception.ExamException;
import ru.rl.project.exception.JDBCException;

/**
 *
 * @author d.gorshenin
 */
public class State {
    
    private Map<Theme, Exam> themeExams = new ConcurrentHashMap<Theme, Exam>();
    private Map<ITreeElement, Learn> nodeLearns = new ConcurrentHashMap<ITreeElement, Learn>();
    private Map<ITreeElement, Test> nodeTests = new ConcurrentHashMap<ITreeElement, Test>();
    private User user;
    
    public Exam getExam(Theme theme) {
        Exam exam = themeExams.get(theme);
        if (exam == null) {
            exam = new Exam(theme);
            themeExams.put(theme, exam);
            exam.next();
        }   
        return exam;

    }
    
    public Learn getLearn(ITreeElement node) {
        Learn learn = nodeLearns.get(node);
        if (learn == null) {
            learn = new Learn(node);
            nodeLearns.put(node, learn);
        }   
        return learn;

    }

    public Test getTest(ITreeElement node) {
        Test test = nodeTests.get(node);
        if (test == null) {
            test = new Test(node);
            nodeTests.put(node, test);
        }   
        return test;

    }

    public boolean hasLearn(ITreeElement node) {
        Learn learn = nodeLearns.get(node);
        return learn != null;

    }

    public boolean hasTest(ITreeElement node) {
        Test test = nodeTests.get(node);
        return test != null;

    }

    public Exam stopExam(Theme theme) throws JDBCException {
        Exam exam = themeExams.get(theme);
        if (exam != null) {
            exam.saveStatistics();
        }   
        return themeExams.remove(theme);
    }

    public void stopLearn(ITreeElement node) {
        nodeLearns.remove(node);
    }

    public void stopTest(ITreeElement node) {
        nodeTests.remove(node);
    }

    public Exam cancelExam(Theme theme) {
        Exam exam = themeExams.get(theme);
        if (exam != null) {
            
        }   
        return themeExams.remove(theme);
    }
    
}
