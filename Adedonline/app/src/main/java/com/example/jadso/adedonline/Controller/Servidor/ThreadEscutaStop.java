package com.example.jadso.adedonline.Controller.Servidor;

import com.example.jadso.adedonline.Controller.Cliente.AsyncTaskDesativaResposta;
import com.example.jadso.adedonline.Model.Conexao;
import com.example.jadso.adedonline.Model.ParticipanteResposta;
import com.example.jadso.adedonline.Model.Stop;
import com.example.jadso.adedonline.ResponderClienteActivity;
import com.example.jadso.adedonline.ResponderServidorActivity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jadso on 04/03/2018.
 */

public class ThreadEscutaStop implements Runnable {

    public static Stop condicaoStop;
    Socket conexao;
    ArrayList<Socket> todasConexoes;

    public ThreadEscutaStop(Stop condicaoStop, Socket conexao, ArrayList<Socket> todasConexoes){
        this.condicaoStop = condicaoStop;
        this.conexao = conexao;
        this.todasConexoes = todasConexoes;
    }

    @Override
    public void run(){
        try {
            //Ouvindo STOP
            System.out.println("Agurdando stop de : " + conexao.getPort());
            BufferedReader entradaDeDados = new BufferedReader(new InputStreamReader(this.conexao.getInputStream()));
            String stop = entradaDeDados.readLine();

            AsyncTaskDesativaResposta asyncTaskDesativaResposta = new AsyncTaskDesativaResposta(ResponderServidorActivity.listViewResposta,
                                                                                                ResponderServidorActivity.btnEnviar,
                                                                                                ResponderServidorActivity.btnEnviarResposta);
            asyncTaskDesativaResposta.execute(1);

            System.out.println("Recebido pacote de stop");

            //Se ninguém disparou o stop anteriormente, então vou percorrer todas as conexões e disparar o stop
            if (!condicaoStop.getStop()){
                condicaoStop.alteraCondicao(); //O STOP agora foi disparado

                for (Socket participante : this.todasConexoes){
                    //Enviando o STOP a cada uma delas, mas para o autor do stop não deve retornar uma confirmacao
                    DataOutputStream saidaDeDados = new DataOutputStream(participante.getOutputStream());
                    if (participante != this.conexao) {
                        saidaDeDados.writeBytes("stop" + '\n');
                        saidaDeDados.flush();
                    } else {
                        saidaDeDados.writeBytes("confirmadostop" + '\n');
                        saidaDeDados.flush();
                    }
                }
            }

            System.out.println("Escuta encerrada");

            //DEPOIS DO STOP AGUARDA O ARRAYDERESPOSTA DOS PARTICIPANTES
            DataInputStream objectInput = null; //Error Line!

            objectInput = new DataInputStream(conexao.getInputStream());
            int tamanho_do_array = objectInput.readInt(); //Recebo o tamanho do array

            byte[] respostas_recebidas = new byte[tamanho_do_array]; //Crio um vetor de byte com o tamanho recebido
            objectInput.read(respostas_recebidas, 0, respostas_recebidas.length); //leio os dados e insiro no array

            ArrayList<String> respostas = new ArrayList();
            respostas = convertByteArrayToObject(respostas_recebidas); //Resposta do usuário
            ResponderServidorActivity.respostas_participantes.add( new ParticipanteResposta(conexao.getPort(), conexao, respostas)); //Armazenando a resposta do participante

            System.out.println("RESPOSTA RECEBIBA");
        } catch (IOException ex) {
            Logger.getLogger(ThreadEscutaStop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<String> convertByteArrayToObject(byte[] bytes) {
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
