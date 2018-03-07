package com.example.jadso.adedonline.Controller.Cliente;

import com.example.jadso.adedonline.CorrecaoRespostaActivity;
import com.example.jadso.adedonline.Model.Participante;
import com.example.jadso.adedonline.Model.ParticipanteResposta;
import com.example.jadso.adedonline.SalaEntrar;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

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

            CorrecaoRespostaActivity.quantidadeDeRespostas = quantidadeDeRespostas;

            ArrayList<ParticipanteResposta> respostasParticipantes = new ArrayList<>();

            for (int x = 0; x < quantidadeDeRespostas; x++){
                int id = entradaDeDados.readInt();
                int tamanho = entradaDeDados.readInt();
                byte[] dados = new byte[tamanho];
                entradaDeDados.read(dados, 0, dados.length);
                ParticipanteResposta resposta_participante = new ParticipanteResposta(id, null, convertByteArrayToArrayString(dados));
                respostasParticipantes.add(resposta_participante);
                System.out.println("CHEGOU A RESPOSTA NUMERO: " + x);
            }

            System.out.println("CHEGOU A DE TODO MUNDO");
            CorrecaoRespostaActivity.respostasDosParticipantes = respostasParticipantes;

            AsyncTaskMontaTelaDeCorrecao atualiza_tela = new AsyncTaskMontaTelaDeCorrecao();
            atualiza_tela.execute(1);

        } catch (IOException e) {
            e.printStackTrace();
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
