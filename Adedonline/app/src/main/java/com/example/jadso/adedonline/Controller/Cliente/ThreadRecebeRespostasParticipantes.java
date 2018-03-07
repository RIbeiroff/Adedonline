package com.example.jadso.adedonline.Controller.Cliente;

import com.example.jadso.adedonline.SalaEntrar;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by jadso on 07/03/2018.
 */

public class ThreadRecebeRespostasParticipantes implements Runnable {

    public ThreadRecebeRespostasParticipantes(){

    }

    @Override
    public void run(){
        Socket conexao = SalaEntrar.conexao;

        try {
            DataInputStream entradaDeDados = new DataInputStream(conexao.getInputStream()); //Error Line!
            int quantidadeDeRespostas =   entradaDeDados.readInt(); //Receber a quantidade de resposta que irei receber

            for (int x = 0; x < quantidadeDeRespostas; x++){
                int tamanho = entradaDeDados.readInt();
                byte[] dados = new byte[tamanho];
                entradaDeDados.read(dados, 0, dados.length);
                System.out.println("CHEGOU A RESPOSTA NUMERO: " + x);
            }

            System.out.println("SAI DO LAÇO");
            System.out.println("CHEGOU A DE TODO MUNDO");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
