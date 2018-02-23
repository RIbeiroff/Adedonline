/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Servidor;

import Model.Participante;
import Model.Sala;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *  Essa classe tem como objetivo acomodar os novos participantes, assim que o participante clica em entrar na sala, 
 * ele estará fazendo uma solicitação ao serverSocket
 */
public class ThreadGerenciamentoServidor implements Runnable{
    
    Sala sala;
    int porta;
    ServerSocket servidor;
    
    public ThreadGerenciamentoServidor(Sala sala, int porta) throws IOException{
        this.sala = sala;
        this.porta = porta;
    }
    
    @Override
    public void run() {
        try {
            servidor = new ServerSocket(porta); //Criando servidor com a porta passada por parametro
        } catch (IOException ex) {
            Logger.getLogger(ThreadGerenciamentoServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        while (true){
            try {
                sala.participantes.clear();
                Socket conexaoSocket = servidor.accept();
                System.out.println("Aceitando conexao de : " + conexaoSocket.getInetAddress());
                sala.participantes.add(conexaoSocket);
                System.out.println("Adicionado o participante");
                
                for (Socket participante : sala.participantes){
                    System.out.println("Enviando ao participante: " + participante.getInetAddress() + " / " + participante.getPort());
                    DataOutputStream saida = new DataOutputStream(participante.getOutputStream()); //Cadeia de saída
                    BufferedReader inFromServer = new BufferedReader((new InputStreamReader(participante.getInputStream())));
                    saida.writeBytes( 'a' + "" + '\n');
                    saida.flush();
                    System.out.println("Enviado letra aos particiapnte...");
                    }
                
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
    }
}

