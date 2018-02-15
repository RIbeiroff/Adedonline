/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.jadso.adedonline.Controller.Servidor;


import com.example.jadso.adedonline.Model.Sala;
import java.net.DatagramPacket;
import java.net.MulticastSocket;


public class ThreadRecebeBroadcast implements Runnable {
    int porta;
    Sala sala;
    
    public ThreadRecebeBroadcast(Sala sala, int porta){
        this.sala = sala;
        this.porta = porta;
    }
    
    @Override
    public void run(){
        
        while (true) {
            try {
                System.out.println("Aguardando broadcast...");
                MulticastSocket mcs = new MulticastSocket(porta); //Criacao de instancia para ouvir o broadcast
                byte ping[] = new byte[10];

                DatagramPacket pacote = new DatagramPacket(ping, ping.length);
                mcs.receive(pacote); //Recebendo o ping da rede
                String data = new String(pacote.getData(), 0, pacote.getLength());

                System.out.println("Recebido broadcast de : " + pacote.getAddress() + " | " + pacote.getPort());

                if (data.equals("servidor")){ //Testando para saber se o cliente quer saber se sou um servidor
                    byte[] tamanho = sala.getNome().getBytes();
                    DatagramPacket pacote_com_tamanho_objeto = new DatagramPacket(tamanho, 0, tamanho.length, pacote.getAddress(), pacote.getPort());
                    mcs.send(pacote_com_tamanho_objeto); // Enviando o nome da sala para o interessado
                    System.out.println("Enviado: " + sala.getNome());
                }
            } catch (Exception e){
                System.out.print(e.getMessage());
            }
        }
    }
}
