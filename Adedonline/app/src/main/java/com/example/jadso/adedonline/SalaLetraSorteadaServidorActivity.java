package com.example.jadso.adedonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jadso.adedonline.Model.Sala;

public class SalaLetraSorteadaServidorActivity extends AppCompatActivity {

    public Sala sala;
    public static TextView txtView1, txtView2;
    public Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_letra_sorteada_servidor);

        txtView1 = (TextView) findViewById(R.id.textView1);
        txtView2 = (TextView) findViewById(R.id.textView2);
        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);

       Intent intent = getIntent();
       char letraSorteada = (char) intent.getSerializableExtra("LetraSorteada");

        sala = SalaIniciarActivity.sala;

        txtView2.setText((letraSorteada + " ").toUpperCase() ) ;

        System.out.println(sala.exibirSala());

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalaLetraSorteadaServidorActivity.this, ResponderActivity.class);
                startActivity(intent);
            }
        });
    }
}
