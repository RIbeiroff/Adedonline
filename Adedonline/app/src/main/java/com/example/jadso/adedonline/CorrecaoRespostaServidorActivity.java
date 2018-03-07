package com.example.jadso.adedonline;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jadso.adedonline.Model.Resposta;
import com.example.jadso.adedonline.Model.RespostaAdapter;

import java.util.ArrayList;

/**
 * Created by jadso on 07/03/2018.
 */

public class CorrecaoRespostaServidorActivity extends AppCompatActivity {

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


    }

}
