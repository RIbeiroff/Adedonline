package com.example.jadso.adedonline.Controller.Servidor;

import com.example.jadso.adedonline.CorrecaoRespostaServidorActivity;
import com.example.jadso.adedonline.Model.ParticipanteResposta;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jadso on 07/03/2018.
 */

public class ThreadRecebeCorrecaoRespostas implements Runnable {

    Socket conexao;

    public ThreadRecebeCorrecaoRespostas(Socket conexao){
        this.conexao = conexao;
    }

    @Override
    public void run() {
        if (conexao != null) {
            try {
                DataInputStream entrada_de_dados = new DataInputStream(conexao.getInputStream());
                int quantidade = entrada_de_dados.readInt();

                for (int x = 0; x < quantidade; x++){
                    int id = entrada_de_dados.readInt();
                    int tamanho = entrada_de_dados.readInt();
                    byte[] dados = new byte[tamanho];
                    entrada_de_dados.read(dados, 0, dados.length);
                    ArrayList<String> correcoes = new ArrayList<>();
                    correcoes = convertByteArrayToArrayString(dados);
                    CorrecaoRespostaServidorActivity.respostasCorrigidas
                            .add(new ParticipanteResposta(id, null, correcoes));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static ArrayList<String> convertByteArrayToArrayString(byte[] bytes) {
        ArrayList<String> respostas = new ArrayList();

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
