package com.example.ricardomay.concursomovil2;
/*
    Numero de control:12390214
    Nombre del alumno: Noe Jesús Alpuche Villanueva
    Correo electronico: alvi619@hotmail.com
    Numero de control: 12390221
    Nombre del alumno: Ruxandra Azucely Caamal Sánchez
    Correo electronico: ruxandracammalsanchez94@gmail.com
    Numero de control: 12390230
    Nombre del alumno: Fermi Altair Durán Gorocica
    Correo electronico: fermidurn@yahoo.com.mx
    Numero de control:12390243
    Nombre del alumno: Ricardo Jesús May Chalé
    Correo electronico:Ricardo.chalheeziithoo@hotmail.com

    Aula: V09
    Semestre: 9no.
    Descripción del problema: Debido a la inseguridad sufrida en Chetumal
    se desea desarrollar una app, en la cual se brinde a los usuarios la información generale del taxi,
    así como el de su conductor, los cuales podrán ser enviados a través de un mensaje de texto al
    contacto que el usuario desee.

 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.R.*;


//Clase principal Main Activity
public class MainActivity extends AppCompatActivity {
    //Variables globales
    EditText editTextUsuario, editTextPassword;
    //Metodo encargado de inicializar la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtenemos una referencia a los controles de la interfaz
        editTextUsuario=(EditText) findViewById(R.id.editTextUsuario);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);

    }
    //llamamos al método ingresar de la clase Intent. Tiene dos parámetros de tipo String, en el primero indicamos el nombre del dato y en el segundo el valor del dato:
    public void ingresar(View v) {
        if(editTextUsuario.getText().toString().equals("admin") && editTextPassword.getText().toString().equals("admin")){
            Intent i = new Intent(this, MainSistema.class );
            //Creamos la información a pasar entre actividades
            i.putExtra("Usuario", editTextUsuario.getText().toString());
            startActivity(i);
            //Envio de mensajes en la pantalla
            Toast.makeText(getApplicationContext(),"Iniciando sistema...", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "Error de inicio de sistema...", Toast.LENGTH_SHORT).show();
        }
    }
    //llamamos al método registro de la clase Intent. Tiene dos parámetros de tipo String, en el primero indicamos el nombre del dato y en el segundo el valor del dato:
    public void registro(View v) {
        Intent i = new Intent(this, MainRegistro.class);
        startActivity(i);
    }
}
