package com.example.jadso.adedonline.Controller.Cliente;

import android.widget.Button;
import android.widget.TextView;

import com.example.jadso.adedonline.Model.Conexao;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by jadso on 22/02/2018.
 */

public class ThreadConexaoServidor implements Runnable {
    Conexao conexao;
    InetAddress ip;
    int porta;
    TextView titulo, letraSorteada;
    Button btnConfirmar;

    public ThreadConexaoServidor(Conexao conexao, InetAddress ip, int porta, TextView titulo, TextView letraSorteada, Button btnConfirmar){
        this.conexao = conexao;
        this.ip = ip;
        this.porta = porta;
        this.titulo = titulo;
        this.letraSorteada = letraSorteada;
        this.btnConfirmar = btnConfirmar;
    }

    @Override
    public void run(){
        try {
            Socket socketConexao = new Socket(ip, porta);
            conexao.conexao = socketConexao;
            conexao.configurarEntradaDeDados();
            conexao.configurarSaidaDeDados();
            System.out.println("Condiceos");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Recebendo letra do servidor
        char letraSorteada = '-';
        try {
            letraSorteada = conexao.entradaDeDados.readLine().charAt(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

      AsyncTaskRecebeLetraServidor asyncTaskAguardaLetraSorteada = new AsyncTaskRecebeLetraServidor(letraSorteada, this.titulo, this.letraSorteada, btnConfirmar);
        asyncTaskAguardaLetraSorteada.execute(1);

    }
}
