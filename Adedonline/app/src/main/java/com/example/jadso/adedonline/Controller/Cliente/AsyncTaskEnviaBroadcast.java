package com.example.jadso.adedonline.Controller.Cliente;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by jadso on 21/02/2018.
 */

public class AsyncTaskEnviaBroadcast extends AsyncTask<Integer, Integer, Void> {

    public static ArrayList<String> nome_das_salas = new ArrayList<>();
    public static ArrayAdapter nomes_das_salas_Adapter;
    public static ArrayList<DatagramPacket> pacotes_servidores = new ArrayList<DatagramPacket>();
    int porta;
    ListView listView;
    TextView textView;

    public AsyncTaskEnviaBroadcast (int porta, ArrayList<String> nomesDasSalas, ArrayList<DatagramPacket> pacotes_servidores,
                                    ArrayAdapter nomes_das_salas_Adapter, ListView listView, TextView textView) {
        this.porta = porta;
        this.nome_das_salas = nomesDasSalas;
        this.pacotes_servidores = pacotes_servidores;
        this.listView = listView;
        this.nomes_das_salas_Adapter = nomes_das_salas_Adapter;
        this.textView = textView;
    }

    @Override
    protected Void doInBackground(Integer... params) {
        System.out.println("Entrou no metodo doInBack");

        try {
            DatagramSocket ds = new DatagramSocket();
            byte[] b = "servidor".getBytes(); // O servidor só responde aos broadcast que contem esse conteudo "servidor"
            InetAddress addr = InetAddress.getByName("255.255.255.255"); //Definindo o endere�o de envio do pacote neste caso o endere�o de broadcast
            DatagramPacket pkg = new DatagramPacket(b, b.length, addr,this.porta);
            ds.send(pkg);    //enviando pacote broadcast
            ds.setSoTimeout(3000); //Espero por dez segundos as respostas da rede
            try {
                while (true){
                    //Recebendo o tamanho do objeto
                    byte[] tamanho = new byte[50];
                    DatagramPacket pkg1 = new DatagramPacket(tamanho, tamanho.length, addr,this.porta); //pkg1 recebe o nome da sala
                    ds.receive(pkg1);
                    System.out.println("Pacote recebido...");
                    nome_das_salas.add(new String (pkg1.getData(), 0 , pkg1.getLength()));
                    pacotes_servidores.add(pkg1);
                }
            } catch (SocketTimeoutException ste){
                System.out.println("Tempo encerrado...");
                publishProgress(1);
            }
        } catch (Exception e) {
            System.out.println("Nao foi possivel enviar a mensagem");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        System.out.println("Entrou no metodo onProgreessUpdate");
        textView.setText("Salas Disponíveis!");
        listView.setVisibility(View.VISIBLE);
        nomes_das_salas_Adapter.notifyDataSetChanged();
    }
}
