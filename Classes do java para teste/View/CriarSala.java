/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Cliente.ThreadEnviaBroadcast;
import Controller.Cliente.ThreadEnviaBroadcastTeste;
import Controller.Servidor.ThreadGerenciamentoServidor;
import Model.Sala;
import Controller.Servidor.ThreadRecebeBroadcast;
import Model.Categoria;
import Model.Participante;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Essa classe  representa o ato de clicar em criar a sala, pois o autor do clique será responsável por escutar os broadscast
 *  que pergunta "Quem é servidor?", e ao mesmo tempo, gerenciar os participantes do Adedonha. 
 */
public class CriarSala {
    public static ArrayList<DatagramPacket> pacotes_servidores = new ArrayList();
    
    public static void main(String[] args) throws IOException{
        int porta = 12345;

        
        Sala Sala = new Sala();
        Sala.setNome("hhahahaha");
        Sala.totalRodadas = 20;
        Sala.categorias.add("Nome");
        Sala.categorias.add("Cidade");
        Sala.categorias.add("Série");
        
        
       // ThreadEnviaBroadcast teste = new ThreadEnviaBroadcast(12345);
        
       new Thread( new ThreadRecebeBroadcast(Sala, porta)).start();
       new Thread( new ThreadGerenciamentoServidor(Sala, porta)).start();
       //new Thread( new ThreadEnviaBroadcastTeste(12345, nomes, pacotes_servidores)).start();
       
       /*
       new Thread(new Runnable(){
          @Override
           public void run(){
               while (pacotes_servidores.size() == 0)
                   try {
                       Thread.sleep(4000);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(CriarSala.class.getName()).log(Level.SEVERE, null, ex);
                   }
               
               System.out.println("Tentando conexao com: " + pacotes_servidores.get(0).getAddress());
               
              try {
                  Socket clientSocket = new Socket(pacotes_servidores.get(0).getAddress(), 12345);
                
                  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                  BufferedReader inFromServer = new BufferedReader((new InputStreamReader(clientSocket.getInputStream())));
                  System.out.println("Crianda a conexao com o servidor...");
                  
                  String retorno = inFromServer.readLine();
                  System.out.println("Letra sorteada: " + retorno);
              } catch (IOException ex) {
                  Logger.getLogger(CriarSala.class.getName()).log(Level.SEVERE, null, ex);
              }
          
           }
       }).start();
       */
    }   
}
