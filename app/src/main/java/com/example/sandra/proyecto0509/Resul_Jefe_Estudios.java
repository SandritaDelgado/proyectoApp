package com.example.sandra.proyecto0509;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Resul_Jefe_Estudios extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TableLayout tabla;
    TableRow cabecera;
    TableRow separador_cabecera;
    TableRow fila;
    Spinner spinner;
    HashMap<String, Integer> meses;
    Button jefe;
    String[] groupclass = {"Elige un grupo","Grupo A", "Grupo B", "Grupo C", "Grupo D"};
    Tabla tabla2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resul__jefe__estudios);

        spinner=(Spinner)findViewById(R.id.spinnerTablaJE);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,groupclass);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        /*Spinner spinner=(Spinner)findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,groupclass);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position!=0) {
            Toast.makeText(getApplicationContext(), groupclass[position], Toast.LENGTH_SHORT).show();
            tabla2 = new Tabla(this, (TableLayout)findViewById(R.id.tabla));
            tabla2.agregarCabecera(R.array.cabecera_tabla);
            FirebaseDatabase fbd=FirebaseDatabase.getInstance();
            DatabaseReference dbr= (DatabaseReference) fbd.getReference("users");
            dbr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot datas: dataSnapshot.getChildren()){
                        //"A" es el valor que sale del spinner
                        if(datas.child("grupo").getValue().toString().compareTo(spinner.getSelectedItem().toString()) == 0){
                            for(DataSnapshot  children: datas.getChildren()){
                                if(children.getKey().compareTo("grupo") != 0) {
                                    ArrayList<String> elementos=new ArrayList<String>();




                                    elementos.add(datas.getKey().toString());
                                    String key = children.getKey();
                                    if(key.substring(0,2).equals("01")){
                                        elementos.add("Enero " + key.substring(2,6));
                                    }
                                    if(key.substring(0,2).equals("02")){
                                        elementos.add("Febrero " + key.substring(2,6));
                                    }
                                    if(key.substring(0,2).equals("03")){
                                        elementos.add("Marzo " + key.substring(2,6));
                                    }
                                    if(key.substring(0,2).equals("04")){
                                        elementos.add("Abril " + key.substring(2,6));
                                    }
                                    if(key.substring(0,2).equals("05")) {
                                        elementos.add("Mayo " + key.substring(2,6));
                                    }
                                    if(key.substring(0,2).equals("06")){
                                        elementos.add("Junio " + key.substring(2,6));
                                    }
                                    if(key.substring(0,2).equals("07")){
                                        elementos.add("Julio " + key.substring(2,6));
                                    }
                                    if(key.substring(0,2).equals("08")){
                                        elementos.add("Agosto " + key.substring(2,6));
                                    }
                                    if(key.substring(0,2).equals("09")){
                                        elementos.add("Septiembre " + key.substring(2,6));
                                    }
                                    if(key.substring(0,2).equals("10")){
                                        elementos.add("Octubre " + key.substring(2,6));
                                    }
                                    if(key.substring(0,2).equals("11")){
                                        elementos.add("Noviembre " + key.substring(2,6));
                                    }
                                    if(key.substring(0,2).equals("12")){
                                        elementos.add("Diciembre " + key.substring(2,6));
                                    }
                                    elementos.add(children.child("contadores").getValue().toString());
                                    tabla2.agregarFilaTabla(elementos);
                                }
                            }

                        }

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}