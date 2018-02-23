package com.example.jadso.adedonline.Controller.Servidor;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jadso.adedonline.Model.Sala;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jadso on 23/02/2018.
 */

public class AsyncTaskAtualizaListView extends AsyncTask<Integer, Integer, Void> {
    ListView lista;
    ArrayList<String> participantes = new ArrayList();
    ArrayAdapter endereco_dos_participantes;
    Sala sala;

    public AsyncTaskAtualizaListView(int valor, ListView lista, ArrayList<String> participantes, ArrayAdapter endereco_dos_participantes, Sala sala){
        this.lista = lista;
        this.participantes = participantes;
        this.endereco_dos_participantes = endereco_dos_participantes;
        this.sala = sala;
    }


    @Override
    protected Void doInBackground(Integer... params) {
        participantes.clear();

        for (Socket participante : sala.participantes){
            participantes.add(participante.getInetAddress().toString());
        }

        publishProgress(1);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        endereco_dos_participantes.notifyDataSetChanged();
    }
}
