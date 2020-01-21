package com.example.sandra.proyecto0509;

import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Pantalla_Secundaria_Jefe extends AppCompatActivity {

    Button estadisticas_jefe, web_base_datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla__secundaria__jefe);

        estadisticas_jefe= (Button) findViewById(R.id.btn_estadisticas_jefe);
        estadisticas_jefe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion = new Intent(getApplication(), Resul_Jefe_Estudios.class);
                startActivity(intencion);
            }
        });

       /* web_base_datos= (Button) findViewById(R.id.btn_basedatos_jefe);
        web_base_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://console.firebase.google.com/project/ag21tfg/database/ag21tfg/data/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });*/

    }
}
