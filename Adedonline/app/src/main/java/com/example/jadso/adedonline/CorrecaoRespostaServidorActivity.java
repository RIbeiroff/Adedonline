package com.example.jadso.adedonline;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jadso.adedonline.Model.ParticipanteResposta;
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
    public static ArrayList<Resposta> respostas = new ArrayList<>();
    //Minhas respostas para corrigir
    public static ArrayList<ParticipanteResposta> respostas_para_eu_corrigir = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correcao_resposta);

        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        listResposta = (ListView) findViewById(R.id.listRespostas);
        btnEnviar = (FloatingActionButton) findViewById(R.id.btnEnviar);

        btnEnviar.setVisibility(View.GONE);

        //Deixando invisível
        listResposta.setVisibility(View.GONE);

        //Configurando arrayAdapter
        respostasAdapter = new RespostaAdapter(this, respostas);
        listResposta.setAdapter(respostasAdapter);

        txtTitulo.setTextSize(16);
        txtTitulo.setText("Aguardando recebimento das respostas");


        btnEnviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View view){
                int tamanhoListView = listResposta.getChildCount();
                View v;

                TextView txtNome;
                RadioGroup rdGroup;
                RadioButton rdButton;

                //ArrayList<Resposta> respostas = new ArrayList<>();
                ArrayList<String> respostas = new ArrayList<>();

                //Percorrendo o listVIew e armazenando todas as respostas
                for (int x = 0; x < tamanhoListView; x++){
                    v = listResposta.getChildAt(x);
                    rdGroup = (RadioGroup) v.findViewById(R.id.rdGroup);
                    int id = rdGroup.getCheckedRadioButtonId();
                    rdButton = (RadioButton) rdGroup.findViewById(id);
                    String resposta = rdButton.getText().toString();
                    respostas.add(resposta);
                }

                for (String resposta : respostas){
                    System.out.println(resposta);
                }
            }
        });
    }

}
