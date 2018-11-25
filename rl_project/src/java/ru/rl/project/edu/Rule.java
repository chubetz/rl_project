/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.edu;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import ru.rl.project.db.JDBCUtils;
import static ru.rl.project.edu.Entity.getStorage;
import static ru.rl.project.edu.Realm.getMap;
import static ru.rl.project.edu.Theme.getMock;
import static ru.rl.project.edu.ThemeExam.getMap;
import ru.rl.project.exception.JDBCException;
import ru.rl.project.util.Utils;

/**
 *
 * @author mithia
 */
public class Rule extends Entity implements ITreeElement{

    public static final Comparator<Rule> NUMBER_COMPARATOR = new NumberComparator();
    
    Rule(int id) {
        super("Rule", id);
    }

    private static Map<String, Object> defaultState, defaultPrimaryKey;

    @Override
    protected Map<String, Object> getDefaultState() {
        return defaultState;        
    }

    @Override
    protected Map<String, Object> getDefaultPrimaryKey() {
        return defaultPrimaryKey;
    }

    static {
        defaultPrimaryKey = new LinkedHashMap<String, Object>();
        defaultPrimaryKey.put("id", -1);

        defaultState = new LinkedHashMap<String, Object>();
        defaultState.put("themeId", -1);
        defaultState.put("text", "");
        defaultState.put("number", -1);
    }

    @Override
    public boolean isPkAuto() {
        return true;
    }
    
    public static Map<Integer, Rule> getMap() {
        return Collections.unmodifiableMap(getStorage().getRuleMap());
    }

    public static Rule saveRule(String id, Map<String, ?> data) throws JDBCException {
        return saveRule(Integer.parseInt(id), data);
    }
    public static Rule saveRule(int id, Map<String, ?> data) throws JDBCException {
        Rule rule = getMap().get(id);
        Utils.print("saveRule", data);
        if (rule == null) 
            rule = new Rule(-1);
        if (data != null && !data.isEmpty()) {
            if (data.get(data.keySet().toArray()[0]).getClass().isArray()) { //параметры пришли с фронта
                data = Utils.translateWebData( (Map<String, String[]>)data );
            }
            Utils.print("saveRuleAfterTranslation", data);
            rule.setState(data);

        }
        Map<String, Object> pk = JDBCUtils.saveEntity(rule);
        if (pk != null) { // удалось записать объект в БД
            rule.setPrimaryKey(pk);
            getStorage().register(rule);
            Utils.print("Rule pk: ", pk);
        } else {
            return null;
        }
            
        return rule;
    }

    public int getNumber() { //вспомогательный геттер
        return this.getInt("number");
    }

    public String getText() { //вспомогательный геттер
        return this.getStr("text");
    }

    public Theme getTheme() {
        return Theme.getMap().get(this.getInt("themeId"));
    }

    public Map<Integer, Question> getQuestionMap() {
        return Collections.unmodifiableMap(getStorage().getQuestionMap(this));
    }

    public String getQuestionsHTMLLink(String linkText) {
        return "<a href=view?info=questions&ruleId=" + this.getId() + ">" + linkText + "</a>";
    }
    public String getQuestionsHTMLLink() {
        return getQuestionsHTMLLink("Задания");
    }
    public int getQuestionsQty() {
        return getQuestionMap().size();
    }

    private static Rule mock = new Rule(-100);
    public static Rule getMock() { //обертка для использования в jsp
        return mock;
    }
    public static Rule getMock(String themeId) { //обертка для использования в jsp
        Rule rule = getMock();
        if (themeId != null && Theme.getById(themeId) != null) {
            Map<String, Object> state = new HashMap<String, Object>();
            state.put("themeId", Integer.parseInt(themeId));
            rule.setState(state);
        }
        return rule;
    }

    public static Rule getById(Object id){
        if (id instanceof String)
            return getMap().get(Integer.parseInt((String)id));
        else
            return getMap().get((Integer)id);
    }

    public static Rule getById(int id){
        return getById(new Integer(id));
    }

    
    public TreeSign getTreeSign() {
        treeSign.setName("Правило <b>" + this.getNumber() + "</b>");
        treeSign.setId(getTableName() + "_" + getId());
        treeSign.setTableBgcolor("#B62528");
        treeSign.setTdBgcolor("#F7CFCF");
        //treeSign.setEditLink("controller?action=edit_theme&id=" + getId());
        //treeSign.setProfileLink(getProfileURL());
        
        return treeSign;
    }
    public String getThemesHTML() {
        StringBuilder sb = new StringBuilder();
        int themeId = this.getInt("themeId");
//        boolean dropDownDisabled = this.getId() < 0 && themeId >= 0;
        
        sb.append("<select name=\"themeId\">\n");
        for (Map.Entry<Integer, Theme> entry: Theme.getMap().entrySet()) {
            sb.append("<option value=\"" + entry.getKey() + "\"");
            if (themeId == entry.getKey())
                sb.append(" selected");
            sb.append(">" + entry.getValue().getRealm().getDescription() + "::" + entry.getValue().getText() + "</option>\n");
        }
        sb.append("</select>\n");
//        if (dropDownDisabled) { //нужно продублировать выбранное значение, т.к. select disabled не отправляется в форму
//            sb.append("<input type=\"hidden\" name=\"realmId\" value=\"" + realmId + "\" />");
//        }
            
        return sb.toString();
    }
    private static class NumberComparator implements Comparator<Rule> {
        @Override
        public int compare(Rule o1, Rule o2) {
            return new Integer(o1.getNumber()).compareTo(new Integer(o2.getNumber()));
        }
    }

    @Override
    public List<ITreeElement> getTreeElements() {
        return new ArrayList<ITreeElement>(getQuestionMap().values());
    }

}
