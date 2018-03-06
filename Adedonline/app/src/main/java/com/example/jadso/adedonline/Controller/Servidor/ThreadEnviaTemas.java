package com.example.jadso.adedonline.Controller.Servidor;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jadso on 06/03/2018.
 * No momento em que o cliente faz a conexão com a sala, o servidor envia de imediato o arrayList contendo os temas
 * da sala.
 *
 */

public class ThreadEnviaTemas implements Runnable {

    Socket conexao;
    ArrayList<String> temas;

    public ThreadEnviaTemas(Socket conexao, ArrayList<String> temas){
        this.conexao = conexao;
        this.temas = temas;
    }

    @Override
    public void run(){
        DataOutputStream objectOutput = null;
        try {
            objectOutput = new DataOutputStream(conexao.getOutputStream());
            byte[] dados = convertObjectToByteArray(temas); //Conversao do ArrayList<String> (temas) em byteArray
            objectOutput.writeInt(dados.length); //Primeiro envio o tamanho a ser alocado pelo cliente para receber o byteArray
            objectOutput.write(dados); //Por segundo, envio o byteArray
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //Método responsável pela conversão do ArratList<String> em byteArray
    public static byte[] convertObjectToByteArray(ArrayList<String> temas) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(temas);
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

