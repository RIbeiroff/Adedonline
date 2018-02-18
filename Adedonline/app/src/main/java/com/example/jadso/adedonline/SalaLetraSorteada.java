package com.example.jadso.adedonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.jadso.adedonline.Model.Sala;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class SalaLetraSorteada extends AppCompatActivity {

    private TextView txtView1, txtView2;
    private Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_letra_sorteada);

        txtView1 = (TextView) findViewById(R.id.txtView1);
        txtView2 = (TextView) findViewById(R.id.txtView2);
        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);

        Intent intent = getIntent();
        String LetraSorteada = (String) intent.getSerializableExtra("LetraSorteada");

        txtView2.setText(LetraSorteada.toString());
    }
}
