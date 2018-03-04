package com.example.jadso.adedonline.Model;

/**
 * Created by jadso on 04/03/2018.
 */

public class Stop {

    boolean condicao;

    public Stop(){
        this.condicao = false;
    }

    synchronized public void alteraCondicao(){
        this.condicao = true;
    }

    public boolean getStop(){
        return this.condicao;
    }
}
