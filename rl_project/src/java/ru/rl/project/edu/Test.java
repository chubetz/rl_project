/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.edu;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 *
 * @author mithia
 */
public class Test {
    private ITreeElement node; //раздел/тема, который проверяется
    
    private String title; 

    private TestElement current;
    
    private List<TestElement> testElements = new ArrayList<TestElement>();
    
    private Iterator<TestElement> iterator;
    
    public Test(ITreeElement node) {
        this.node = node;
        
        Map<Integer, List<Question>> qMap = new TreeMap<>();
        if (node instanceof Realm) {
            Realm realm = (Realm)node;
            this.title = "Раздел <b>" + realm.getDescription() + "</b>";
            for (Question q: realm.getQuestionMap().values()) {
                int numOnExam = q.getNumberOnExam();
                if (numOnExam > 0) {
                    List<Question> list = qMap.get(numOnExam);
                    if (list == null) {
                        list = new ArrayList<Question>();
                        qMap.put(numOnExam, list);
                    }
                    list.add(q);
                }
            }
            
            //формируем список вопросов к экзамену
            for (Map.Entry<Integer, List<Question>> entry: qMap.entrySet()) {
                Collections.shuffle(entry.getValue());
                testElements.add(new TestElement(entry.getValue().get(0).generateQuestion(Question.TEST_MODE), this));
            }
            
            this.iterator = testElements.iterator();
            next();
        }
    }
    
    public void next() {
        this.current = iterator.hasNext() ? iterator.next() : null;
    }
    
    public TestElement getCurrent() {
        return current;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public ITreeElement getNode() {
        return this.node;
    }
    
    public int getTotalQuestionsQty() {
        return this.testElements.size();
    }
    
    public int getCorrectAnswerQty() {
        int count = 0;
        for (TestElement te: this.testElements) {
            if (te.isUserAnsweredRight())
                count++;
        }
        return count;
    }
    
    public double getCorrectPercent() {
        try {
            BigDecimal prc = new BigDecimal(getCorrectAnswerQty()/(getTotalQuestionsQty()/100f));
            prc = prc.setScale(0, RoundingMode.HALF_UP);
            return prc.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    public String getCorrectPercentStr() {
        return "" + (int)getCorrectPercent();

    }
    
    public String getSchoolMark() {
        if (getCorrectPercent() == 0)
            return "0";
        else if (getCorrectPercent() < 10)
            return "0+";
        else if (getCorrectPercent() < 20)
            return "1-";
        else if (getCorrectPercent() == 20)
            return "1";
        else if (getCorrectPercent() < 30)
            return "1+";
        else if (getCorrectPercent() < 40)
            return "2-";
        else if (getCorrectPercent() == 40)
            return "2";
        else if (getCorrectPercent() < 50)
            return "2+";
        else if (getCorrectPercent() < 60)
            return "3-";
        else if (getCorrectPercent() == 60)
            return "3";
        else if (getCorrectPercent() < 70)
            return "3+";
        else if (getCorrectPercent() < 80)
            return "4-";
        else if (getCorrectPercent() == 80)
            return "4";
        else if (getCorrectPercent() < 90)
            return "4+";
        else if (getCorrectPercent() < 100)
            return "5-";
        else //if (getCorrectPercent() == 100)
            return "5";
    }
    
    public List<TestElement> getElements() {
        return this.testElements;
    }
}
