package com.example.jadso.adedonline;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jadso.adedonline.Controller.Cliente.ThreadDisparaStop;
import com.example.jadso.adedonline.Controller.Cliente.ThreadEscutaStop;


/**
 * Created by jadso on 04/03/2018.
 */

public class ResponderClienteActivity extends AppCompatActivity {
    public static ListView listViewResposta;
    public FloatingActionButton btnEnviar;
    public TextView txtTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responder);

        listViewResposta = (ListView) findViewById(R.id.listViewResposta);
        btnEnviar = (FloatingActionButton) findViewById(R.id.btnEnviar);
        txtTitulo = (TextView) findViewById(R.id.txtTitulo);

        //Escutando stop do servidor
        new Thread( new ThreadEscutaStop(SalaLetraSorteada.conexao.conexao)).start();

        btnEnviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar.make(view, "Stop disparado!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //Disparando stop para o servidor
                new Thread( new ThreadDisparaStop(SalaLetraSorteada.conexao.conexao)).start();
                /*
                int tamanhoListView = listViewResposta.getChildCount();
                View v;
                EditText edtResposta;
                ArrayList<>
                //Percorrendo o listVIew e armazenando todas as respostas
                for (int x = 0; x < tamanhoListView; x++){
                    v = listViewResposta.getChildAt(x);
                    edtResposta = (EditText) v.findViewById(R.id.edtResposta);
                    System.out.println("Resposta: " + edtResposta.getText().toString());
                }
                */
            }
        });

    }
}
