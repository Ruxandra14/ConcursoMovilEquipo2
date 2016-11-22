package com.example.ricardomay.concursomovil2;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainMensaje extends AppCompatActivity {

    //variables globales para la concatenacion del mensaje
    TextView textViewContenido, textViewContenido1, textViewContenido2;
    //Código del mensaje de envío y Uri de contenido global
    public static final int PICK_CONTACT_REQUEST = 1 ;
    private Uri contactUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mensaje);

        //Obtenemos el valor del parametro que pasamos del activity anterior
        Intent i=getIntent();
        Bundle extras =i.getExtras();
        String datoNumero=(String)extras.get("numero");//Obtengo el nombre

        //obtenemos los formatos para el envio de la hora y fecha del dispositivo
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        //Creación de la cadena que se cargara en el textview de vista anticipada antes de enviar el mensaje
        textViewContenido= (TextView) findViewById(R.id.textViewContenido);
        textViewContenido.setText("Numero de taxi: "+datoNumero+ "\n" +
                "Hora de salida: "+hourFormat.format(date)+ "\n" +
                "Fecha de salida: "+dateFormat.format(date)+ "\n" +
                "Nombre del dueño: "+""+ "\n" +
                "Nombre del Conductor: "+"");

        textViewContenido1=(TextView)findViewById(R.id.textViewContenido1);
        textViewContenido2=(TextView)findViewById(R.id.textViewContenido2);

        /* Uso de la clase LocationManager para obtener la localizacion del GPS */
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainMensaje(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
                (LocationListener) Local);

        textViewContenido1.setText("Cargando ubicacion ...");//Mostrará cuando se halla encontrado una dirección
        textViewContenido2.setText("");//No  mostrará nada hasta que se tenga acceso al dispositivo GPS

    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    textViewContenido2.setText("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        MainMensaje mainMensaje;

        public MainMensaje getMainMensaje() {
            return mainMensaje;
        }

        public void setMainMensaje(MainMensaje mainMensaje) {
            this.mainMensaje = mainMensaje;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();
            String Text = "Mi ubicacion actual es: " + "\n Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();
            textViewContenido1.setText(Text);
            this.mainMensaje.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            textViewContenido1.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            textViewContenido1.setText("GPS Activado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Este metodo se ejecuta cada vez que se detecta un cambio en el
            // status del proveedor de localizacion (GPS)
            // Los diferentes Status son:
            // OUT_OF_SERVICE -> Si el proveedor esta fuera de servicio
            // TEMPORARILY_UNAVAILABLE -> Temporalmente no disponible pero se
            // espera que este disponible en breve
            // AVAILABLE -> Disponible
        }

    }




    public void initPickContacts(View v){
        /*
        Crear un intent para seleccionar un contacto del dispositivo
         */
        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        /*
        Iniciar la actividad esperando respuesta a través
        del canal PICK_CONTACT_REQUEST
         */
        startActivityForResult(i, PICK_CONTACT_REQUEST);
    }

    private void renderContact(Uri uri) {

        /*
        Obtener instancias de los Views
         */
        TextView contactName = (TextView)findViewById(R.id.contactName);
        TextView contactPhone = (TextView)findViewById(R.id.contactPhone);

        /*
        Setear valores
         */
        contactName.setText(getName(uri));
        contactPhone.setText(getPhone(uri));
        //contactPic.setImageBitmap(getPhoto(uri));
    }

    public void sendMessage(View v){

        /*
        Creando nuestro gestor de mensajes
         */
        SmsManager smsManager = SmsManager.getDefault();

        /*
        Enviando el mensaje
         */
        if(contactUri!=null) {
            smsManager.sendTextMessage(
                    getPhone(contactUri),
                    null,
                    "¡Estamos aprendiendo a Desarrrollar en Android!"+"\n"+textViewContenido.getText().toString()+"\n"+textViewContenido1.getText().toString()+"\n"+textViewContenido2.getText().toString(),
                    null,
                    null);

            Toast.makeText(this, "Mensaje Enviado", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(this, "Selecciona un contacto primero", Toast.LENGTH_LONG).show();


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                /*
                Capturar el valor de la Uri
                 */
                contactUri = intent.getData();
                /*
                Procesar la Uri
                 */
                renderContact(contactUri);
            }
        }
    }

    private String getPhone(Uri uri) {
        /*
        Variables temporales para el id y el teléfono
         */
        String id = null;
        String phone = null;

        /************* PRIMERA CONSULTA ************/
        /*
        Obtener el _ID del contacto
         */
        Cursor contactCursor = getContentResolver().query(
                uri,
                new String[]{ContactsContract.Contacts._ID},
                null,
                null,
                null);


        if (contactCursor.moveToFirst()) {
            id = contactCursor.getString(0);
        }
        contactCursor.close();

        /************* SEGUNDA CONSULTA ************/
        /*
        Sentencia WHERE para especificar que solo deseamos
        números de telefonía móvil
         */
        String selectionArgs =
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE+"= " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;

        /*
        Obtener el número telefónico
         */
        Cursor phoneCursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER },
                selectionArgs,
                new String[] { id },
                null
        );
        if (phoneCursor.moveToFirst()) {
            phone = phoneCursor.getString(0);
        }
        phoneCursor.close();

        return phone;
    }

    private String getName(Uri uri) {

        /*
        Valor a retornar
         */
        String name = null;

         /*
        Obtener una instancia del Content Resolver
         */
        ContentResolver contentResolver = getContentResolver();

        /*
        Consultar el nombre del contacto
         */
        Cursor c = contentResolver.query(
                uri,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                null,
                null,
                null);

        if(c.moveToFirst()){
            name = c.getString(0);
        }

        /*
        Cerramos el cursor
         */
        c.close();

        return name;
    }


}
