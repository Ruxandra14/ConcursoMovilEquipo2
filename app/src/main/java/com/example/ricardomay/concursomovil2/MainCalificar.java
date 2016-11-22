package com.example.ricardomay.concursomovil2;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

//Clase principal Main Calificar
public class MainCalificar extends ActionBarActivity {
    //Variables globales
    RatingBar ratingBar;
    TextView rateMessage;
    String ratedValue;
    //Metodo encargado de inicializar la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_calificar);
        //Obtenemos una referencia a los controles de la interfaz
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        rateMessage = (TextView) findViewById(R.id.rateMessage);
        //ratingBar nos regresa el valor de la calificacion de las estrellas
        ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                ratedValue = String.valueOf(ratingBar.getRating());
                rateMessage.setText("You have rated the Product : "
                        + ratedValue + "/5.");
            }
        });
    }
}
