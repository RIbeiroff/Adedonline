package com.example.jadso.adedonline;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jadso.adedonline.Controller.Cliente.ThreadEnviaResposta;
import com.example.jadso.adedonline.Controller.Servidor.ThreadDisparaStop;
import com.example.jadso.adedonline.Controller.Servidor.ThreadEscutaStop;
import com.example.jadso.adedonline.Controller.Servidor.ThreadVerficaChegadaResposta;
import com.example.jadso.adedonline.Model.ParticipanteResposta;
import com.example.jadso.adedonline.Model.Stop;
import com.example.jadso.adedonline.Model.TemasAdapter;

import java.net.Socket;
import java.util.ArrayList;

public class ResponderServidorActivity extends AppCompatActivity {
    public static ListView listViewResposta;
    public static FloatingActionButton btnEnviar;
    public static FloatingActionButton btnEnviarResposta;
    public static TextView txtTitulo;
    public static ArrayList<ParticipanteResposta> respostas_participantes = new ArrayList();

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
        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        btnEnviar = (FloatingActionButton) findViewById(R.id.btnEnviar);
        btnEnviarResposta = (FloatingActionButton) findViewById(R.id.btnEnviarResposta);

        btnEnviarResposta.setVisibility(View.GONE);

        ArrayList<String> temas = (ArrayList) SalaCreateActivity.arrayTemas;
        TemasAdapter temasAdapter = new TemasAdapter(this, temas);

        listViewResposta.setAdapter(temasAdapter);

        new Thread( new ThreadVerficaChegadaResposta()).start(); //Verfica se chegaram todas as respostas dos participantes

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Stop disparado!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //Se ainda não foi disparado, eu envio o stop participantes
                System.out.println("Condicao do stop: " + stop.getStop());
                if (!stop.getStop()) {
                    System.out.println("Stop disparado");
                    new Thread(new ThreadDisparaStop(stop, conexoes)).start();
                }

                btnEnviar.setVisibility(View.GONE); //Desativar depois
                btnEnviarResposta.setVisibility(View.VISIBLE); //Desativar depois

            }
        });

        btnEnviarResposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Enviada suas mensagens ao servidor!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                /*
                Código para percorrer os edit text do list view e armazenar o texto de cada um
                  */
                int tamanhoListView = listViewResposta.getChildCount();
                View v;
                EditText edtResposta;

                ArrayList<String> respostas = new ArrayList<>();

                //Percorrendo o listVIew e armazenando todas as respostas
                for (int x = 0; x < tamanhoListView; x++){
                    v = listViewResposta.getChildAt(x);
                    edtResposta = (EditText) v.findViewById(R.id.edtResposta);
                    String resposta = edtResposta.getText().toString();
                    respostas.add(resposta);
                }

                respostas_participantes.add( new ParticipanteResposta(null, respostas));
                Intent intent = new Intent(ResponderServidorActivity.this, CorrecaoRespostaServidorActivity.class);
                startActivity(intent);
            }
        });
    }
}
