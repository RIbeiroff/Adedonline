package com.example.jadso.adedonline.Model;

import java.io.Serializable;

/**
 * Created by jadso on 12/02/2018.
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
