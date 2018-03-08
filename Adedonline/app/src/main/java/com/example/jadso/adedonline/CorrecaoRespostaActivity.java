package com.example.jadso.adedonline;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jadso.adedonline.Controller.Cliente.ThreadEnviaRespostasCorrigidasServidor;
import com.example.jadso.adedonline.Controller.Cliente.ThreadRecebeRespostasParticipantes;
import com.example.jadso.adedonline.Model.ParticipanteResposta;
import com.example.jadso.adedonline.Model.Resposta;
import com.example.jadso.adedonline.Model.RespostaAdapter;

import java.util.ArrayList;

public class CorrecaoRespostaActivity extends AppCompatActivity {
    public static TextView txtTitulo;
    public static ListView listResposta;
    public static FloatingActionButton btnEnviar;
    public static RespostaAdapter respostasAdapter;
    public static ArrayList<Resposta> respostas = new ArrayList<>();
    public static ArrayList<ParticipanteResposta> respostasDosParticipantes = new ArrayList<>();
    public static ArrayList<ParticipanteResposta> respostasCorrigidas = new ArrayList<>();
    public static int quantidadeDeRespostas = 0;


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


        //Disparar a thread responvável por receber as respostas e setar na tela
        new Thread(new ThreadRecebeRespostasParticipantes()).start();


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Cliquei");

                int tamanhoListView = listResposta.getChildCount();
                View v;

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

                System.out.println("Tamanho do array: " + respostas.size());
                int quantCategorias = SalaEntrar.temas_da_sala_escolhida.size() - 1;
                int participante = 0, contador = 0;

                ArrayList<String> correcoes = new ArrayList();

                for (String correcao : respostas){
                    correcoes.add(correcao);

                    if (contador == quantCategorias){
                        respostasCorrigidas.add(new ParticipanteResposta(respostasDosParticipantes.get(participante).id, null, correcoes));
                        correcoes.clear();
                        participante++;
                    }

                    contador++;
                }

                new Thread ( new ThreadEnviaRespostasCorrigidasServidor() ).start();

                Intent intent = new Intent(CorrecaoRespostaActivity.this, PontuacaoRodadaActivity.class);
                startActivity(intent);
            }
        });



    }
}
