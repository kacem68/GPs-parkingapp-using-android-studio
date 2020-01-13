package com.example.gpsparking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ModifyParking extends AppCompatActivity {
    Toolbar toolbar;
    TextView Title;
    EditText designation, longitude, latitude,places,proprietaire,date;
    DataBase dataBase= new DataBase(this);
    DatePickerDialog picker;
    Button btn_add;
    int num_park;
    private String nom;
    private String prenom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parkin);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Title= (TextView) toolbar.findViewById(R.id.toolbar_title);
        Title.setText("Modifier Parking");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Bundle extras= getIntent().getExtras();
        if(extras!=null)
        {
            num_park=extras.getInt("id_parking");
        }
        Intent intent= getIntent();
        if(intent!=null)
        {
            nom=intent.getStringExtra("nom");
            prenom=intent.getStringExtra("prenom");
        }

        designation= (EditText) findViewById(R.id.in_designantion);
        longitude= (EditText) findViewById(R.id.in_longitude);
        latitude= (EditText) findViewById(R.id.in_latitude);
        places= (EditText) findViewById(R.id.in_place);
        proprietaire= (EditText) findViewById(R.id.in_proprietaire);
        date=(EditText) findViewById(R.id.in_date);
        date.setInputType(InputType.TYPE_NULL);

        btn_add=(Button) findViewById(R.id.btn_ajouter_parking);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Modifier();
            }
        });


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker= new DatePickerDialog(ModifyParking.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                    }
                }, year,month,day);
                picker.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_proprietaire, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.tableau_debord_P) {
            Intent intent= new Intent(this, DashbordProprietaireActivity.class);
            intent.putExtra("nom",nom);
            intent.putExtra("prenom",prenom);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.demandes_P){
            Intent intent= new Intent(this,AddDemandes.class);
            intent.putExtra("nom",nom);
            intent.putExtra("prenom",prenom);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.parking_P){
            Intent intent= new Intent(this,MesParkingActivity.class);
            intent.putExtra("nom",nom);
            intent.putExtra("prenom",prenom);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.add_parking_P){
            Intent intent= new Intent(this,AddPArkinActivity.class);
            intent.putExtra("nom",nom);
            intent.putExtra("prenom",prenom);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.deconnect_espace_proprietaire){
            Intent intent= new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);         return true;
        }
        else if(id== R.id.add_profil_proprietaire){
            Intent intent= new Intent(this,ProfilActivity.class);
            intent.putExtra("nom",nom);
            intent.putExtra("prenom",prenom);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.profil_proprietaire){
            Intent intent= new Intent(this,Profil.class);
            intent.putExtra("nom",nom);
            intent.putExtra("prenom",prenom);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }






        @Override
        protected void onResume() {
        super.onResume();
            ArrayList<HashMap<String,String>> parking = new ArrayList<>();
            parking= dataBase.getListParkingByid(num_park);

            for(int i=0;i<parking.size();i++)
            {
                final HashMap<String,String> temdata=(HashMap<String ,String>) parking.get(i);
                Set<String> key = temdata.keySet();
                Iterator it = key.iterator();
                designation.setText(temdata.get("designation"));
                places.setText(temdata.get("places"));
                proprietaire.setText(temdata.get("key_proprietaire"));
                longitude.setText(temdata.get("longitude"));
                latitude.setText(temdata.get("Latitude"));
                date.setText(temdata.get("date_ajout"));
            }
    }


    public void Modifier()
    {
        String in_designantion,in_proprietaire;
        int in_places;
        Double in_long, in_lat;
        Date in_date;




        if(  designation.getText().toString().isEmpty()|| longitude.getText().toString().isEmpty()||latitude.getText().toString().isEmpty() ||
                places.getText().toString().isEmpty() || proprietaire.getText().toString().isEmpty() || date.getText().toString().isEmpty()
        )
        {
            ((TextView) findViewById(R.id.messge_add_parking)).setVisibility(View.VISIBLE);
        }
        else {
            in_long=Double.valueOf(longitude.getText().toString());
            in_lat= Double.valueOf(latitude.getText().toString());
            in_date= Date.valueOf(date.getText().toString());
            in_places= Integer.valueOf(places.getText().toString());
            in_designantion=designation.getText().toString().trim();
            in_proprietaire= proprietaire.getText().toString();
            Long i= dataBase.updateParkingDetails(num_park,in_designantion,in_places,in_proprietaire,in_long,in_lat,in_date,1);
            Intent intent= new Intent(this,MesParkingActivity.class);
            intent.putExtra("nom",nom);
            intent.putExtra("prenom",prenom);
            startActivity(intent);
            System.out.println("Resultat" + i);
        }
    }
}


