/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.jadso.adedonline.Controller.Servidor;

import com.example.jadso.adedonline.Model.Sala;
import com.example.jadso.adedonline.Model.Participante;
import java.io.IOException;
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
                Socket conexaoSocket = servidor.accept();
                sala.participantes.add( new Participante(conexaoSocket.getInetAddress(), conexaoSocket.getPort()));
                System.out.println("Chegou novo particpante...");
            } catch (IOException ex) {
                Logger.getLogger(ThreadGerenciamentoServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
