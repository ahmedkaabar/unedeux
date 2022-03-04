/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionCommandeLivraison.entities;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Fedi
 */
public class Iservice {
  

/**
 *
 * @author ahmed
     * @param <P>
 */
public interface IService <P>{
    
  
    P insert(P entity);
    void delete(int id);
    void update(int id,P entity);
    
    ArrayList<P> getall();
  
    P get(int id )throws SQLException;
    
    
}
    
}
