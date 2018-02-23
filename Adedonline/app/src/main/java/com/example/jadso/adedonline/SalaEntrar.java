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
    public static ArrayAdapter nomes_das_salas_Adapter;
    public static ArrayList<DatagramPacket> pacotes_servidores = new ArrayList<DatagramPacket>();
    public static char letraSorteada;
    public int porta = 12345;

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
        listView1.setVisibility(View.GONE);
        /*
        final Thread t1 = new Thread( new com.example.jadso.adedonline.Controller.Cliente.ThreadEnviaBroadcast
                        (12345, nome_das_salas, pacotes_servidores));

        t1.start();
        */


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

                        //Iniciando a thread que faz a conexao com o servidor
                        //Pametros socket, pacote do arrayList recebido do servidor, porta, char com a letra sorteada
                        //new Thread(new com.example.jadso.adedonline.Controller.Cliente.ThreadRecebeDadosServidor(conexao, pacotes_servidores.get(position),
                          //                                                                                          12345, letraSorteada)).start();

                        //Iniciar a tela que visualiza a letra
                        Intent intent = new Intent(SalaEntrar.this, SalaLetraSorteada.class);
                        intent.putExtra("IP", pacotes_servidores.get(position).getAddress());
                        intent.putExtra("Porta", porta);
                        startActivity(intent);

                        /*
                        String letra = 'a' + "";
                        if (!letra.isEmpty()){
                            System.out.println("Startei a intent");
                            intent.putExtra("LetraSorteada", letra);
                            startActivity(intent);
                        */
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
