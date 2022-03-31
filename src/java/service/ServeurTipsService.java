package service;


import connection.Mico;
import entity.ServeurTips;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Verthaga
 */
public class ServeurTipsService {
    public static ArrayList<ServeurTips> getTips(String date1, String date2) throws Exception{
        ArrayList<ServeurTips> valiny = new ArrayList();
        Mico mico = new Mico();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String sql = "select idserveur, sum(tips) as montanttips, nomserveur from vcommande "
                + "where dateheure between '"+ date1 +"' and '" + date2 + "'"
                + " group by idserveur, nomserveur";
        try {
            connection = mico.connect();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                ServeurTips serveurTips = new ServeurTips();
                serveurTips.setIdServeur(resultSet.getInt("idserveur"));
                serveurTips.setMontantTips(resultSet.getDouble("montanttips"));
                serveurTips.setNomServeur(resultSet.getString("nomserveur"));
                valiny.add(serveurTips);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            resultSet.close();
            statement.close();
            connection.close();
        }
        
        return valiny;
    }
}
