package com.example.jadso.adedonline.Controller.Cliente;

import com.example.jadso.adedonline.CorrecaoRespostaActivity;
import com.example.jadso.adedonline.Model.ParticipanteResposta;
import com.example.jadso.adedonline.SalaEntrar;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jadso on 07/03/2018.
 */

public class ThreadEnviaRespostasCorrigidasServidor implements Runnable {

    public ThreadEnviaRespostasCorrigidasServidor(){

    }

    @Override
    public void run(){

        Socket conexao = SalaEntrar.conexao;

        if (conexao != null) {
            try {
                DataOutputStream saida_de_dados = new DataOutputStream(conexao.getOutputStream());
                saida_de_dados.writeInt(CorrecaoRespostaActivity.respostasCorrigidas.size());

                for (ParticipanteResposta respostaCorrigida : CorrecaoRespostaActivity.respostasCorrigidas){
                    saida_de_dados.writeInt(respostaCorrigida.getId());
                    byte[] dados = convertObjectToByteArray(respostaCorrigida.getResposta());
                    saida_de_dados.writeInt(dados.length);
                    saida_de_dados.write(dados, 0, dados.length);
                }

                DataInputStream entrada_de_dados = new DataInputStream(conexao.getInputStream());
                int pontuacao  = entrada_de_dados.readInt();
                AsyncTaskMostraPontuacao atualiza = new AsyncTaskMostraPontuacao(pontuacao);
                atualiza.execute(1);

            } catch (IOException e) {
                e.printStackTrace();
            }
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
