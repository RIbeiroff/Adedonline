package com.example.jadso.adedonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InserirTemaActivity extends AppCompatActivity {

    EditText edtTema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_tema);

        edtTema = (EditText) findViewById(R.id.edtTema);

        Button btnInserir = (Button) findViewById(R.id.btnInserir);
        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTema.getText().toString().equals("")) {
                    Toast.makeText(getBaseContext(), "Informe um Tema", Toast.LENGTH_SHORT).show();
                } else {
                    SalaCreateActivity.arrayTemas.add(edtTema.getText().toString());
                    finish();
                }
            }
        });
    }
}
