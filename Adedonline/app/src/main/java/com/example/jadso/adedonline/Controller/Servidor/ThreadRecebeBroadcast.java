/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.jadso.adedonline.Controller.Servidor;


import com.example.jadso.adedonline.Model.Categoria;
import com.example.jadso.adedonline.Model.Participante;
import com.example.jadso.adedonline.Model.Sala;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;


public class ThreadRecebeBroadcast implements Runnable {
    int porta;
    Sala sala;
    
    public ThreadRecebeBroadcast(Sala sala, int porta){
        this.sala = sala;
        this.porta = porta;
    }
    
        public static int byteArrayToInt(byte[] b) 
        {
            return   b[3] & 0xFF |
                    (b[2] & 0xFF) << 8 |
                    (b[1] & 0xFF) << 16 |
                    (b[0] & 0xFF) << 24;
        }

        public static byte[] intToByteArray(int a)
        {
            return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),   
                (byte) ((a >> 8) & 0xFF),   
                (byte) (a & 0xFF)
            };
        }       
    
    @Override
    public void run(){
        
        while (true) {
            try {
                MulticastSocket mcs = new MulticastSocket(porta); //Criacao de instancia para ouvir o broadcast
                byte ping[] = new byte[10];
                        
                DatagramPacket pacote = new DatagramPacket(ping, ping.length);
                mcs.receive(pacote); //Recebendo o ping da rede
                String data = new String(pacote.getData(), 0, pacote.getLength());
              

                if (data.equals("servidor")){ //Testando para saber se o cliente quer saber se sou um servidor

                    byte byteArraySala[];
                    byteArraySala = convertObjectToByteArray(this.sala); //Convertendo o objeto sala em array de bytes
                    byte[] tamanho = new byte[4];
                    tamanho = intToByteArray(byteArraySala.length); 

                    DatagramPacket pacote_com_tamanho_objeto = new DatagramPacket(tamanho, 0, tamanho.length, pacote.getAddress(), pacote.getPort());
                    mcs.send(pacote_com_tamanho_objeto); // Enviando o tamanho do objeto, para em seguida enviar o proprio objeto

                    DatagramPacket pacote_com_objeto = new DatagramPacket(byteArraySala, 0, byteArraySala.length, pacote.getAddress(), pacote.getPort());
                    mcs.send(pacote_com_objeto);
                    String dado = new String(pacote_com_objeto.getData(), 0, pacote_com_objeto.getLength());
                    
                }
            } catch (Exception e){
                System.out.print(e.getMessage());
            }
        }
    }
    
        public static byte[] convertObjectToByteArray(Sala sala) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(sala);
            objectOutputStream.flush();
            objectOutputStream.close();
            byteArrayOutputStream.close();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        return bytes;
    }
 
    public static Sala convertByteArrayToObject(byte[] bytes) {
        Sala sala = null;
 
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (Sala) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
         
        return null;
    }
}
