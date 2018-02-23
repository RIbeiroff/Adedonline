/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author jadso
 */
public class Categoria implements Serializable {
    String categoria;
    
    public Categoria(){}
    
    public Categoria(String categoria){
        this.categoria = categoria;
    }
   
    public String getCategoria(){
        return this.categoria;
    }
    
    public void setCategoria(String categoria){
        this.categoria = categoria;
    }
}
