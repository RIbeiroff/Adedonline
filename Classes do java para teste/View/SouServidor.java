/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author jadso
 */
public class SouServidor {
    
        public static byte[] convertObjectToByteArray(ArrayList arrayList) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(arrayList);
            objectOutputStream.flush();
            objectOutputStream.close();
            byteArrayOutputStream.close();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        return bytes;
    }
 
    /**
     * Converte um array de bytes para um objeto
     * @param bytes
     * @return object
     */
    public static ArrayList convertByteArrayToObject(byte[] bytes) {
        ArrayList sala = new ArrayList();
 
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (ArrayList) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
         
        return null;
    }    
    
    
    public static void main(String [] args) throws IOException{
        
        /*
        int porta = 6789;
        InetAddress ip = InetAddress.getByName("192.168.3.103");
        Socket conexao = new Socket(ip,porta);
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(conexao.getInputStream())); 
        DataOutputStream outToServer = new DataOutputStream(conexao.getOutputStream());
        
        String consulta = "select nome from pessoa";
        outToServer.writeBytes(consulta + '\n');
        System.out.println("Aguardando consulta");
        
        ArrayList<String> array = new ArrayList();
        array.add("jadson");
        array.add("Nome");
        
        byte[] byteArray = convertObjectToByteArray(array);
        
        ArrayList<String> resultado = new ArrayList();
        
        resultado = convertByteArrayToObject(byteArray);
        
        
        for (String indice : resultado){
            System.out.println(indice);
        }
*/
        
         
         DatagramSocket clientSocket = new DatagramSocket();
         InetAddress IPAddress = InetAddress.getByName("192.168.3.103");
         
         byte[] sendData = new byte[1024];
         sendData = "select nome from pessoa".getBytes();
         
         System.out.println("Montagem do pacote");
         DatagramPacket pacote_envio = new DatagramPacket(sendData,sendData.length, IPAddress, 9876);
         clientSocket.send(pacote_envio);
         System.out.println("Enviei o pacote");   
         
         byte[] receiveData = new byte[1024];
         DatagramPacket pacote_recebido = new DatagramPacket(receiveData, receiveData.length);
         clientSocket.receive(pacote_recebido);
         System.out.println("Recebi o pacote");   
         
         byte[] resultado = pacote_recebido.getData();
         
         ArrayList<String> consulta = new ArrayList();
         consulta = convertByteArrayToObject(resultado);
         
         for (String indice : consulta){
             System.out.println(indice);
         }
    }
    
}
