/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Verthaga
 */
public class ServeurTips  extends db.DbTable{
    int idServeur;
    double montantTips;
    String nomServeur;

    public ServeurTips (){}
    
    public ServeurTips (int idServeur, double montantTips, String nomServeur){
          setIdServeur(idServeur);
          setMontantTips(montantTips);
          setNomServeur(nomServeur);
    }
    
    public ServeurTips (double montantTips, String nomServeur){
          setMontantTips(montantTips);
          setNomServeur(nomServeur);
    }
    
    public int getIdServeur() {
        return idServeur;
    }

    public void setIdServeur(int idServeur) {
        this.idServeur = idServeur;
    }

    public double getMontantTips() {
        return montantTips;
    }

    public void setMontantTips(double montantTips) {
        this.montantTips = montantTips;
    }

    public String getNomServeur() {
        return nomServeur;
    }

    public void setNomServeur(String nomServeur) {
        this.nomServeur = nomServeur;
    }
    
            
}
