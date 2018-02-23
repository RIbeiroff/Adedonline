package com.example.jadso.adedonline.Model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by jadso on 22/02/2018.
 */

public class Conexao implements Serializable {
    public Socket conexao;
    public BufferedReader entradaDeDados;
    public DataOutputStream saidaDeDados;

    public Conexao(){};

    public Conexao(Socket conexao){
        this.conexao = conexao;
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
}
