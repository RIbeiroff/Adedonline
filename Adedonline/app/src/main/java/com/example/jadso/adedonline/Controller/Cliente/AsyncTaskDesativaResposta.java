package com.example.jadso.adedonline.Controller.Cliente;

import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;

/**
 * Created by jadso on 06/03/2018.
 * Tarefa utilizada pela activity ResponderClienteActivity/ResponderServidorActivity
 * Ambas são chamadas em ThreadEscutaStop.
 * Ele desativa o list view para preenchimento da resposta e ativa o botao de enviar a resposta
 */

public class AsyncTaskDesativaResposta extends AsyncTask<Integer, Integer, Void> {
    ListView lista;
    FloatingActionButton btnStop, btnEnviarResposta;

    public AsyncTaskDesativaResposta(ListView lista, FloatingActionButton btnStop, FloatingActionButton btnEnviarResposta){
        this.lista = lista;
        this.btnStop = btnStop;
        this.btnEnviarResposta = btnEnviarResposta;
    }

    @Override
    protected Void doInBackground(Integer... params) {
        publishProgress(1);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        lista.setVisibility(View.GONE);
        btnStop.setVisibility(View.GONE);
        btnEnviarResposta.setVisibility(View.VISIBLE);
    }
}
