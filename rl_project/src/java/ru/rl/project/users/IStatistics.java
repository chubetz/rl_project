/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.users;

/**
 *
 * @author mithia
 */
public interface IStatistics {
    
    int getRealmsQty();
    int getThemesQty();
    int getQuestionsQty();
    int getQuestionsNotaBeneQty();
    int getQuestionsCommonQty();
    int getQuestionsTestQty();
    int getExamsQty();
    double getAverageScore();
    
}
