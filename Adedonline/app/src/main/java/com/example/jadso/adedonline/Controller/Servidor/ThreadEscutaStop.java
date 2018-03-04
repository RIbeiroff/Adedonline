package com.example.jadso.adedonline.Controller.Servidor;

import com.example.jadso.adedonline.Model.Conexao;
import com.example.jadso.adedonline.Model.Stop;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jadso on 04/03/2018.
 */

public class ThreadEscutaStop implements Runnable {

    public static Stop condicaoStop;
    Socket conexao;
    ArrayList<Socket> todasConexoes;

    public ThreadEscutaStop(Stop condicaoStop, Socket conexao, ArrayList<Socket> todasConexoes){
        this.condicaoStop = condicaoStop;
        this.conexao = conexao;
        this.todasConexoes = todasConexoes;
    }

    @Override
    public void run(){
        try {
            //Ouvindo STOP
            System.out.println("Agurdando stop de : " + conexao.getPort());
            BufferedReader entradaDeDados = new BufferedReader(new InputStreamReader(this.conexao.getInputStream()));
            String stop = entradaDeDados.readLine();
            System.out.println("Recebido pacote de stop");

            //Se ninguém disparou o stop anteriormente, então vou percorrer todas as conexões e disparar o stop
            if (!condicaoStop.getStop()){
                condicaoStop.alteraCondicao(); //O STOP agora foi disparado

                for (Socket participante : this.todasConexoes){
                    //Enviando o STOP a cada uma delas, mas para o autor do stop não deve retornar uma confirmacao
                    DataOutputStream saidaDeDados = new DataOutputStream(participante.getOutputStream());
                    if (participante != this.conexao) {
                        saidaDeDados.writeBytes("stop" + '\n');
                        saidaDeDados.flush();
                    } else {
                        saidaDeDados.writeBytes("confirmadostop" + '\n');
                        saidaDeDados.flush();
                    }
                }
            }
            System.out.println("Escuta encerrada");
        } catch (IOException ex) {
            Logger.getLogger(ThreadEscutaStop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
