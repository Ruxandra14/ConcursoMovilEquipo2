package com.example.ricardomay.concursomovil2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

//Clase principal Main Datos
public class MainDatos extends AppCompatActivity {
    //Variables globales
    TextView textViewNumeroTaxi;
    //Metodo encargado de inicializar la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_datos);
        //Obtenemos una referencia a los controles de la interfaz
        textViewNumeroTaxi = (TextView) findViewById( R.id.textViewNumeroTaxi);
        //Creamos la información a pasar entre actividades
        Intent i=getIntent();
        Bundle extras =i.getExtras();
        if (extras != null) {//ver si contiene datos
            String datoNumero=(String)extras.get("numero");//Obtengo el nombre
            textViewNumeroTaxi.setText("No. Taxi: "+datoNumero);
        }
    }
}
