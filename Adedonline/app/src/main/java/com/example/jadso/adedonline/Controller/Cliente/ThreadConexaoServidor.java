package com.example.jadso.adedonline.Controller.Cliente;

import android.widget.Button;
import android.widget.TextView;

import com.example.jadso.adedonline.Model.Conexao;

import org.w3c.dom.Text;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jadso on 22/02/2018.
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
            Socket socketConexao = new Socket(ip, porta);
            this.conexao = socketConexao;
            System.out.println("Condiceos");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Recebendo temas do servidor
        try {
            DataInputStream entradaDeDados = new DataInputStream(conexao.getInputStream());
            this.temas = entradaDeDados.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }

//      AsyncTaskRecebeLetraServidor asyncTaskAguardaLetraSorteada = new AsyncTaskRecebeLetraServidor(letraSorteada, this.titulo, this.letraSorteada, btnConfirmar);
  //      asyncTaskAguardaLetraSorteada.execute(1);

    }
}
