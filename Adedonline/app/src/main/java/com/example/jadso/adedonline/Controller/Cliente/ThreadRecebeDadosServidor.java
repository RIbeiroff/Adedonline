package com.example.jadso.adedonline.Controller.Cliente;

import android.content.Intent;

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
    DataOutputStream saidaDeDados;
    BufferedReader entradaDeDados;
    char letraSorteada;

    public ThreadRecebeDadosServidor(Socket conexao, DatagramPacket pacote, int porta, char letraSorteada) {
        this.pacote = pacote;
        this.porta = porta;
        this.conexao = conexao;
        conectarAoServidor();
    }

    public void conectarAoServidor(){
        if (pacote != null){
            try {
                this.conexao = new Socket(pacote.getAddress(), this.porta);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        configurarEntradaDeDados();
        configurarSaidaDeDados();
    }

    public void configurarEntradaDeDados(){
        if (conexao != null){
            try {
                this.entradaDeDados = new BufferedReader(new InputStreamReader(this.conexao.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void configurarSaidaDeDados(){
        if (this.conexao != null){
            try {
                this.saidaDeDados = new DataOutputStream(conexao.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            this.letraSorteada = entradaDeDados.readLine().charAt(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
