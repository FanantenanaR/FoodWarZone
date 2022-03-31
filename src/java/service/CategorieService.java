/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Categorie;
import entity.Vplat;
import java.util.ArrayList;

/**
 *
 * @author Verthaga
 */
public class CategorieService {
    public static ArrayList<Categorie> getAll(){
        ArrayList<Categorie> valiny = null;
        Categorie categorie = new Categorie();
        try {
            valiny = categorie.get("");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            return valiny;
        }
    }
}
