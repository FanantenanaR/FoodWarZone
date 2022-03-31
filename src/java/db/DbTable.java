package db;


import connection.Mico;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
convention
foreing key mila manomboka @id
mampiasa default value
Mysql tsy Pascal convention
*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Verthaga
 */
public class DbTable {

    
    public String upperFirst(String s) {
        char[] charArray = s.toCharArray();
        charArray[0]=Character.toUpperCase(charArray[0]);
        String chaine = new String(charArray);
        return chaine;
    }
    
    public Method setter(Class classe, String name){
        Method valiny = null;
        Method methods[] = classe.getDeclaredMethods();
        for(Method m : methods){
            if(m.getName().equals("set"+this.upperFirst(name))){
                valiny = m;
                break;
            }
        }
        return valiny;
    }
    
    public Method getter(Class classe, String name){
        Method valiny = null;
        Method methods[] = classe.getDeclaredMethods();
        for(Method m : methods){
            if(m.getName().equals("get"+this.upperFirst(name))){
                valiny = m;
                break;
            }
        }
        return valiny;
    }
    
    
    public HashMap<String, Object> fieldData() throws IllegalAccessException, IllegalArgumentException{
        HashMap<String, Object> valiny = new HashMap<>();
        Field[] allField = this.getClass().getDeclaredFields();
        for(Field field: allField){
            try {
                Object value = this.getter(this.getClass(), field.getName()).invoke(this);
                valiny.put(field.getName(), value);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(DbTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return valiny;
    }
    
    public HashMap<String, Object> fieldNotNull() throws IllegalAccessException{
        HashMap<String, Object> valiny = new HashMap<>();
        HashMap<String, Object> all = this.fieldData();
        for(Map.Entry mapElement: all.entrySet()){
            if(mapElement.getKey().toString().startsWith("id")){
                if(mapElement.getValue() instanceof Number){
                    if(!mapElement.getValue().equals(0))
                        valiny.put(mapElement.getKey().toString(), mapElement.getValue());
                } else if (mapElement.getValue() instanceof java.lang.String) {
                    if(!mapElement.getValue().equals("")){
                        valiny.put(mapElement.getKey().toString(), mapElement.getValue());
                    }
                }                        
            } else {
                if(mapElement.getValue()!=null)
                    valiny.put(mapElement.getKey().toString(), mapElement.getValue());
            }
        }
        
        return valiny;
    }
    
    public HashMap <String, Object> fieldString() throws IllegalAccessException{
        HashMap<String, Object> valiny = new HashMap<>();
        HashMap<String, Object> all = this.fieldData();
        for(Map.Entry mapElement: all.entrySet()){
            if(mapElement.getValue() instanceof String){
                if(mapElement.getValue() != "")
                    valiny.put(mapElement.getKey().toString(), mapElement.getValue());
            }
        }
        
        return valiny;
    }
    public HashMap<String, Object> fieldNotNullStrict() throws IllegalAccessException{
        HashMap<String, Object> valiny = new HashMap<>();
        HashMap<String, Object> all = this.fieldData();
        for(Map.Entry mapElement: all.entrySet()){
            if(mapElement.getValue() instanceof Integer){
                if((int)mapElement.getValue() != 0)
                    valiny.put(mapElement.getKey().toString(), mapElement.getValue());
            } else if (mapElement.getValue() instanceof Double){
                if((double)mapElement.getValue() != 0.0)
                    valiny.put(mapElement.getKey().toString(), mapElement.getValue());
            } else if (mapElement.getValue() instanceof Float){
                if((float)mapElement.getValue() != 0.0)
                    valiny.put(mapElement.getKey().toString(), mapElement.getValue());
            /*} else if (mapElement.getValue() instanceof java.lang.String){
                if(mapElement.getValue().toString().isEmpty())
                    valiny.put(mapElement.getKey().toString(), mapElement.getValue());*/
            } else  {
                if(mapElement.getValue() != null)
                    valiny.put(mapElement.getKey().toString(), mapElement.getValue());
            }
        }
        return valiny;
    }
    
    public HashMap<String, String> fieldType(){
        HashMap<String, String> valiny = new HashMap<>();
        Field[] allField = this.getClass().getDeclaredFields();
        for(Field field: allField){
            String type = this.getter(this.getClass(), field.getName()).getReturnType().getSimpleName();
            valiny.put(field.getName(), type);
        }
        return valiny;
    }
    /*
    my where requete hafa mihitsy,
    tsy mifanaraka @entity
    */
    public ArrayList get(String myWhere) throws SQLException, IllegalAccessException{
        ArrayList<DbTable> valiny = new ArrayList<DbTable>();
        HashMap<String, Object> fieldData = this.fieldNotNullStrict();
        String table = this.getClass().getSimpleName();
        Mico m = new Mico();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        HashMap<String, String> fieldType = null;
        String req = "SELECT * FROM "+table;
        if(!myWhere.isEmpty()){
            req = req + " WHERE " + myWhere;
        }
        if(fieldData.size()!=0 && myWhere.isEmpty()){
            String where = " WHERE ";
            int isa = 1;
            for(Map.Entry mapElement: fieldData.entrySet()){
                where = where  + mapElement.getKey().toString() + " = ? ";
                if(isa < fieldData.size()){
                    where += " AND ";
                }
                isa++;
            }
            req += where;
        }
        
        try {
            con = m.connect();
            ps = con.prepareStatement(req);
            if(fieldData.size()!=0 && myWhere.isEmpty()){
               int i=1;
                for(Map.Entry mapElement: fieldData.entrySet()){
                    Object colName = mapElement.getKey();
                    
                    Object value = this.getter(this.getClass(), colName.toString()).invoke(this);
                    ps.setObject(i, value);
                    i++;
                }
            }
            System.out.println(ps);
            res = ps.executeQuery();
            fieldType = this.fieldType();
            while (res.next()){
                Object temp = this.getClass().newInstance();
                for(Map.Entry mapElement: fieldType.entrySet()){
                    if(mapElement.getValue().equals("int")){
                        setter(this.getClass(), mapElement.getKey().toString()).invoke(temp, res.getInt(mapElement.getKey().toString()));
                    }else if (mapElement.getValue().equals("float")){
                        setter(this.getClass(), mapElement.getKey().toString()).invoke(temp, res.getFloat(mapElement.getKey().toString()));
                    }else if (mapElement.getValue().equals("double")){
                        setter(this.getClass(), mapElement.getKey().toString()).invoke(temp, res.getDouble(mapElement.getKey().toString()));
                    }else if (mapElement.getValue().equals("String")){
                        setter(this.getClass(), mapElement.getKey().toString()).invoke(temp, res.getString(mapElement.getKey().toString()));
                    }else if (mapElement.getValue().equals("Date")){
                        setter(this.getClass(), mapElement.getKey().toString()).invoke(temp, res.getDate(mapElement.getKey().toString()));
                    }else if (mapElement.getValue().equals("Time")){
                        setter(this.getClass(), mapElement.getKey().toString()).invoke(temp, res.getTime(mapElement.getKey().toString()));
                    }else if (mapElement.getValue().equals("Timestamp")){
                        setter(this.getClass(), mapElement.getKey().toString()).invoke(temp, res.getTimestamp(mapElement.getKey().toString()));
                    }
                }
                valiny.add((DbTable) temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally{
            res.close();
            ps.close();
            con.close();
            fieldType.clear();
            return valiny;
        }
    }
    /*
        this is ignore case query
        if entity == null and mywhere is empty => no result
        mety fona na misy entity na tsy misy where
        where just tohiny le requete
    
        entity tsy maintsy string
        
        pour les recherche avancees
    */
    public ArrayList getLike(String myWhere) throws SQLException, IllegalAccessException{
        ArrayList<DbTable> valiny = new ArrayList<DbTable>();
        HashMap<String, Object> fieldData = this.fieldString();
        String table = this.getClass().getSimpleName();
        Mico m = new Mico();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        HashMap<String, String> fieldType = null;
        String req = "SELECT * FROM "+table;
        if(myWhere.isEmpty() && fieldData.size()==0){
            return valiny;
        }
        if(!myWhere.isEmpty() && fieldData.size()==0){
            req = req + " WHERE " + myWhere;
        }
        if(fieldData.size()!=0){
            String where = " WHERE ";
            int isa = 1;
            for(Map.Entry mapElement: fieldData.entrySet()){
                where = where + "(SELECT UPPER(" + mapElement.getKey().toString()+ "))" + " like ? ";
                if(isa < fieldData.size()){
                    where += " AND ";
                }
                isa++;
            }
            req += where;
            if(!myWhere.isEmpty()){
                req += " AND " + myWhere;
            }
        }
        
        try {
            con = m.connect();
            ps = con.prepareStatement(req);
            if(fieldData.size()!=0){
               int i=1;
                for(Map.Entry mapElement: fieldData.entrySet()){
                    Object colName = mapElement.getKey();
                    
                    Object value = this.getter(this.getClass(), colName.toString()).invoke(this);
                    ps.setObject(i, "%"+value.toString().toUpperCase()+"%");
                    i++;
                }
            }
            System.out.println(ps);
            res = ps.executeQuery();
            fieldType = this.fieldType();
            while (res.next()){
                Object temp = this.getClass().newInstance();
                for(Map.Entry mapElement: fieldType.entrySet()){
                    if(mapElement.getValue().equals("int")){
                        setter(this.getClass(), mapElement.getKey().toString()).invoke(temp, res.getInt(mapElement.getKey().toString()));
                    }else if (mapElement.getValue().equals("float")){
                        setter(this.getClass(), mapElement.getKey().toString()).invoke(temp, res.getFloat(mapElement.getKey().toString()));
                    }else if (mapElement.getValue().equals("double")){
                        setter(this.getClass(), mapElement.getKey().toString()).invoke(temp, res.getDouble(mapElement.getKey().toString()));
                    }else if (mapElement.getValue().equals("String")){
                        setter(this.getClass(), mapElement.getKey().toString()).invoke(temp, res.getString(mapElement.getKey().toString()));
                    }else if (mapElement.getValue().equals("Date")){
                        setter(this.getClass(), mapElement.getKey().toString()).invoke(temp, res.getDate(mapElement.getKey().toString()));
                    }else if (mapElement.getValue().equals("Time")){
                        setter(this.getClass(), mapElement.getKey().toString()).invoke(temp, res.getTime(mapElement.getKey().toString()));
                    }else if (mapElement.getValue().equals("Timestamp")){
                        setter(this.getClass(), mapElement.getKey().toString()).invoke(temp, res.getTimestamp(mapElement.getKey().toString()));
                    }
                }
                valiny.add((DbTable) temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally{
            res.close();
            ps.close();
            con.close();
            fieldType.clear();
            return valiny;
        }
    }
    
    public void insert() throws IllegalAccessException, Exception{
        HashMap<String, Object> fieldData = this.fieldNotNull();
        Mico m = new Mico();
        Connection con = null;
        PreparedStatement ps =  null;
        String req = "INSERT INTO "+this.getClass().getSimpleName();
        String col = "";
        String toChange = "?";
        int length = fieldData.size();
        System.out.println("length=" + length);
        int isa = 1;
        if(length>1){
            for(int i = 1; i<length; i++){
                    toChange += ", ?";
            }
        }
        toChange = "("+ toChange+ ")";
        for(Map.Entry mapElement: fieldData.entrySet()){
            col = col + "," + mapElement.getKey().toString();
        }
        char[] colChar = col.toCharArray();
        colChar[0] = ' ';
        col = "("+new String(colChar)+")";
        req = req + " " + col + " VALUES " + toChange;
        try {
            con = m.connect();
            ps = con.prepareStatement(req);
            
            for(Map.Entry mapElement: fieldData.entrySet()){
                Object colName = mapElement.getKey();
                
                Object value = this.getter(this.getClass(), colName.toString()).invoke(this);
                ps.setObject(isa, value);
                isa++;
            }
            //System.out.println(ps);
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            ps.close();
            con.close();
            fieldData.clear();
        }
        
    }
    
    public void delete(String myQuery) throws IllegalAccessException, SQLException, Exception{
        HashMap<String, Object> fieldData = this.fieldNotNullStrict();
        Mico m = new Mico();
        Connection con = null;
        PreparedStatement ps =  null;
        String req = "DELETE FROM " + this.getClass().getSimpleName() + " WHERE ";
        if(!myQuery.isEmpty()){
            req += myQuery;
        } else {
            String where = "";
            int isa = 1;
            for(Map.Entry mapElement: fieldData.entrySet()){
                where = where  + mapElement.getKey().toString() + " = ? ";
                if(isa < fieldData.size()){
                    where += " AND ";
                }
                isa++;
            }
            req += where;
        }
        
        try {
            con = m.connect();
            ps = con.prepareStatement(req);
            if(myQuery.isEmpty()){
                int i=1;
                for(Map.Entry mapElement: fieldData.entrySet()){
                    Object colName = mapElement.getKey();
                    
                    Object value = this.getter(this.getClass(), colName.toString()).invoke(this);
                    ps.setObject(i, value);
                    i++;
                }
            }
            
            System.out.println(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            ps.close();
            con.close();
            fieldData.clear();
        }
    }
    
    public void update(String myWhere) throws IllegalAccessException, SQLException, Exception{
        HashMap<String, Object> fieldData = this.fieldNotNullStrict();
        Mico m = new Mico();
        Connection con = null;
        PreparedStatement ps =  null;
        String req = "UPDATE " +  this.getClass().getSimpleName() + " SET ";
        String set = "";
        int isa = 1;
        int length = fieldData.size();
        for(Map.Entry mapElement: fieldData.entrySet()){
            if(!mapElement.getKey().toString().equals("id")){
                set = set  + mapElement.getKey().toString() + " = ? ";
                if(isa<length-1){
                    set += ", ";
                }
                isa++;
            }
        }
        String where = "";
        if(myWhere.isEmpty())
            where = "WHERE id = '" + fieldData.get("id").toString() +"'";
        else
            where ="WHERE " + myWhere;
        
        req = req + set + where;
        
        try {
            con = m.connect();
            ps = con.prepareStatement(req);
            int i=1;
            for(Map.Entry mapElement: fieldData.entrySet()){
                if(!mapElement.getKey().toString().equals("id")){
                    Object colName = mapElement.getKey();

                    Object value = this.getter(this.getClass(), colName.toString()).invoke(this);
                    ps.setObject(i, value);
                    i++;
                }
            }
            System.out.println(ps);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            ps.close();
            con.close();
            fieldData.clear();
        }
        
    }
    
}
