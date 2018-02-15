package com.example.jadso.adedonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AlterarTemaActivity extends AppCompatActivity {

    EditText edtAlterar;
    Button btnAlterar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_tema);

        edtAlterar = (EditText) findViewById(R.id.edtAlterarTema);
        Intent intent = getIntent();
        final int item = (int) intent.getSerializableExtra("item");
        String tema = (String) SalaCreateActivity.arrayTemas.get(item).toString();

        edtAlterar.setText(tema);

        btnAlterar = (Button) findViewById(R.id.btnAlterar);
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtAlterar.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Informe um Tema", Toast.LENGTH_SHORT).show();
                }else {
                    SalaCreateActivity.arrayTemas.set(item, edtAlterar.getText().toString());
                    SalaCreateActivity.adapterTemas.notifyDataSetChanged();
                    finish();
                }
            }
        });
    }
}
