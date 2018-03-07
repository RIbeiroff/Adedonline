package com.example.jadso.adedonline.Controller.Servidor;

import android.os.AsyncTask;
import android.view.View;

import com.example.jadso.adedonline.CorrecaoRespostaServidorActivity;
import com.example.jadso.adedonline.Model.ParticipanteResposta;
import com.example.jadso.adedonline.Model.Resposta;
import com.example.jadso.adedonline.SalaCreateActivity;

/**
 * Created by jadso on 07/03/2018.
 */

public class AsyncTaskMontaTelaDeCorrecao extends AsyncTask<Integer, Integer, Void> {


    public AsyncTaskMontaTelaDeCorrecao(){

    }

    @Override
    protected Void doInBackground(Integer... params) {

        publishProgress(1);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        CorrecaoRespostaServidorActivity.txtTitulo.setText("Valide as respostas dos outros participantes");

        CorrecaoRespostaServidorActivity.respostas.clear();

        int contador = 0, quantidade = CorrecaoRespostaServidorActivity.respostas_para_eu_corrigir.size();

        for (ParticipanteResposta participante_resposta : CorrecaoRespostaServidorActivity.respostas_para_eu_corrigir){
            for (int x = 0 ; x < participante_resposta.resposta.size(); x++){
                String tratamentoResposta  = "";
                if (participante_resposta.resposta.get(x).length() == 0)
                    tratamentoResposta = "Sem resposta";
                else
                    tratamentoResposta = participante_resposta.resposta.get(x);
                Resposta resposta = new Resposta(SalaCreateActivity.arrayTemas.get(x), tratamentoResposta);
                CorrecaoRespostaServidorActivity.respostas.add(resposta);
            }
        }

        CorrecaoRespostaServidorActivity.listResposta.setVisibility(View.VISIBLE);
        CorrecaoRespostaServidorActivity.btnEnviar.setVisibility(View.VISIBLE);
        CorrecaoRespostaServidorActivity.respostasAdapter.notifyDataSetChanged();
    }
}
