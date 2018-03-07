package com.example.jadso.adedonline.Model;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jadso on 07/03/2018.
 * Classe responsável por armazenar as respostas de cada conexão
 */

public class ParticipanteResposta {
    int id;
    Socket conexao;
    ArrayList<String> resposta;

    public ParticipanteResposta(Socket conexao, ArrayList<String> resposta){
        this.conexao = conexao;
        this.resposta = resposta;
    }

    public Socket getSocket(){
        return conexao;
    }

    public ArrayList<String> getResposta(){
        return resposta;
    }
}

