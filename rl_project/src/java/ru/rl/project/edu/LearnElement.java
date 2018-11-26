/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.edu;

import ru.rl.project.util.Constants;

/**
 *
 * @author d.gorshenin
 */
public class LearnElement {
    
    private Realm realm;
    private Theme theme;
    private Rule rule;
    private Question question;
    
    private String title, subtitle;
    private String form = "";
    private String titleBgcolor = "", subtitleBgcolor = "";
    
    private Learn parentLearn;
   
    
    public LearnElement(ITreeElement node, Learn parentLearn) {
        if (node instanceof Realm) {
            this.realm = (Realm)node;
            this.title = "Раздел <b>" + realm.getDescription() + "</b>";
        }
        if (node instanceof Question) {
            this.question = (Question)node;
            this.title = "Тема <b>" + this.question.getRule().getTheme().getText() + "</b>";
            this.titleBgcolor = Constants.MAIN_BGCOLOR;
            this.subtitleBgcolor = Constants.MAIN_BGCOLOR;
            StringBuilder sb = new StringBuilder();
            sb.append("Правило для изучения");
            sb.append("<p>");
            sb.append("<b>" + this.question.getRule().getNumber() + "</b>. " + this.question.getRule().getText());
            this.subtitle = sb.toString();
        }
        if (node instanceof Rule) {
            this.rule = (Rule)node;
            this.title = "Тема <b>" + this.rule.getTheme().getText() + "</b>";
            this.titleBgcolor = Constants.MAIN_BGCOLOR;
            StringBuilder sb = new StringBuilder();
            sb.append("Правило для изучения");
            sb.append("<p>");
            sb.append("<b>" + this.rule.getNumber() + "</b>. " + this.rule.getText());
            this.subtitle = sb.toString();
            this.form = "                            <form name=\"start\" action=\"learn\" method=\"POST\">\n" +
"                                <input type=\"hidden\" name=\"action\" value=\"learn\"/>\n" +
"                                <input type=\"hidden\" name=\"nodeId\" value=\"" + parentLearn.getMainNode().getTreeSign().getId() + "\"/>\n" +
"                                <input type=\"hidden\" name=\"doNext\" value=\"true\"/>\n" +
"                                <input type=\"Submit\" value=\"Продолжить\"/>\n" +
"                            </form>\n" +
"";
        }
        if (node instanceof Theme) {
            this.theme = (Theme)node;
            this.title = "Тема <b>" + theme.getText() + "</b>";
            this.form = "                            <form name=\"start\" action=\"learn\" method=\"POST\">\n" +
"                                <input type=\"hidden\" name=\"action\" value=\"learn\"/>\n" +
"                                <input type=\"hidden\" name=\"nodeId\" value=\"" + parentLearn.getMainNode().getTreeSign().getId() + "\"/>\n" +
"                                <input type=\"hidden\" name=\"doNext\" value=\"true\"/>\n" +
"                                <input type=\"Submit\" value=\"Продолжить\"/>\n" +
"                            </form>\n" +
"";
        }
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getTitleBgcolor() {
        return this.titleBgcolor;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public String getSubtitleBgcolor() {
        return this.subtitleBgcolor;
    }

    public String getForm() {
        return this.form;
    }
    
    
}
