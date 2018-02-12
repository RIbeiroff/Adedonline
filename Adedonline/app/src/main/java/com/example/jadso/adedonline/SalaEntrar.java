package com.example.jadso.adedonline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jadso on 12/02/2018.
 */

public class SalaEntrar extends AppCompatActivity {
    private TextView txtView1;
    private ListView listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_sala);

        txtView1 = (TextView) findViewById(R.id.txtView1);
        listView1 = (ListView) findViewById(R.id.listView1);

        new Thread( new com.example.jadso.adedonline.Controller.Cliente.ThreadEnviaBroadcast
                        (12345, listView1, new ArrayAdapter<String>(this, android.R.layout.activity_list_item))).start();


        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }
}
