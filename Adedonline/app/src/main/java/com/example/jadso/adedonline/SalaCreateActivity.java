package com.example.jadso.adedonline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SalaCreateActivity extends AppCompatActivity {

    public static List<String> arrayTemas = new ArrayList<>();
    EditText edtNomeSala;
    ListView listaTemas;
    Button btnAdicionarTema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtNomeSala = (EditText) findViewById(R.id.edtNomeSala);
        listaTemas = (ListView) findViewById(R.id.lvTemas);

        listaTemas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayTemas));

        btnAdicionarTema = (Button) findViewById(R.id.btnAdicionarTema);

        btnAdicionarTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalaCreateActivity.this, InserirTemaActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

}
