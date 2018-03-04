package com.example.jadso.adedonline;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jadso.adedonline.Controller.Servidor.ThreadDisparaStop;
import com.example.jadso.adedonline.Controller.Servidor.ThreadEscutaStop;
import com.example.jadso.adedonline.Model.Stop;
import com.example.jadso.adedonline.Model.TemasAdapter;

import java.net.Socket;
import java.util.ArrayList;

public class ResponderServidorActivity extends AppCompatActivity {
    public static ListView listViewResposta;
    public FloatingActionButton btnEnviar;
    public static TextView txtTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responder);

        final Stop stop = new Stop(); //Variável responsável pelo controle do stop

        final ArrayList<Socket> conexoes = SalaIniciarActivity.sala.participantes; //Receber as conexoes existentes

        //Escutar Stop de cada participante
        for (Socket conexao : conexoes){
            //O stop será compartilhado entre as Threads, para saber se foi disparado ou nao,
            // cada thread escutará um cliente e comunicará a outras caso tenha escutado algum stop
            new Thread( new ThreadEscutaStop(stop, conexao, conexoes) ).start();
        }

        listViewResposta = (ListView) findViewById(R.id.listViewResposta);
        btnEnviar = (FloatingActionButton) findViewById(R.id.btnEnviar);
        txtTitulo = (TextView) findViewById(R.id.txtTitulo);

        ArrayList<String> temas = (ArrayList) SalaCreateActivity.arrayTemas;
        TemasAdapter temasAdapter = new TemasAdapter(this, temas);

        listViewResposta.setAdapter(temasAdapter);

        btnEnviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar.make(view, "Stop disparado!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //Se ainda não foi disparado, eu envio o stop participantes
                if (!stop.getStop())
                    new Thread( new ThreadDisparaStop(stop, conexoes));



                /*
                int tamanhoListView = listViewResposta.getChildCount();
                View v;
                EditText edtResposta;
                ArrayList<>
                //Percorrendo o listVIew e armazenando todas as respostas
                for (int x = 0; x < tamanhoListView; x++){
                    v = listViewResposta.getChildAt(x);
                    edtResposta = (EditText) v.findViewById(R.id.edtResposta);
                    System.out.println("Resposta: " + edtResposta.getText().toString());
                }
                */
            }
        });


    }
}
