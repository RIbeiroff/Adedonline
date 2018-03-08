package com.example.jadso.adedonline.Controller.Cliente;

import android.widget.Button;
import android.widget.TextView;

import com.example.jadso.adedonline.Model.Conexao;
import com.example.jadso.adedonline.SalaEntrar;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jadso on 22/02/2018.
 * Thread responsável por fazer a conexão com o servidor, e em seguida, recebe um ArrayList<String> contendo os temas da sala
 */

public class ThreadConexaoServidor implements Runnable {
    Socket conexao;
    InetAddress ip;
    int porta;
    ArrayList<String> temas;

    public ThreadConexaoServidor(Socket conexao, InetAddress ip, int porta, ArrayList<String> temas){
        this.conexao = conexao;
        this.ip = ip;
        this.porta = porta;
    }

    @Override
    public void run(){
        try {
            Socket socketConexao = new Socket(ip, porta); //Conexao com o servidor
            this.conexao = socketConexao;
            SalaEntrar.conexao = socketConexao;
            System.out.println("Condiceos");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Recebendo temas do servidor
        try {

            DataInputStream objectInput = new DataInputStream(conexao.getInputStream());
            int tamanho = objectInput.readInt(); //Recebendo o tamanho do byte tamanho que irei receber
            byte[] dados = new byte[tamanho]; //Crio de acordo com o tamanho recebido
            objectInput.read(dados, 0, dados.length); //Atribuo a leitura ao byteArray dados
            this.temas = convertByteArrayToObject(dados); //Método responsável pela conversão de byteArray em ArrayList<String>
            SalaEntrar.temas_da_sala_escolhida = this.temas; //Setando o array de temas da activity
            int contador = 0;
            while (contador < this.temas.size()){
                System.out.println(temas.get(contador));
                contador++;
            }

        new Thread( new ThreadRecebeDadosServidor(SalaEntrar.conexao, SalaEntrar.pacotes_servidores.get(SalaEntrar.sala_escolhida), this.porta)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

//      AsyncTaskRecebeLetraServidor asyncTaskAguardaLetraSorteada = new AsyncTaskRecebeLetraServidor(letraSorteada, this.titulo, this.letraSorteada, btnConfirmar);
  //      asyncTaskAguardaLetraSorteada.execute(1);

    }



    public static ArrayList<String> convertByteArrayToObject(byte[] bytes) {
        ArrayList<String> temas = new ArrayList();

        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (ArrayList<String>) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}
