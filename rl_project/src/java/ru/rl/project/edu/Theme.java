/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.edu;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import ru.rl.project.db.JDBCUtils;
import static ru.rl.project.edu.Realm.getMap;
import ru.rl.project.exception.JDBCException;
import ru.rl.project.util.Utils;

/**
 *
 * @author mithia
 */
public class Theme extends Entity implements ITreeElement {
    
    public static final Comparator<Theme> NUMBER_COMPARATOR = new NumberComparator();

    Theme(int id) {
        super("Theme", id);
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
        defaultState.put("realmId", -1);
        defaultState.put("text", "");
        defaultState.put("number", 0.0);
    }
    
    public String getText() { //вспомогательный геттер
        return this.getStr("text");
    }

    public double getNumber() { //вспомогательный геттер
        return this.getDbl("number");
    }

    public String getNumberStr() { //вспомогательный геттер
        return this.getDblStr("number");
    }


    public static Theme getMock() { //обертка для использования в jsp
        return new Theme(-100);
    }
    public static Theme getMock(String realmId) { //обертка для использования в jsp
        Theme theme = getMock();
        if (realmId != null && Realm.getById(realmId) != null) {
            Map<String, Object> state = new HashMap<String, Object>();
            state.put("realmId", Integer.parseInt(realmId));
            theme.setState(state);
        }
        return theme;
    }
    
    public static Map<Integer, Theme> getMap() {
        return Collections.unmodifiableMap(getStorage().getThemeMap());
    }
    
    public Realm getRealm() {
        return Realm.getMap().get(this.getInt("realmId"));
    }

    public String toString() {
        return "Тема {" + getId() + "} {" + getRealm().getStr("text") + "}";
    }

    public String getTitle() {
        return "" + getDblStr("number") + " " + getText();
    }

    public static Theme getById(Object id){
        if (id instanceof String)
            return getMap().get(Integer.parseInt((String)id));
        else
            return getMap().get((Integer)id);
    }

    public static Theme getById(int id){
        return Theme.getById(new Integer(id));
    }
    
    public static Theme saveTheme(String id, Map<String, ?> data) throws JDBCException {
        return saveTheme(Integer.parseInt(id), data);
    }
    public static Theme saveTheme(int id, Map<String, ?> data) throws JDBCException {
        Theme theme = getMap().get(id);
        Utils.print("saveTheme", data);
        if (theme == null) 
            theme = new Theme(-1);
        if (data != null && !data.isEmpty()) {
            if (data.get(data.keySet().toArray()[0]).getClass().isArray()) { //параметры пришли с фронта
                data = Utils.translateWebData( (Map<String, String[]>)data );
            }
            Utils.print("saveThemeAfterTranslation", data);
            Realm oldRealm = theme.getRealm();
            theme.setState(data);
            Realm realm = theme.getRealm();
            if (realm != null && oldRealm != null && realm.getId() != oldRealm.getId()) {
                for (int questionId: theme.getQuestionMap().keySet()) {
                    new ThemeQuestion(theme.getId(), questionId).delete();
                }
                getStorage().unbind(theme, oldRealm);
            }

        }
        Map<String, Object> pk = JDBCUtils.saveEntity(theme);
        if (pk != null) { // удалось записать объект в БД
            theme.setPrimaryKey(pk);
            getStorage().register(theme);
            Utils.print("Theme pk: ", pk);
        } else {
            return null;
        }
            
        return theme;
    }
    
    public Map<Integer, Question> getQuestionMap() {
        return Collections.unmodifiableMap(getStorage().getQuestionMap(this));
    }
    
    public List<Question> getValidQuestions() {
        ArrayList<Question> validQuestions = new ArrayList<Question>();
        for (Question q: getQuestionMap().values()) {
            if (q.isValid())
                validQuestions.add(q);
        }
        return validQuestions;
    }
    
    public int getInvalidQuestionQty() {
        return getQuestionMap().size() - getValidQuestions().size();
    }
    
    public Map<Integer, ThemeExam> getThemeExamMap() {
        return Collections.unmodifiableMap(getStorage().getThemeExamMap(this));
    }

    public int getThemeExamsQty() {
        return getThemeExamMap().size();
    }

    public String getExamsTableHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table width=\"100%\" cellspacing=\"2\">\n");
        sb.append("<tr><td>Дата и время проверки</td><td>%</td></tr>");
        List<ThemeExam> examList = new ArrayList<ThemeExam>(getThemeExamMap().values());
        Collections.sort(examList, ThemeExam.DATE_COMPARATOR);
        for (int i=0; i<examList.size(); i++) {
            ThemeExam exam = examList.get(i);
            sb.append("<tr bgcolor=" + (i%2 == 0 ? "white" : "#D7DDDD") + ">\n");
            sb.append("<td>\n");
            sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(exam.getDate()));
            sb.append("</td>\n");
            sb.append("<td>\n");
            sb.append(Utils.round(exam.getPercentage(), 2));
            sb.append("</td>\n");
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        return sb.toString();
    }

    public String getQuestionsHTMLLink(String linkText) {
        return "<a href=view?info=questions&themeId=" + this.getId() + ">" + linkText + "</a>";
    }
    public String getQuestionsHTMLLink() {
        return getQuestionsHTMLLink("Карточки");
    }
    public int getQuestionsQty() {
        return getQuestionMap().size();
    }

    public String getThemeExamsHTML() {
        List<ThemeExam> exams = new ArrayList<ThemeExam>(getThemeExamMap().values());
        Collections.sort(exams,  ThemeExam.DATE_COMPARATOR);
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");
        for (ThemeExam exam : exams) {
            sb.append("<tr>\n");
            sb.append("<td>\n");
//            sb.append("Дата и время проверки: <b>" + DateFormat.getDateInstance(DateFormat.FULL).format(exam.getDate()) + "</b>");
            sb.append("Дата и время проверки: <b>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(exam.getDate()) + "</b>");
            sb.append("</td>\n");
            sb.append("<td>\n");
            sb.append("Процент корректных ответов: <b>" + Utils.round(exam.getPercentage(), 2) + "</b>");
            sb.append("</td>\n");
            sb.append("</tr>\n");
            
        }
        sb.append("</table>\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Theme) && 
                ((Theme)obj).getPrimaryKey().equals(this.getPrimaryKey()) && 
                ((Theme)obj).getQuestionMap().keySet().equals(this.getQuestionMap().keySet());
    }
    
    public boolean isExaminable() {
        return !getValidQuestions().isEmpty();
    }

    @Override
    public List<ITreeElement> getTreeElements() {
        return new ArrayList<ITreeElement>(getQuestionMap().values());
    }

    @Override
    public TreeSign getTreeSign() {
        treeSign.setName("Тема <b>" + this.getTitle() + "</b>");
        treeSign.setId(getTableName() + "_" + getId());
        treeSign.setTableBgcolor("#2DA935");
        treeSign.setTdBgcolor("#B4ECB7");
        treeSign.setEditLink("controller?action=edit_theme&id=" + getId());
        treeSign.setProfileLink(getProfileURL());
        
        return treeSign;
    }
    
    
    private static class NumberComparator implements Comparator<Theme> {
        @Override
        public int compare(Theme o1, Theme o2) {
            return new Double(o1.getNumber()).compareTo(new Double(o2.getNumber()));
        }
    }
}
