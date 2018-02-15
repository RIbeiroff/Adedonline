package com.example.jadso.adedonline.Controller.Cliente;


import android.widget.ArrayAdapter;

import com.example.jadso.adedonline.SalaEntrar;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class ThreadEnviaBroadcast implements Runnable {

    int porta;
    ArrayList<String> nomes;

    public ThreadEnviaBroadcast(int porta, ArrayList<String> nomes) {
        this.porta = porta;
        this.nomes = nomes;
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
                        }
                    } catch (SocketTimeoutException ste){
                        System.out.println("Tempo encerrado...");

                    }
                } catch (Exception e) {
                    System.out.println("Nao foi possivel enviar a mensagem");
                    e.printStackTrace();
		        }
    }
}
