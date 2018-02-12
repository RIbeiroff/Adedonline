package com.example.jadso.adedonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnEntrar, btnCriar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnCriar = (Button) findViewById(R.id.btnCriar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v){
                Intent intent = new Intent(MainActivity.this, SalaEntrar.class);
                startActivity(intent);
            }
        });

        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SalaCreateActivity.class);
                startActivity(intent);
            }
        });


    }
}
