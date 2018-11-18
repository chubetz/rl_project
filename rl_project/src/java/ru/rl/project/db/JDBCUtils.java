/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.rl.project.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.rl.project.controller.MainServlet;
import ru.rl.project.edu.DBEntity;
import ru.rl.project.edu.Entity;
import ru.rl.project.exception.JDBCException;
import ru.rl.project.util.Utils;

/**
 *
 * @author d.gorshenin
 */
public class JDBCUtils {
    
    static {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    static SimpleDateFormat hsqlDateLiteralFormat = new SimpleDateFormat("'TIMESTAMP' ''yyyy-MM-dd HH:mm:ss''");
    
    public static Connection getHSQLConnection(String user, String password) {
        System.out.println("Connecting to database...");
        try {            
            return DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9002/test2",user,password);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    public static Connection getHSQLConnection() {
        return getHSQLConnection("SA", "");
    }
    
    private static void fillEntityFromRecordSet(LoadedEntity entity, ResultSet rs, DBEntity stateSource) throws SQLException {
        List<Map<String, Object>> stateMaps = new ArrayList<Map<String, Object>>();
        Map<String, Object> primaryKey = new LinkedHashMap<String, Object>(stateSource.getPrimaryKey());
        Map<String, Object> state = new LinkedHashMap<String, Object>(stateSource.getState());
        stateMaps.add(primaryKey);
        stateMaps.add(state);
        for (Map<String, Object> stateMap : stateMaps) {
            for (String field : stateMap.keySet()) {
                if (stateMap.get(field) instanceof String) {
                    stateMap.put(field, rs.getString(field));
                }
                if (stateMap.get(field) instanceof Integer) {
                    stateMap.put(field, rs.getInt(field));
                }
                if (stateMap.get(field) instanceof Boolean) {
                    stateMap.put(field, rs.getBoolean(field));
                }
                if (stateMap.get(field) instanceof Double) {
                    stateMap.put(field, rs.getDouble(field));
                }
                if (stateMap.get(field) instanceof Date) {
                    Timestamp ts = rs.getTimestamp(field);
                    if (ts != null)
                        stateMap.put(field, new Date(ts.getTime()));
                }
            }
            
        }
        entity.setPrimaryKey(primaryKey);
        entity.setState(state);
        
    }
    
    public static List<DBEntity> loadEntitiesData(DBEntity sample) throws JDBCException {
        Connection conn = null;
        Statement stmt = null;
        String sql = null;
        List<DBEntity> list = new ArrayList<DBEntity>();
        try {
            conn = JDBCUtils.getHSQLConnection();
            sql = "SELECT * FROM " + sample.getTableName();
            Utils.print("select_init=-==========>", sql);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                LoadedEntity entity = new LoadedEntity();
                fillEntityFromRecordSet(entity, rs, sample);
                list.add(entity);
            }            
            rs.close();
        } catch (Exception e) {
            throw new JDBCException(e, sql, null);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                throw new JDBCException(ex, sql, null);
            }
        }
        Utils.print("loaded_list:::", list);
        return list;
    }
    
    public static String getSQLLiteral(Object obj) {
        String result = null;
        if (obj instanceof String) {
            result = "'" + ((String)obj).replace("'","''") + "'";
        } else if (obj instanceof Date) {
            result = hsqlDateLiteralFormat.format((Date)obj);
        } else if (obj == null) {
            result = "NULL";
        } else {
            result = obj.toString();
        }
        
        return result;
    }
    
