package com.example.jadso.adedonline.Controller.Servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
                System.out.println("Enviando ao participante: " + participante.getInetAddress() + " / " + participante.getPort());
                DataOutputStream saida = new DataOutputStream(participante.getOutputStream()); //Cadeia de saída
                BufferedReader inFromServer = new BufferedReader((new InputStreamReader(participante.getInputStream())));
                saida.writeBytes( this.letraSorteada + "" + '\n');
                saida.flush();
                System.out.println("Enviado letra ao particiapnte: " + letraSorteada);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
