/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Cliente;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 *
 * @author jadso
 */
public class ThreadEnviaBroadcastTeste implements Runnable {
    int porta;
    ArrayList<String> nomes = new ArrayList();
    ArrayList<DatagramPacket> pacotes_servidores = new ArrayList();
    
    public ThreadEnviaBroadcastTeste(int porta, ArrayList<String> nomes,ArrayList<DatagramPacket> pacotes_servidores){
        this.porta = porta;
        this.nomes = nomes;
        this.pacotes_servidores = pacotes_servidores;
    }
    
	@Override
	public void run() {
        //Envio broadcast na rede
		try {
                    DatagramSocket ds = new DatagramSocket();
                    byte[] b = "servidor".getBytes(); // O servidor só responde aos broadcast que contem esse conteudo "servidor"
                    InetAddress addr = InetAddress.getByName("255.255.255.255"); //Definindo o endere�o de envio do pacote neste caso o endere�o de broadcast
                    DatagramPacket pkg = new DatagramPacket(b, b.length, addr,this.porta);
                    ds.send(pkg);    //enviando pacote broadcast			
                    ds.setSoTimeout(10000); //Espero por dez segundos as respostas da rede
                    try {
                        while (true){
                            //Recebendo o tamanho do objeto
                            byte[] tamanho = new byte[50];
                            DatagramPacket pkg1 = new DatagramPacket(tamanho, tamanho.length, addr,this.porta); //pkg1 recebe o nome da sala
                            ds.receive(pkg1);
                            System.out.println("Pacote recebido...");
                            nomes.add(new String (pkg1.getData(), 0 , pkg1.getLength()));
                            pacotes_servidores.add(pkg1);
                        }
                    } catch (SocketTimeoutException ste){
                        System.out.println("Tempo encerrado...");
                        for (DatagramPacket pacote: pacotes_servidores){
                            System.out.println("Nome da sala: " + new String(pacote.getData(), 0, pacote.getData().length));
                            System.out.println("Ip: " + pacote.getAddress() + " / " + pacote.getPort());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Nao foi possivel enviar a mensagem");
                    e.printStackTrace();
		        }
    }
    
}
