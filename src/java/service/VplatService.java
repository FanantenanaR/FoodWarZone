/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Vplat;
import java.util.ArrayList;

/**
 *
 * @author Verthaga
 */
public class VplatService {
    public static ArrayList<Vplat> getAll(){
        ArrayList<Vplat> valiny = null;
        Vplat vplat = new Vplat();
        try {
            valiny = vplat.get("");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            return valiny;
        }
    }
    
    public static ArrayList<Vplat> getByCategrie(int idCategorie){
        ArrayList<Vplat> valiny = null;
        Vplat vplat = new Vplat();
        vplat.setIdCategorie(idCategorie);
        try {
            valiny = vplat.get("");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            return valiny;
        }
    }
}
