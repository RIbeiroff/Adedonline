package com.example.jadso.adedonline;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jadso.adedonline.Controller.Cliente.AsyncTaskEnviaBroadcast;
import com.example.jadso.adedonline.Controller.Cliente.ThreadConexaoServidor;

import java.net.DatagramPacket;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Created by jadso on 12/02/2018.
 */

public class SalaEntrar extends AppCompatActivity {
    public static TextView txtView1;
    public static ListView listView1;
    public static ArrayList<String> nome_das_salas = new ArrayList<>();
    public static ArrayList<String> temas_da_sala_escolhida = new ArrayList<>();
    public static ArrayAdapter nomes_das_salas_Adapter;
    public static ArrayList<DatagramPacket> pacotes_servidores = new ArrayList<DatagramPacket>();
    public static char letraSorteada;
    public int porta = 12345;
    public static int sala_escolhida = -1;
    public static Socket conexao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_sala);

        nomes_das_salas_Adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nome_das_salas);

        txtView1 = (TextView) findViewById(R.id.txtView1);
        listView1 = (ListView) findViewById(R.id.listView1);
        listView1.setAdapter(nomes_das_salas_Adapter);
        listView1.setVisibility(View.GONE);

        // Construtor - int porta, ArrayList<String> nomesDasSalas, ArrayList<DatagramPacket> pacotes_servidores, ArrayAdapter nomes_das_salas_Adapter, ListView listView
        AsyncTaskEnviaBroadcast servidores = new AsyncTaskEnviaBroadcast(12345, nome_das_salas, pacotes_servidores, nomes_das_salas_Adapter, listView1, txtView1);
        servidores.execute(1);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(SalaEntrar.this); //Criando AlertDialog
                dlg.setMessage("Tem certeza que deseja entrar nessa sala?"); //Setando mensagem


                dlg.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(SalaEntrar.this, "negativo=" + arg1, Toast.LENGTH_SHORT).show();
                    }
                });

                dlg.setNegativeButton("Sim, eu desejo", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(SalaEntrar.this, "Entrou na sala " + pacotes_servidores.get(position).getAddress(), Toast.LENGTH_SHORT).show();

                        //Iniciar a thread que faz conexao com o servidor e recebe os temas da sala
                        new Thread( new ThreadConexaoServidor(conexao, pacotes_servidores.get(position).getAddress(), porta, temas_da_sala_escolhida) ).start();
                        sala_escolhida = position;


                        //Iniciar a tela que visualiza a letra
                        Intent intent = new Intent(SalaEntrar.this, SalaLetraSorteada.class);
                        intent.putExtra("IP", pacotes_servidores.get(position).getAddress());
                        intent.putExtra("Porta", pacotes_servidores.get(position).getPort());
                        startActivity(intent);

                    }
                });
                dlg.show();
            }
        });
    }
}
