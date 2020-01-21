package com.example.sandra.proyecto0509;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login_jefe extends AppCompatActivity implements View.OnClickListener {

    private Button btn_registrado;
    private EditText TextEmail;
    private EditText TextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_jefe);

        btn_registrado= (Button) findViewById(R.id.btn_yaregistrado);
        btn_registrado.setOnClickListener(this);
        TextEmail = (EditText) findViewById(R.id.ed_email);
        TextPassword = (EditText) findViewById(R.id.et_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_yaregistrado:
                String emailSesion=TextEmail.getText().toString();
                String passwordSesion=TextPassword.getText().toString();

                if(emailSesion.equals("jefeestudios4@hotmail.com")  && passwordSesion.equals("1234567890")){
                    Intent intencion = new Intent(getApplication(), Resul_Jefe_Estudios.class);
                    startActivity(intencion);
                }
                else{
                    AlertDialog.Builder seleccionDB = new AlertDialog.Builder(Login_jefe.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    seleccionDB.setMessage("Contraseña o Usuario incorrecto");
                    seleccionDB.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog noDB = seleccionDB.create();
                    noDB.show();
                }
        }
    }
}
