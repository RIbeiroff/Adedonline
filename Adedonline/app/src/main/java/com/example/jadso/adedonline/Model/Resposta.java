package com.example.jadso.adedonline.Model;

import java.io.Serializable;

/**
 * Created by jadso on 07/03/2018.
 */

public class Resposta implements Serializable {
    public String categoria, resposta;
    boolean condicao;

    public Resposta(String categoria, String resposta){
        this.categoria = categoria;
        this.resposta = resposta;
    }

    public Resposta(String categoria, String resposta, boolean condicao){
        this.categoria = categoria;
        this.resposta = resposta;
        this.condicao = condicao;
    }
}
