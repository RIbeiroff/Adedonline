package com.example.jadso.adedonline.Controller.Servidor;

import com.example.jadso.adedonline.Model.Stop;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jadso on 04/03/2018.
 */

public class ThreadDisparaStop implements Runnable {

    Stop stopDisparado;
    ArrayList<Socket> conexoes;

    public ThreadDisparaStop(Stop stopDisparado, ArrayList<Socket> conexoes){
        this.stopDisparado = stopDisparado;
        this.conexoes = conexoes;
    }

    @Override
    public void run(){
        try {
            //Se o STOP não foi disparado
            if (!stopDisparado.getStop()) {
                System.out.println("Vou enviar o stop aos participantes");
                stopDisparado.alteraCondicao(); //Ele passa a ser disparado
                for (Socket participante : conexoes){
                    System.out.println("Enviando a: " + participante.getPort());

                    //Enviar stop ao participante
                    DataOutputStream saidaDeDados = new DataOutputStream(participante.getOutputStream());
                    saidaDeDados.writeBytes("stop" + '\n'); //Envia a todas as conexões
                    saidaDeDados.flush();
                    System.out.println("Vou enviar o stop aos participantes");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadDisparaStop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
