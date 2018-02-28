package com.example.jadso.adedonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jadso.adedonline.Controller.Cliente.AsyncTaskEnviaBroadcast;
import com.example.jadso.adedonline.Controller.Cliente.AsyncTaskRecebeLetraServidor;
import com.example.jadso.adedonline.Controller.Cliente.ThreadConexaoServidor;
import com.example.jadso.adedonline.Model.Conexao;
import com.example.jadso.adedonline.Model.Sala;

import org.w3c.dom.Text;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class SalaLetraSorteada extends AppCompatActivity {

    public static TextView txtView1, txtView2;
    public static Button btnConfirmar;
    public static Conexao conexao = new Conexao();
    int porta = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_letra_sorteada);

        txtView1 = (TextView) findViewById(R.id.txtView1);
        txtView2 = (TextView) findViewById(R.id.txtView2);
        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);


        //Deixando os componentes invisíveis
        txtView2.setVisibility(View.GONE);
        btnConfirmar.setVisibility(View.GONE);

        final Intent intent = getIntent();
        InetAddress ip = (InetAddress) intent.getSerializableExtra("IP");
        int porta = (int) intent.getSerializableExtra("Porta");


        //Criando conexao com o servidor
        new Thread( new ThreadConexaoServidor(conexao, ip, porta, txtView1, txtView2, btnConfirmar)).start();
    }
}
