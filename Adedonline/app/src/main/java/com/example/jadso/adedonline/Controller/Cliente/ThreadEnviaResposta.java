package com.example.jadso.adedonline.Controller.Cliente;

import com.example.jadso.adedonline.Model.Resposta;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jadso on 07/03/2018.
 */

public class ThreadEnviaResposta implements Runnable {

    Socket conexao;
    ArrayList<String> respostas;

    public ThreadEnviaResposta(Socket conexao, ArrayList<String> respostas){
        this.conexao = conexao;
        this.respostas = respostas;
    }

    @Override
    public void run() {
        if (conexao != null) {
            try {
                DataOutputStream saida_de_dados = new DataOutputStream(conexao.getOutputStream());
                byte[] dados = convertObjectToByteArray(respostas);
                saida_de_dados.writeInt(dados.length);
                System.out.println("Tamanho: " + dados.length);
                saida_de_dados.write(dados); //Enviando as respostas ao servidor
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static byte[] convertObjectToByteArray(ArrayList<String> respostas) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(respostas);
            objectOutputStream.flush();
            objectOutputStream.close();
            byteArrayOutputStream.close();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }
}
