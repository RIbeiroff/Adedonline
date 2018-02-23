package com.example.jadso.adedonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.jadso.adedonline.Model.Sala;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class SalaIniciarActivity extends AppCompatActivity {
    private Button btnAtualizar, btnIniciar;
    private ListView listView1;
    public ArrayList<String> participantes = new ArrayList<String>();
    public ArrayAdapter<String> endereco_dos_participantes;
    public static Sala sala;


    static ArrayList<Integer> numerosSorteados = new ArrayList();

    public char sortearLetra () {
        if (numerosSorteados.size() < 26){
            Random generator = new Random();
            int numeroSorteado =  generator.nextInt(25);
            char[] alfabeto = new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

            while (true){
                if (!numerosSorteados.contains(numeroSorteado)){
                    numerosSorteados.add(numeroSorteado);
                    return alfabeto[numeroSorteado];
                } else
                    numeroSorteado++;

                if (numeroSorteado == 26)
                    numeroSorteado = 0;
            }
        }
        return '-';
    }

    public void atualizaListView() {
        participantes.clear();

        for (Socket participante : sala.participantes){
            participantes.add(participante.getInetAddress().toString());
        }

        if (endereco_dos_participantes == null) {
            endereco_dos_participantes = new ArrayAdapter(this, android.R.layout.simple_list_item_1, participantes);
            listView1.setAdapter(endereco_dos_participantes);
            System.out.println("Passei pelo if");
        } else {
            endereco_dos_participantes.notifyDataSetChanged();
            System.out.println("Passei pelo else");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_iniciar);

        listView1 = (ListView) findViewById(R.id.listView1);
        btnAtualizar = (Button) findViewById(R.id.btnAtualizar);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);

        //Inicializando ArrayAdapter
       // endereco_dos_participantes = new ArrayAdapter(this, android.R.layout.simple_list_item_1, participantes);

        Intent intent = getIntent();
        this.sala = (Sala) intent.getSerializableExtra("Sala");

        int porta = 12345;

        //Iniciar a thread responsavel por ouvir os broadcast da rede
        new Thread( new com.example.jadso.adedonline.Controller.Servidor.ThreadRecebeBroadcast(this.sala, porta)).start();

        try {
            new Thread( new com.example.jadso.adedonline.Controller.Servidor.ThreadGerenciamentoServidor(this.sala, porta, listView1, endereco_dos_participantes, participantes)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }


        btnAtualizar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                atualizaListView();
            }
        });

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //Sortea Letra
                 char letraSorteada = sortearLetra();

                 System.out.println("Letra Sorteada: " + letraSorteada);

                 //Enviar a letra sorteada e as categorias para montar o quadro de respostas
                 Intent intent = new Intent(SalaIniciarActivity.this, SalaLetraSorteadaServidorActivity.class);
                 intent.putExtra("LetraSorteada", letraSorteada);
                 startActivity(intent);

                 //Dispara Thread que envia letra aos participantes
                 new Thread(new com.example.jadso.adedonline.Controller.Servidor.ThreadEnviaLetraSorteada(sala.getParticipantes(), letraSorteada)).start();

                 //Chama a tela de visualizar a letra sorteada
            }
        });
    }
}
