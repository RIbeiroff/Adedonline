package com.example.jadso.adedonline.Controller.Cliente;

import android.os.AsyncTask;
import android.view.View;

import com.example.jadso.adedonline.PontuacaoRodadaActivity;

/**
 * Created by jadso on 08/03/2018.
 */

public class AsyncTaskMostraPontuacao extends AsyncTask<Integer, Integer, Void> {
    int pontuacao;

    public AsyncTaskMostraPontuacao(int pontuacao){
        this.pontuacao = pontuacao;
    }

    @Override
    protected Void doInBackground(Integer... params) {

        publishProgress(1);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        PontuacaoRodadaActivity.txtTitulo.setText("Pontuacao");
        PontuacaoRodadaActivity.txtPontuacao.setText(pontuacao + " ");
        PontuacaoRodadaActivity.txtPontuacao.setVisibility(View.VISIBLE);
        PontuacaoRodadaActivity.btnConfirmar.setVisibility(View.VISIBLE);
    }

    }
