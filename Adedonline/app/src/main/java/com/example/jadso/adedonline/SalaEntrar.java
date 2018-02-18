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

import java.net.DatagramPacket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Created by jadso on 12/02/2018.
 */

public class SalaEntrar extends AppCompatActivity {
    private TextView txtView1;
    public static ListView listView1;
    public static ArrayList<String> nome_das_salas = new ArrayList<>();
    public static ArrayAdapter nomes_das_salas_Adapter;
    public static ArrayList<DatagramPacket> pacotes_servidores = new ArrayList<DatagramPacket>();

    public void atualizaListView() {
        if (nomes_das_salas_Adapter == null) {
            nomes_das_salas_Adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nome_das_salas);
            listView1.setAdapter(nomes_das_salas_Adapter);
        } else
            nomes_das_salas_Adapter.notifyDataSetChanged();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_sala);

        nomes_das_salas_Adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nome_das_salas);

        txtView1 = (TextView) findViewById(R.id.txtView1);
        listView1 = (ListView) findViewById(R.id.listView1);
//        final ArrayAdapter<String> arrayAdapterNomesDasSalas = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView1.setAdapter(nomes_das_salas_Adapter);

        final Thread t1 = new Thread( new com.example.jadso.adedonline.Controller.Cliente.ThreadEnviaBroadcast
                        (12345, nome_das_salas, pacotes_servidores));

        t1.start();

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
                        Toast.makeText(SalaEntrar.this, "Clicou na sala " + pacotes_servidores.get(position).getAddress(), Toast.LENGTH_SHORT).show();
                    }
                });

                /*
                dlg.setNeutralButton("Sim, eu desejo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SalaEntrar.this, "Clicou na sala " + pacotes_servidores.get(position).getAddress(), Toast.LENGTH_SHORT).show();
                    };
                });

                dlg.setNeutralButton("Cancelar", null);
                */

                dlg.show();
            }
        });

    }
}
