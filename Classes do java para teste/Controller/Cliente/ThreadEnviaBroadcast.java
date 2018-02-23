package Controller.Cliente;

import Model.Sala;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class ThreadEnviaBroadcast implements Runnable {
	
	int porta;
        ArrayList<Sala> salas;
	
        public ThreadEnviaBroadcast(int porta) {
		this.porta = porta;
                this.salas = new ArrayList();
        }
	
        //Conversão de byteArray para int
        public static int byteArrayToInt(byte[] b) 
        {
            return   b[3] & 0xFF |
                    (b[2] & 0xFF) << 8 |
                    (b[1] & 0xFF) << 16 |
                    (b[0] & 0xFF) << 24;
        }

        //Conversão de int para byteArray
        public static byte[] intToByteArray(int a)
        {
            return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),   
                (byte) ((a >> 8) & 0xFF),   
                (byte) (a & 0xFF)
            };
        }           
        
        //Conversão de byteArray para o objeto Sala
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
        
	@Override
	public void run() {

            
		//Envio broadcast na rede
		try {
                    DatagramSocket ds = new DatagramSocket();
                    byte[] b = "servidor".getBytes(); // O servidor só responde aos broadcast que contem esse conteudo "servidor"
                    InetAddress addr = InetAddress.getByName("255.255.255.255"); //Definindo o endere�o de envio do pacote neste caso o endere�o de broadcast
                    DatagramPacket pkg = new DatagramPacket(b, b.length, addr,this.porta);
                    ds.send(pkg);    //enviando pacote broadcast			
                    System.out.println("Broadcast enviado...");
                    System.out.println("Ip e porta do pkg: " + pkg.getAddress().toString() + "/" + pkg.getPort());

                    ds.setSoTimeout(10000); //Espero por dez segundos as respostas da rede
                    
                    try {
                        while (true){
                            //Recebendo o tamanho do objeto
                            byte[] tamanho = new byte[4];
                            DatagramPacket pkg1 = new DatagramPacket(tamanho, tamanho.length, addr,this.porta); //pkg1 recebe o tamanho do objeto sala
                            ds.receive(pkg1);

                            //Craindo um array de bytes do tamanho do objeto
                            byte[] byteArraySala = new byte[byteArrayToInt(pkg1.getData())];
                            DatagramPacket pkg2 = new DatagramPacket(byteArraySala, byteArraySala.length, addr,this.porta); //pkg2 recebe o objeto
                            ds.receive(pkg2);
                            
                            salas.add(convertByteArrayToObject(pkg2.getData()));
                        }                        
                    } catch (SocketTimeoutException ste){
                        System.out.println("O tempo de recebimendo das salas ja foi concluido");
                        int contador = 1;
                        for (Sala sala : salas){
                            System.out.println("SALA " + contador);
                            System.out.println(sala.exibirSala());
                            contador++;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Nao foi possivel enviar a mensagem");
                }
	}
	
	public static void main(String [] args) {
		new Thread( new ThreadEnviaBroadcast(12345)).start();
	}
	
}
