package com.example.sandra.proyecto0509;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;



import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.sandra.proyecto0509.VariablesGlobales.contador;

public class CrearUsuarios extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private EditText TextEmail;
    private EditText TextPassword;
    private EditText name;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;
    private Button btn_registrado;
    private Spinner lista_grupos;

    private Button btn_jefe;

    ValueEventListener listener;
    String grupoclaseintent = "";
    String nombreUsu="";

    String[] groupclass = { "Elige un grupo", "Grupo A", "Grupo B", "Grupo C", "Grupo D"};

    // y quiero acceder a esta variable desde
    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbr;

    FirebaseAuth.AuthStateListener authStateListener;

    private static final Pattern PASSWORD_PATTERN= Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            //"(?=.*[a-zA-Z])" //+    //any letter
            //"(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{4,}" +               //at least 4 characters
            "$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuarios);


        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //Referenciamos los views
        TextEmail = (EditText) findViewById(R.id.ed_email);
        TextPassword = (EditText) findViewById(R.id.et_password);
        name=(EditText)findViewById(R.id.name);

        btnRegistrar = (Button) findViewById(R.id.btn_register);
        //attaching listener to button
        btnRegistrar.setOnClickListener(this);

        btn_jefe=(Button)findViewById(R.id.btn_jefe);
        btn_jefe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),Login_jefe.class);
                startActivity(i);
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Realizando registro...");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        //progressDialog.show();


        btn_registrado= (Button) findViewById(R.id.btn_yaregistrado);
        btn_registrado.setOnClickListener(this);

        lista_grupos=(Spinner) findViewById(R.id.spinner);
        lista_grupos.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,groupclass);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lista_grupos.setAdapter(aa);

        progressDialog.dismiss();
        authStateListener=new FirebaseAuth.AuthStateListener() {
                    //comprobamos el inicio y el cierre de sesion
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user=firebaseAuth.getCurrentUser();
                        if(user!=null){
                            progressDialog.show();
                            if((lista_grupos.getSelectedItem().toString() == "Elige un grupo")) {
                                if (name.getText().toString().equals("")) {
                                    FirebaseDatabase fbd = FirebaseDatabase.getInstance();
                                    dbr = (DatabaseReference) fbd.getReference("users").child(user.getUid());
                                    listener = dbr.addValueEventListener(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists() && dataSnapshot.getValue().toString() != "") {
                                                progressDialog.dismiss();
                                                String contador = dataSnapshot.child("grupo").getValue().toString();
                                                grupoclaseintent = contador;
                                                nombreUsu = dataSnapshot.child("nombre").getValue().toString();
                                                Toast.makeText(CrearUsuarios.this, "Sesion iniciada con email " + user.getEmail() + "Grupo " + grupoclaseintent + "Nombre " + nombreUsu, Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(CrearUsuarios.this, PantallaSecundaria.class);
                                                intent.putExtra("GRUPO", grupoclaseintent);
                                                intent.putExtra("NOMBRE", nombreUsu);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                            else {
                                progressDialog.dismiss();
                                grupoclaseintent = lista_grupos.getSelectedItem().toString();
                                nombreUsu=name.getText().toString();
                                Toast.makeText(CrearUsuarios.this,"Sesion iniciada con email " + user.getEmail() + "Grupo " + grupoclaseintent + "Nombre " + nombreUsu,Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(CrearUsuarios.this,PantallaSecundaria.class);
                                intent.putExtra("GRUPO",grupoclaseintent);
                                intent.putExtra("NOMBRE",nombreUsu);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                        }
                else{
                    Toast.makeText(CrearUsuarios.this,"Sesion cerrada",Toast.LENGTH_LONG).show();
                }
            }
        };
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }

    public boolean registrar(String email, String password){

        /*progressDialog.setMessage("Realizando registro...");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();*/
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                   // Toast.makeText(CrearUsuarios.this,"Se ha registrado el usuario con el email: "+ TextEmail.getText(),Toast.LENGTH_LONG).show();
                }
                else{
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                        Toast.makeText(CrearUsuarios.this, "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(CrearUsuarios.this,"No se pudo registrar el usuario la contraseña tiene que tener al menos 6 caracteres con algun numero ",Toast.LENGTH_LONG).show();
                }
                //progressDialog.dismiss();

            }
        });
        return true;
    }

    public boolean iniciarsesion(String email, String password){
        /*progressDialog.setMessage("Realizando consulta...");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();*/
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String grupoclaseintent=lista_grupos.getSelectedItem().toString();
                    String nombre=name.getText().toString();
                    //Toast.makeText(CrearUsuarios.this,"Ha iniciado sesion  el usuario con el email: "+ TextEmail.getText(),Toast.LENGTH_LONG).show();
                    Intent intencion = new Intent(getApplication(), PantallaSecundaria.class);
                    intencion.putExtra("GRUPO",grupoclaseintent);
                    intencion.putExtra("NOMBRE",nombre);
                    startActivity(intencion);
                }else{
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                        Toast.makeText(CrearUsuarios.this, "Ese usuario ya existe en la base de datos", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(CrearUsuarios.this,"No se pudo iniciar sesion el usuario no esta registrado ",Toast.LENGTH_LONG).show();
                }

            }
        });
        return true;
        //progressDialog.dismiss();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_register:
                String emailInicio=TextEmail.getText().toString();
                String passwordInicio=TextPassword.getText().toString();
                String grupoclase=lista_grupos.getSelectedItem().toString();
                String nom=name.getText().toString();

                if(emailInicio.isEmpty() || passwordInicio.isEmpty() || grupoclase.isEmpty() || nom.isEmpty()){
                    Toast.makeText(CrearUsuarios.this,"No puedes dejar ningun dato sin rellenar y pulsa REGISTRAR ",Toast.LENGTH_SHORT).show();
                }
               /* else if(emailInicio.equals("jefeestudios4@hotmail.com") &&  !passwordInicio.isEmpty()){
                    Intent intencion = new Intent(getApplication(), Pantalla_Secundaria_Jefe.class);
                    startActivity(intencion);
                }*/
                else {
                    registrar(emailInicio,passwordInicio);
                }
                break;
            case R.id.btn_yaregistrado:

                String emailSesion=TextEmail.getText().toString();
                String passwordSesion=TextPassword.getText().toString();
                String grupo=lista_grupos.getSelectedItem().toString();
                String nom2=name.getText().toString();

                /*if(emailSesion.equals("jefeestudios4@hotmail.com") &&  !passwordSesion.isEmpty()){
                    Intent intencion = new Intent(getApplication(), Pantalla_Secundaria_Jefe.class);
                    startActivity(intencion);
                }*/
                if(emailSesion.isEmpty() || passwordSesion.isEmpty() || grupo.isEmpty() || nom2.isEmpty()){
                    Toast.makeText(CrearUsuarios.this,"No dejes nada en blanco y pulsa INICIAR SESION",Toast.LENGTH_SHORT).show();
                }
                else {
                    iniciarsesion(emailSesion,passwordSesion);
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        progressDialog.dismiss();
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
        if (dbr != null && listener != null) {
            dbr.removeEventListener(listener);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position!=0) {
            Toast.makeText(getApplicationContext(), groupclass[position], Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