    public static Map<String, Object> saveEntity(DBEntity entity) throws JDBCException {
        Map<String, Object> result = null;
        Connection conn = null;
        Statement stmt = null;
        String sql = null;
        int id = -1;
        try {
            conn = JDBCUtils.getHSQLConnection();
            StringBuilder where_info = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, Object> entry: entity.getPrimaryKey().entrySet()) {
                if (first) {
                    first = false;
                } else {
                    where_info.append(" AND ");
                }
                where_info.append(entry.getKey());
                where_info.append("=");
                where_info.append(getSQLLiteral(entry.getValue()));
            }
            sql = "SELECT * FROM " + entity.getTableName() + " WHERE " + where_info;
            Utils.print("select======>", sql);
            //stmt = conn.prepareStatement(sql, 
             //       ResultSet.TYPE_SCROLL_SENSITIVE,
              //      ResultSet.CONCUR_UPDATABLE);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //prp.setInt(1, (Integer)entity.getState().get("id"));
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.first()) {
                // обновляем запись
                StringBuilder set_info = new StringBuilder();
                first = true;
                for (Map.Entry<String, Object> entry: entity.getState().entrySet()) {
                    if (first) {
                        first = false;
                    } else {
                        set_info.append(",");
                    }
                    set_info.append(entry.getKey());
                    set_info.append("=");
                    set_info.append(getSQLLiteral(entry.getValue()));
                }
                sql = "UPDATE " + entity.getTableName() + " SET " + set_info.toString() + " WHERE " + where_info;
                Utils.print("update===>", sql);
                
                stmt.executeQuery(sql);
                result = entity.getPrimaryKey();
            } else {
                //вставляем новую запись 
                StringBuilder fields = new StringBuilder("("), values = new StringBuilder(" VALUES (");
                first = true;
                Set<Map.Entry<String, Object>> entrySet = new LinkedHashSet<Map.Entry<String, Object>>();
                if (!entity.isPkAuto()) {
                    entrySet.addAll(entity.getPrimaryKey().entrySet());
                }
                entrySet.addAll(entity.getState().entrySet());
                for (Map.Entry<String, Object> entry: entrySet) {
                    if (first) {
                        first = false;
                    } else {
                        fields.append(",");
                        values.append(",");
                    }
                    fields.append(entry.getKey());
                    values.append(getSQLLiteral(entry.getValue()));
                }
                fields.append(")"); values.append(")");
                sql = "INSERT INTO " + entity.getTableName() + " " + fields.toString() + values.toString();
                Utils.print(sql);
                stmt.executeQuery(sql);
                if (entity.isPkAuto()) {
                    sql = "SELECT MAX(ID) FROM " + entity.getTableName();
                    rs = stmt.executeQuery(sql);
                    rs.first();
                    id = rs.getInt(1);
                    result = new HashMap<String, Object>();
                    result.put("id", id);
                } else {
                    result = entity.getPrimaryKey();
                }
                    
            }
            rs.close();
        } catch (Exception e) {
            throw new JDBCException(e, sql, null);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                throw new JDBCException(ex, sql, null);
            }
        }
        return result;
    }
    
    public static boolean deleteEntity(DBEntity entity) throws JDBCException {
        Connection conn = null;
        Statement stmt = null;
        String sql = null;
        try {
            conn = JDBCUtils.getHSQLConnection();
            StringBuilder where_info = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, Object> entry: entity.getPrimaryKey().entrySet()) {
                if (first) {
                    first = false;
                } else {
                    where_info.append(" AND ");
                }
                where_info.append(entry.getKey());
                where_info.append("=");
                where_info.append(getSQLLiteral(entry.getValue()));
            }
            sql = "DELETE FROM " + entity.getTableName() + " WHERE " + where_info;
            Utils.print("delete=========>", sql);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            int total = stmt.executeUpdate(sql);
            return total > 0;
        } catch (Exception e) {
            throw new JDBCException(e, sql, null);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                throw new JDBCException(ex, sql, null);
            }
        }
    }

        private void testCode() {
        /*
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement prp = null;
        try {
            conn = JDBCUtils.getHSQLConnection();            
            System.out.println(conn);
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM Customer";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id  = rs.getInt("id");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String street = rs.getString("street");
                String city = rs.getString("city");
                //System.out.println("" + id + " " + firstName + " " + lastName + " " + street + " " + city);
            }            
            rs.close();
            
            String psql = "INSERT INTO Customer VALUES(?,?,?,?,?)";
            prp = conn.prepareStatement(psql);
            
            prp.setInt(1, 333);
            prp.setString(2, "Maria");
            prp.setString(3, "Mastchenko");
            prp.setString(4, "Belomorskaya");
            prp.setString(5, "Snezhinks");
            int rows = prp.executeUpdate();
            
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id  = rs.getInt("id");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String street = rs.getString("street");
                String city = rs.getString("city");
                System.out.println("" + id + " " + firstName + " " + lastName + " " + street + " " + city);
            }            
            rs.close();
            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (prp != null) prp.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        */
    }
    
}
