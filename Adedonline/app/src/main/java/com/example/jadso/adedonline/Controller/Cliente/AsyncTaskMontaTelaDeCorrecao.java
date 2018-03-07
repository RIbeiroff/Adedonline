package com.example.jadso.adedonline.Controller.Cliente;

import android.os.AsyncTask;
import android.view.View;

import com.example.jadso.adedonline.CorrecaoRespostaActivity;
import com.example.jadso.adedonline.CorrecaoRespostaServidorActivity;
import com.example.jadso.adedonline.Model.ParticipanteResposta;
import com.example.jadso.adedonline.Model.Resposta;
import com.example.jadso.adedonline.SalaEntrar;

/**
 * Created by jadso on 07/03/2018.
 */

public class AsyncTaskMontaTelaDeCorrecao extends AsyncTask<Integer, Integer, Void> {


    public AsyncTaskMontaTelaDeCorrecao() {

    }

    @Override
    protected Void doInBackground(Integer... params) {

        publishProgress(1);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        CorrecaoRespostaActivity.txtTitulo.setText("Valide as respostas dos outros participantes");

        CorrecaoRespostaActivity.respostas.clear();

        int contador = 0, quantidade = CorrecaoRespostaActivity.quantidadeDeRespostas;

        for (ParticipanteResposta participante_resposta : CorrecaoRespostaActivity.respostasDosParticipantes){
            for (int x = 0 ; x < participante_resposta.resposta.size(); x++){
                String tratamentoResposta  = "";
                if (participante_resposta.resposta.get(x).length() == 0)
                    tratamentoResposta = "Sem resposta";
                else
                    tratamentoResposta = participante_resposta.resposta.get(x);
                Resposta resposta = new Resposta(SalaEntrar.temas_da_sala_escolhida.get(x), tratamentoResposta);
                CorrecaoRespostaActivity.respostas.add(resposta);
            }
        }

        CorrecaoRespostaActivity.listResposta.setVisibility(View.VISIBLE);
        CorrecaoRespostaActivity.btnEnviar.setVisibility(View.VISIBLE);
        CorrecaoRespostaActivity.respostasAdapter.notifyDataSetChanged();
    }
}
