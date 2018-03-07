package com.example.jadso.adedonline;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jadso.adedonline.Controller.Cliente.ThreadRecebeRespostasParticipantes;
import com.example.jadso.adedonline.Model.Resposta;
import com.example.jadso.adedonline.Model.RespostaAdapter;

import java.util.ArrayList;

public class CorrecaoRespostaActivity extends AppCompatActivity {
    public static TextView txtTitulo;
    public static ListView listResposta;
    public static FloatingActionButton btnEnviar;
    public static RespostaAdapter respostasAdapter;
    public ArrayList<Resposta> respostas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correcao_resposta);

        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        listResposta = (ListView) findViewById(R.id.listRespostas);
        btnEnviar = (FloatingActionButton) findViewById(R.id.btnEnviar);

        //Deixando invisível
        listResposta.setVisibility(View.GONE);

        //Configurando arrayAdapter
        respostasAdapter = new RespostaAdapter(this, respostas);
        listResposta.setAdapter(respostasAdapter);

        txtTitulo.setTextSize(16);
        txtTitulo.setText("Aguardando recebimento das respostas");


        //Disparar a thread responvável por receber as respostas e setar na tela
        new Thread(new ThreadRecebeRespostasParticipantes()).start();
    }
}
