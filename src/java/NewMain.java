
import entity.Vplat;
import java.util.ArrayList;
import service.VplatService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Verthaga
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Vplat> list = VplatService.getAll();
        int stop = 0;
    }
    
}
