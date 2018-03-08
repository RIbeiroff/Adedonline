package com.example.jadso.adedonline.Controller.Servidor;

import android.icu.text.RelativeDateTimeFormatter;

import com.example.jadso.adedonline.CorrecaoRespostaServidorActivity;
import com.example.jadso.adedonline.Model.Correcao;
import com.example.jadso.adedonline.Model.ParticipanteResposta;
import com.example.jadso.adedonline.ResponderServidorActivity;
import com.example.jadso.adedonline.SalaIniciarActivity;

import java.util.ArrayList;

/**
 * Created by jadso on 07/03/2018.
 */

public class ThreadAguardaSubmissaoCorrecoes implements Runnable {

    public ThreadAguardaSubmissaoCorrecoes() {

    }

    @Override
    public void run() {
        int total_participantes = SalaIniciarActivity.sala.getParticipantes().size() + 1;
        int quant_correcoes_totais = total_participantes * (total_participantes - 1);

        while (CorrecaoRespostaServidorActivity.respostasCorrigidas.size() < quant_correcoes_totais) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("FOI RECEBIDA TODAS AS QUESTOES CORRIGIDAS DE TODOS OS PARTICIPANTES CARALHO");



        int quantidade_categoria = SalaIniciarActivity.sala.categorias.size();

        ArrayList<Integer> id_corrigidos = new ArrayList<>();

        ArrayList<Correcao> pontuacao_participantes = new ArrayList<>();

        //Verificando se a resposta é válida
        for (ParticipanteResposta correcao : CorrecaoRespostaServidorActivity.respostasCorrigidas) {
            int id = correcao.id;

            boolean corrigido = false;

            for (Integer corrigidos : id_corrigidos) {
                if (corrigidos == id)
                    corrigido = true;
            }

            if (!corrigido) {
                id_corrigidos.add(id);
                int pontuacao = 0;
                int valido = 0, invalido = 0;
                Correcao participantePontuacao = new Correcao();
                participantePontuacao.id = id;
                participantePontuacao.pontuacao = 0;

                for (int x = 0; x < quantidade_categoria; x++) {
                    int categoria = x;

                    for (ParticipanteResposta correcoes : CorrecaoRespostaServidorActivity.respostasCorrigidas) {
                        if (correcoes.id == id) {
                            if (correcoes.resposta.get(categoria).compareTo("Válido") == 0)
                                valido++;
                            else
                                invalido++;
                        }
                    }

                    if (invalido > valido)
                        pontuacao = 0;
                    else {
                        String minha_resposta = "";
                        //Verificando se a resposta é repetida
                        for (ParticipanteResposta respostas_dos_participantes : ResponderServidorActivity.respostas_participantes) {
                            if (respostas_dos_participantes.id == id) {
                                minha_resposta = respostas_dos_participantes.resposta.get(categoria);
                            }
                        }

                        boolean flag = false;
                        for (ParticipanteResposta respostas_dos_participantes : ResponderServidorActivity.respostas_participantes) {
                            if (respostas_dos_participantes.id != id)
                                if (respostas_dos_participantes.resposta.get(categoria).compareTo(minha_resposta) == 0)
                                    flag = true;

                        }

                        if (!flag)
                            pontuacao = 10;
                        else
                            pontuacao = 5;

                    }

                    //Agora já sei a pontuacao do cara e o seu id
                    participantePontuacao.pontuacao = participantePontuacao.pontuacao + pontuacao;
                }
                pontuacao_participantes.add(participantePontuacao);
            }
        }


        //EXIBIR PONTUACAO DE CADA UM DOS PARTICIPANTES
        for (Correcao pontuacaoToatal : pontuacao_participantes){
            System.out.println(pontuacaoToatal.id + "  " + pontuacaoToatal.pontuacao);
        }



    }
}
