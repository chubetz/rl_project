/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.edu;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author d.gorshenin
 */
public class Learn {
    
    private ITreeElement mainNode;
    
    private List<ITreeElement> list;
    private Iterator<ITreeElement> iterator;
    
    private LearnElement current;
    
    private boolean isCurrentLast;

    private String title;
    
    public Learn(ITreeElement node) {
        this.mainNode = node;
        if (node instanceof Realm) {
            Realm realm = (Realm)node;
            this.title = "Раздел <b>" + realm.getDescription() + "</b>";
        }
        if (node instanceof Theme) {
            Theme theme = (Theme)node;
            this.title = "Тема <b>" + theme.getText() + "</b>";
        }
        list = node.getAllChildren();
        iterator = list.iterator();
        current = new LearnElement(iterator.next(), this);
    }
    
    public LearnElement next() {
        current = new LearnElement(iterator.next(), this);
        if (!iterator.hasNext()) {
            isCurrentLast = true;
        }
        return current;
    }
    
    public LearnElement getCurrent() {
        return this.current;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public ITreeElement getMainNode() {
        return this.mainNode;
    }
    
    public boolean isCurrentLast() {
        return this.isCurrentLast;
    }
}
