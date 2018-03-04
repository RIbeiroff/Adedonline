package com.example.jadso.adedonline.Controller.Cliente;

import com.example.jadso.adedonline.Model.Conexao;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jadso on 04/03/2018.
 */

public class ThreadDisparaStop implements Runnable {

    Socket conexao;

    public ThreadDisparaStop (Socket conexao){
        this.conexao = conexao;
    }

    @Override
    public void run(){
        try {
            System.out.println("Enviando stop a: " + conexao.getPort());
            DataOutputStream saidaDeDados = new DataOutputStream(conexao.getOutputStream());
            saidaDeDados.writeBytes("stop" + '\n');
            saidaDeDados.flush();
        } catch (IOException ex) {
            Logger.getLogger(ThreadDisparaStop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
