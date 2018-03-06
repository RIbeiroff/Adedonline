package com.example.jadso.adedonline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.jadso.adedonline.Model.Sala;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SalaCreateActivity extends AppCompatActivity {

    public static List<String> arrayTemas = new ArrayList<>();
    EditText edtNomeSala, edtQtdeRodadas;
    ListView listaTemas;
    Button btnAdicionarTema, btnConcluir;
    public static ArrayAdapter adapterTemas;
    public int porta = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtNomeSala = (EditText) findViewById(R.id.edtNomeSala);
        edtQtdeRodadas = (EditText) findViewById(R.id.edtQtdeRodadas);
        listaTemas = (ListView) findViewById(R.id.lvTemas);
        btnAdicionarTema = (Button) findViewById(R.id.btnAdicionarTema);
        btnConcluir = (Button) findViewById(R.id.btnConcluir);

        adapterTemas = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayTemas);


        listaTemas.setAdapter(adapterTemas);
        listaTemas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =  new Intent(SalaCreateActivity.this, AlterarTemaActivity.class);
                intent.putExtra("item", i);
                startActivity(intent);
            }
        });


        btnAdicionarTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalaCreateActivity.this, InserirTemaActivity.class);
                startActivity(intent);
            }
        });

        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                //Fazer tratamento para testar se todos os campos estão nukos

               //Crianção do objeto sala
                Sala sala = new Sala();
                sala.nome = edtNomeSala.getText().toString();
                sala.totalRodadas =  Integer.parseInt(edtQtdeRodadas.getText().toString());
                sala.categorias.addAll(arrayTemas.subList(0, arrayTemas.size()));



                Intent intent = new Intent(SalaCreateActivity.this, SalaIniciarActivity.class);
                intent.putExtra("Sala", sala);
                startActivity(intent);
            }
        });

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });*/
    }

    public void onResume() {
        super.onResume();

    }

}
