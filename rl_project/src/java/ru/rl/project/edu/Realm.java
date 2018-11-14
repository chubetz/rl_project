/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.edu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import ru.rl.project.db.JDBCUtils;
import ru.rl.project.exception.JDBCException;
import ru.rl.project.util.Utils;

/**
 *
 * @author d.gorshenin
 */
public class Realm extends Entity implements ITreeElement {
    
    
    Realm(int id) {
        super("Realm", id);
    }
    
    

    private static Map<String, Object> defaultState, defaultPrimaryKey;

    public boolean isPkAuto() {
        return true;
    }

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
        defaultState.put("text", "");
        defaultState.put("description", "");
        defaultState.put("number", -1);
    }
    
    public String getText() { //вспомогательный геттер
        return this.getStr("text");
    }
    
    public int getNumber() { //вспомогательный геттер
        return this.getInt("number");
    }

    public String getDescription() { //вспомогательный геттер
        return this.getStr("description");
    }

    private static Realm mock = new Realm(-100);
    public static Realm getMock() { //обертка для использования в jsp
        return mock;
    }

    public static Map<Integer, Realm> getMap() {
        return Collections.unmodifiableMap(getStorage().getRealmMap());
    }

    public String toString() {
        return "Область {" + getId() + "}";
    }

    public static Realm getById(Object id){
        if (id instanceof String)
            return getMap().get(Integer.parseInt((String)id));
        else
            return getMap().get((Integer)id);
    }

    public static Realm getById(int id){
        return Realm.getById(new Integer(id));
    }
    
    public static Realm saveRealm(String id, Map<String, ?> data) throws JDBCException {
        return saveRealm(Integer.parseInt(id), data);
    }
    public static Realm saveRealm(int id, Map<String, ?> data) throws JDBCException {
        Realm realm = getMap().get(id);
        Utils.print("saveRealm", data);
        if (realm == null) {
            realm = new Realm(-1);
        }
        if (data != null && !data.isEmpty()) {
            if (data.get(data.keySet().toArray()[0]).getClass().isArray()) { //параметры пришли с фронта
                data = Utils.translateWebData( (Map<String, String[]>)data );
            }
            Utils.print("saveRealmAfterTranslation", data);
            realm.setState(data);

        }
        Map<String, Object> pk = JDBCUtils.saveEntity(realm);
        if (pk != null) { // удалось записать объект в БД
            realm.setPrimaryKey(pk);
            getStorage().register(realm);
            Utils.print("Realm pk: ", pk);
        } else {
            return null;
        }
            
        return realm;
    }
    

    public Map<Integer, Theme> getThemeMap() {
        return Collections.unmodifiableMap(getStorage().getThemeMap(this));
    }

    public Map<Integer, Question> getQuestionMap() {
        //return Collections.unmodifiableMap(getStorage().getQuestionMap(this));
        Map<Integer, Question> map = new HashMap<Integer, Question>();
        for (Theme t: getThemeMap().values()) {
            for (Rule r: t.getRuleMap().values()) {
                map.putAll(r.getQuestionMap());
            }
        }
        
        return map;
    }

    public String getQuestionsHTMLLink(String linkText) {
        return "<a href=view?info=questions&realmId=" + this.getId() + ">" + linkText + "</a>";
    }
    public String getQuestionsHTMLLink() {
        return getQuestionsHTMLLink("Карточки");
    }
    public int getQuestionsQty() {
        return getQuestionMap().size();
    }

    public String getThemesHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<ul>\r\n");
        List<Theme> themeList = new ArrayList<Theme>(getThemeMap().values());
        Collections.sort(themeList, Theme.NUMBER_COMPARATOR);
        for (Theme theme: themeList) {
            sb.append("\t<li>" + theme.getProfileLink(theme.getNumberStr() + " " + theme.getText()) + "\r\n");
        }
        sb.append("</ul>");
        return sb.toString();
    }
    
    public String getThemesTableHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table width=\"100%\" cellspacing=\"2\">\n");
        List<Theme> themeList = new ArrayList<Theme>(getThemeMap().values());
        Collections.sort(themeList, Theme.NUMBER_COMPARATOR);
        for (int i=0; i<themeList.size(); i++) {
            Theme theme = themeList.get(i);
            sb.append("<tr bgcolor=" + (i%2 == 0 ? "white" : "#D7DDDD") + ">\n");
            sb.append("<td>\n");
            sb.append(theme.getNumberStr());
            sb.append("</td>\n");
            sb.append("<td>\n");
            sb.append(theme.getProfileLink(theme.getText()));
            sb.append("</td>\n");
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        return sb.toString();
    }

    public String getThemesForSelectHTML() {
        List<Theme> themes = new ArrayList<Theme>(getThemeMap().values());
        StringBuilder sb = new StringBuilder();
        sb.append("<select name=\"themes\" size=\"" + themes.size() + "\" multiple>\r\n");
        for (Theme theme: themes) {
            sb.append("\t<option value=\"" + theme.getId() + "\">" + theme.roundToIntStr(theme.getDbl("number")) + " " + theme.getStr("text") + "</option>\n");
        }
        sb.append("</select>");
        return sb.toString();
    }

    public int getThemesQty() {
        return getThemeMap().size();
    }
    
    @Override
    public List<ITreeElement> getTreeElements() {
        List<ITreeElement> list = new ArrayList<ITreeElement>(getQuestionMap().values());
        for (Theme th: getThemeMap().values()) {
            list.removeAll(th.getQuestionMap().values());
            list.add(0, th);
        }
        
        return list;
    }
    

    @Override
    public TreeSign getTreeSign() {
        treeSign.setName("Предметная область <b>" + this.getDescription() + "</b>");
        treeSign.setId(getTableName() + "_" + getId());
        treeSign.setTableBgcolor("#2DC7E9");
        treeSign.setTdBgcolor("#C0EFF9");
        treeSign.setEditLink("controller?action=edit_realm&id=" + getId());
        
        return treeSign;
        
    }
    

}
