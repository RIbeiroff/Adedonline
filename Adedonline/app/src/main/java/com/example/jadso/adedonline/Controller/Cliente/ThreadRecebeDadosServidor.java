package com.example.jadso.adedonline.Controller.Cliente;

import android.content.Intent;

import com.example.jadso.adedonline.SalaLetraSorteada;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.Socket;

/**
 * Created by jadso on 18/02/2018.
 */

public class ThreadRecebeDadosServidor implements Runnable {

    int porta;
    DatagramPacket pacote;
    Socket conexao;
    char letraSorteada;

    public ThreadRecebeDadosServidor(Socket conexao, DatagramPacket pacote, int porta) {
        this.pacote = pacote;
        this.porta = porta;
        this.conexao = conexao;
    }

    @Override
    public void run() {
        try {

            BufferedReader entradaDeDados = new BufferedReader(new InputStreamReader(this.conexao.getInputStream()));
            this.letraSorteada = entradaDeDados.readLine().charAt(0); // Aguardando o recebimento da letra sorteada

            //Ao receber a letra sorteada starta a tarefa responsável pela atualização da tela
            //Nela será mudado o texto do título, exibe a letra recebida e exibe o botão confirmar
            SalaLetraSorteada.letraSorteada = this.letraSorteada; //Altera o char da letra sorteada da activity
            AsyncTaskRecebeLetraServidor asyncTaskLetraSorteada = new AsyncTaskRecebeLetraServidor(this.letraSorteada, SalaLetraSorteada.txtView1,
                                                                                          SalaLetraSorteada.txtView2, SalaLetraSorteada.btnConfirmar);
            asyncTaskLetraSorteada.execute(1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
