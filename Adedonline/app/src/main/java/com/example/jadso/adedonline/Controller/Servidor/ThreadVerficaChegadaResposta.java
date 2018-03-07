package com.example.jadso.adedonline.Controller.Servidor;

import com.example.jadso.adedonline.CorrecaoRespostaServidorActivity;
import com.example.jadso.adedonline.Model.ParticipanteResposta;
import com.example.jadso.adedonline.ResponderServidorActivity;
import com.example.jadso.adedonline.SalaIniciarActivity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jadso on 07/03/2018.
 */

public class ThreadVerficaChegadaResposta implements Runnable {

    public ThreadVerficaChegadaResposta(){

    }

    @Override
    public void run(){

        boolean flag = false;
        while (!flag){
            try {
                Thread.sleep(3000);
                int quantidadeJogadores = SalaIniciarActivity.sala.participantes.size();
                int quantidadeResposta = ResponderServidorActivity.respostas_participantes.size();

                //Se a quantidade de resposta = (quantidade de clientes + servidor)
                if (quantidadeResposta == (quantidadeJogadores + 1))
                    flag = true;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("RECEBIDO TODAS AS RESPOSTAS");
        /* Após as respostas terem sido todas recebidas, o servidor é responsável por distribuí-las
        * Cada participante vai validar as respostas dos outros
        * */

        //Mas primeiramento eu preciso enviar a quantidade de respostas que cada cliente deverá aguardar
        int quantidadeJogadores = SalaIniciarActivity.sala.participantes.size(); //Quantidade de jogadores

        for (Socket conexao : SalaIniciarActivity.sala.participantes){ //Envio a cada participante da sala a quantidade de mensagens que ele deve aguardar
            DataOutputStream saida_de_dados = null;
            try {
                saida_de_dados = new DataOutputStream(conexao.getOutputStream());
                saida_de_dados.writeInt(quantidadeJogadores);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        //Processo de distribuição das respostas
        //Percorrer as respostas dos participantes
        for (int x = 0; x < ResponderServidorActivity.respostas_participantes.size(); x++){
            ParticipanteResposta participanteResposta =
                                new ParticipanteResposta( ResponderServidorActivity.respostas_participantes.get(x).getId(),
                                                          ResponderServidorActivity.respostas_participantes.get(x).getSocket(),
                                                          ResponderServidorActivity.respostas_participantes.get(x).getResposta());

            //Se é diferente de nulo, significa que é a resposta de um participante, sendo assim, eu armazeno para eu responder e envio aos outros participantes
            if (participanteResposta.getSocket() != null)
                CorrecaoRespostaServidorActivity.respostas_para_eu_corrigir.add(participanteResposta); //Adiciono para eu (o servidor) responder


            //Enviando a outros participantes a resposta
            for (Socket conexao : SalaIniciarActivity.sala.participantes){ //Todos os participantes da sala
                if (conexao != participanteResposta.getSocket()){ //Se este participante não for o que estou com a resposta, então eu o envio a resposta
                    try {
                        DataOutputStream saida_de_dados = new DataOutputStream(conexao.getOutputStream());
                        byte[] dados = convertObjectToByteArray(participanteResposta.getResposta());
                        saida_de_dados.writeInt(participanteResposta.getId()); //ID da resposta
                        saida_de_dados.writeInt(dados.length);
                        saida_de_dados.write(dados);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        AsyncTaskMontaTelaDeCorrecao atualiza_tela = new AsyncTaskMontaTelaDeCorrecao();
        atualiza_tela.execute(1);
    }


    public static byte[] convertObjectToByteArray(ArrayList<String> elementos) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(elementos);
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
