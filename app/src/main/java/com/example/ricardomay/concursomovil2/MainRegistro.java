package com.example.ricardomay.concursomovil2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
//Clase secundaria Main Registro
public class MainRegistro extends AppCompatActivity {
    //Variables globales
    EditText editTextNombre, editTextApellido, editTextCorreo, editTextPassword;
    //Metodo encargado de inicializar la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registro);
        //Obtenemos una referencia a los controles de la interfaz
        editTextNombre=(EditText) findViewById(R.id.editTextNombre);
        editTextApellido=(EditText) findViewById(R.id.editTextApellido);
        editTextCorreo=(EditText) findViewById(R.id.editTextCorreo);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);
    }
    //llamamos al método ingresar de la clase Intent. Tiene dos parámetros de tipo String, en el primero indicamos el nombre del dato y en el segundo el valor del dato:
    public void Registrar(View view) {
    }
}
