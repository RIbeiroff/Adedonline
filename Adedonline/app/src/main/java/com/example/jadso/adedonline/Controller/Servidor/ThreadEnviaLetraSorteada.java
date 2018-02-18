package com.example.jadso.adedonline.Controller.Servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jadso on 18/02/2018.
 */

public class ThreadEnviaLetraSorteada implements Runnable {

    ArrayList<Socket> socketParticpantes;
    char letraSorteada;

    public ThreadEnviaLetraSorteada(ArrayList<Socket> socketParcitipantes, char letraSorteada){
        this.socketParticpantes = socketParcitipantes;
        this.letraSorteada = letraSorteada;
    }

    @Override
    public void run(){
        //Percorrendo as conexoes e enviando a letra sorteada
        for (Socket participante : socketParticpantes){
            try {
                DataOutputStream saida = new DataOutputStream(participante.getOutputStream()); //Cadeia de saída
                saida.writeChar(letraSorteada);
                saida.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
