package com.example.ricardomay.concursomovil2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

//Clase principal Main Sistema
public class MainSistema extends AppCompatActivity {
    //Variables globales
    TextView textViewUsuario;
    EditText editTextNumeroTaxi;
    //Metodo encargado de inicializar la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sistema);
        //Obtenemos una referencia a los controles de la interfaz
        textViewUsuario = (TextView) findViewById( R.id.textViewUsuario );
        //Creamos la información a pasar entre actividades
        Intent i=getIntent();
        Bundle extras =i.getExtras();
        if (extras != null) {//ver si contiene datos
            String datoNombre=(String)extras.get("Usuario");//Obtengo el nombre

            textViewUsuario.setText(datoNombre);
        }
        //Obtenemos una referencia a los controles de la interfaz
        editTextNumeroTaxi = (EditText) findViewById(R.id.editTextNumeroTaxi);
    }
    //llamamos al método verdatos de la clase Intent. Tiene dos parámetros de tipo String, en el primero indicamos el nombre del dato y en el segundo el valor del dato:
    public void verdatos(View view){
        Intent i = new Intent(this, MainDatos.class );
        i.putExtra("numero", editTextNumeroTaxi.getText().toString());
        startActivity(i);
    }
    //llamamos al método enviarmensaje de la clase Intent. Tiene dos parámetros de tipo String, en el primero indicamos el nombre del dato y en el segundo el valor del dato:
    public void enviarmensaje(View view){
        Intent i = new Intent(this, MainMensaje.class );
        i.putExtra("numero", editTextNumeroTaxi.getText().toString());
        startActivity(i);
    }
    //llamamos al método tarifa de la clase Intent. Tiene dos parámetros de tipo String, en el primero indicamos el nombre del dato y en el segundo el valor del dato:
    public void tarifa(View view){
        Intent i = new Intent (this, MainTarifa.class);
        startActivity(i);
    }
    //llamamos al método calificar de la clase Intent. Tiene dos parámetros de tipo String, en el primero indicamos el nombre del dato y en el segundo el valor del dato:
    public void calificar(View view){
        Intent i = new Intent (this, MainCalificar.class);
        startActivity(i);
    }
}
