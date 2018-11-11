/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.util;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author d.gorshenin
 */
public class Utils {
    
    public static String mapToString(Map<?,?> map) {
        StringBuilder sb = new StringBuilder("[");
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            sb.append(entry.getKey().toString() + " => " + convertToString(entry.getValue()) + ", ");
        }
        sb.append("]");
        return sb.toString();
    }
    
    public static String collectionToString(Collection<?> list) {
        StringBuilder sb = new StringBuilder("[");
        for (Object entry : list) {
            sb.append(convertToString(entry) + ", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static String arrayToString(Object unknownArray) {
        if (!unknownArray.getClass().isArray())
            throw new RuntimeException("Аргумент не является массивом!");
        List list = null;
        if (unknownArray instanceof byte[]) {
            list = new ArrayList<Byte>();
            for (byte b: (byte[])unknownArray) {
                list.add(b);
            }
        } else if (unknownArray instanceof int[]) {
            list = new ArrayList<Integer>();
            for (int i: (int[])unknownArray) {
                list.add(i);
            }
        } else if (unknownArray instanceof long[]) {
            ArrayList<Long> myList = new ArrayList<Long>();
            for (long l: (long[])unknownArray) {
                myList.add(l);
            }
        } else if (unknownArray instanceof float[]) {
            list = new ArrayList<Float>();
            for (float f: (float[])unknownArray) {
                list.add(f);
            }
        } else if (unknownArray instanceof double[]) {
            list = new ArrayList<Double>();
            for (double d: (double[])unknownArray) {
                list.add(d);
            }
        } else if (unknownArray instanceof char[]) {
            list = new ArrayList<Character>();
            for (char c: (char[])unknownArray) {
                list.add(c);
            }
        } else if (unknownArray instanceof short[]) {
            list = new ArrayList<Short>();
            for (short s: (short[])unknownArray) {
                list.add(s);
            }
        } else if (unknownArray instanceof boolean[]) {
            list = new ArrayList<Boolean>();
            for (boolean b: (boolean[])unknownArray) {
                list.add(b);
            }
        } else if (unknownArray instanceof Object[])
            list = Arrays.asList((Object[])unknownArray);
            
        return collectionToString(list);
    }

    public static String convertToString(Object obj) {
        String str = null;
        if (obj == null) {
            str = "null";
        } else if (obj instanceof Map) {
            str = mapToString((Map)obj);
        } else if (obj instanceof Collection) {
            str = collectionToString((Collection)obj);
        } else if (obj.getClass().isArray()) {
            str = arrayToString(obj);
        } else {
            str = obj.toString();
        }
        return str;
    }
    
    public static void print(Object obj) {
        System.out.println(convertToString(obj));
    }
    
    public static void print(String label, Object obj) {
        System.out.println(label + ": " + convertToString(obj));
    }

    public static Map<String, Object> translateWebData(Map<String, String[]> data) {
        Map<String, Object> tmp = new LinkedHashMap<>();
        for (String key: data.keySet()) {
            String[] current = data.get(key);
            if (current.length == 1) {
                tmp.put(key, current[0]);
            } else {
                tmp.put(key, current);
            }
        }
        return tmp;
    }

    public static boolean canBeCastedAgainst(Object obj, Class<?> clazz) {
        try {
            clazz.cast(obj);
        } catch (ClassCastException cce) {
            return false;
        }
        return true;
    }
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
