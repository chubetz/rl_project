/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.edu;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import ru.rl.project.db.JDBCUtils;
import static ru.rl.project.edu.Entity.getStorage;
import ru.rl.project.exception.JDBCException;
import ru.rl.project.util.Utils;

/**
 *
 * @author mithia
 */
public class ThemeExam extends Entity {
    
    ThemeExam(int id) {
        super("ThemeExam", id);
    }

    private static Map<String, Object> defaultState, defaultPrimaryKey;
    
    private static class DateComparator implements Comparator<ThemeExam> {

        @Override
        public int compare(ThemeExam o1, ThemeExam o2) {
            return o1.getDate().after(o2.getDate())  ? -1 : (o2.getDate().after(o1.getDate()) ? 1 : 0);
        }
        
    }
    
    public static final Comparator<ThemeExam> DATE_COMPARATOR = new DateComparator();
    
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
        defaultState.put("percentage", 0.0);
        defaultState.put("date", new Date(0));
    }

    @Override
    public boolean isPkAuto() {
        return true;
    }

    public static Map<Integer, ThemeExam> getMap() {
        return Collections.unmodifiableMap(getStorage().getThemeExamMap());
    }

    public static ThemeExam saveThemeExam(String id, Map<String, ?> data) throws JDBCException {
        return saveThemeExam(Integer.parseInt(id), data);
    }
    public static ThemeExam saveThemeExam(int id, Map<String, ?> data) throws JDBCException {
        ThemeExam themeExam = getMap().get(id);
        Utils.print("saveThemeExam", data);
        if (themeExam == null) 
            themeExam = new ThemeExam(-1);
        if (data != null && !data.isEmpty()) {
            if (data.get(data.keySet().toArray()[0]).getClass().isArray()) { //параметры пришли с фронта
                data = Utils.translateWebData( (Map<String, String[]>)data );
            }
            Utils.print("saveThemeExamAfterTranslation", data);
            themeExam.setState(data);

        }
        Map<String, Object> pk = JDBCUtils.saveEntity(themeExam);
        if (pk != null) { // удалось записать объект в БД
            themeExam.setPrimaryKey(pk);
            getStorage().register(themeExam);
            Utils.print("ThemeExam pk: ", pk);
        } else {
            return null;
        }
            
        return themeExam;
    }
    
    public Date getDate() {
        return getDte("date");
    }
    public Double getPercentage() {
        return getDbl("percentage");
    }

}
