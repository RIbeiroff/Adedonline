package com.example.jadso.adedonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PontuacaoRodadaActivity extends AppCompatActivity {

    public static TextView txtTitulo;
    public static TextView txtPontuacao;
    public static Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontuacao_rodada);

        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        txtPontuacao = (TextView) findViewById(R.id.txtViwPontuacao);
        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);

        txtPontuacao.setVisibility(View.GONE);
        btnConfirmar.setVisibility(View.GONE);

    }
}
