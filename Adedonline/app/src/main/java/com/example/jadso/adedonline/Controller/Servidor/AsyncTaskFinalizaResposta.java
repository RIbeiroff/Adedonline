package com.example.jadso.adedonline.Controller.Servidor;

import android.os.AsyncTask;
import android.view.View;

import com.example.jadso.adedonline.Model.Stop;
import com.example.jadso.adedonline.ResponderClienteActivity;
import com.example.jadso.adedonline.ResponderServidorActivity;


/**
 * Created by jadso on 04/03/2018.
 */

public class AsyncTaskFinalizaResposta  extends AsyncTask<Integer, Integer, Void> {

    Stop stop;
    String tela;

    public AsyncTaskFinalizaResposta(Stop stop, String tela){
        this.stop = stop;
        this.tela = tela;
    }

    @Override
    protected Void doInBackground(Integer... params) {
        publishProgress(1);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        ResponderServidorActivity.txtTitulo.setText("O stop foi disparado...");
        ResponderServidorActivity.listViewResposta.setVisibility(View.GONE);
        ResponderServidorActivity.btnEnviar.setVisibility(View.GONE);
        ResponderServidorActivity.btnEnviarResposta.setVisibility(View.VISIBLE);
    }
}
