package com.example.jadso.adedonline.Controller.Cliente;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jadso.adedonline.Model.Conexao;

import java.io.IOException;

/**
 * Created by jadso on 22/02/2018.
 */

public class AsyncTaskRecebeLetraServidor extends AsyncTask<Integer, Integer, Void> {
    TextView txtTitulo, txtLetraSorteada;
    Button btnConfirmar;
    char letra;

    public AsyncTaskRecebeLetraServidor(char letra, TextView titulo, TextView letraSorteada, Button btnConfirmar) {
        this.letra = letra;
        this.txtTitulo = titulo;
        this.txtLetraSorteada = letraSorteada;
        this.btnConfirmar = btnConfirmar;
    }

    @Override
    protected Void doInBackground(Integer... params) {

        publishProgress(1);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        txtTitulo.setText("Letra Sorteada");

        txtLetraSorteada.setText((letra + "").toUpperCase());
        txtLetraSorteada.setVisibility(View.VISIBLE);

        btnConfirmar.setVisibility(View.VISIBLE);
    }

}