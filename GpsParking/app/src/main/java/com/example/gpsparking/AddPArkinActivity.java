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
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class AddPArkinActivity extends AppCompatActivity  {
    Toolbar toolbar;
    TextView Title;
    EditText designation, longitude, latitude,places,proprietaire,date;
    DataBase dataBase= new DataBase(this);
    DatePickerDialog picker;
    String nom,prenom;
    Button btn_add;
    String cin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parkin);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Title= (TextView) toolbar.findViewById(R.id.toolbar_title);
        Title.setText("Ajouter Parking");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent intent= getIntent();
        if(intent!=null)
        {
            nom=intent.getStringExtra("nom");
            prenom=intent.getStringExtra("prenom");




        }

        if(exitprofil(nom,prenom)==0)
        {
            Toast.makeText(this, "Tu dois ajouter un profil Tout d'abord", Toast.LENGTH_LONG).show();
            Intent intent2= new Intent(this, ProfilActivity.class);
            intent2.putExtra("nom",nom);
            intent2.putExtra("prenom",prenom);
            startActivity(intent2);
        }
        designation= (EditText) findViewById(R.id.in_designantion);
        longitude= (EditText) findViewById(R.id.in_longitude);
        latitude= (EditText) findViewById(R.id.in_latitude);
        places= (EditText) findViewById(R.id.in_place);
        proprietaire= (EditText) findViewById(R.id.in_proprietaire);
        proprietaire.setText(cin);
        proprietaire.setEnabled(false);
        date=(EditText) findViewById(R.id.in_date);
        date.setInputType(InputType.TYPE_NULL);

        btn_add=(Button) findViewById(R.id.btn_ajouter_parking);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AjouterParking();
            }
        });


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker= new DatePickerDialog(AddPArkinActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                    }
                }, year,month,day);
                picker.show();
            }
        });

        if(intent!=null)
        {
            if(intent.getStringExtra("designantion")!=null) designation.setText(intent.getStringExtra("designantion"));
           if(intent.getStringExtra("date")!=null) date.setText(intent.getStringExtra("date"));
            if(intent.getStringExtra("places")!=null) places.setText(intent.getStringExtra("places"));
            if(intent.getStringExtra("long")!=null) longitude.setText(intent.getStringExtra("long"));
            if(intent.getStringExtra("lat")!=null) latitude.setText(intent.getStringExtra("lat"));
        }



        longitude.setInputType(InputType.TYPE_NULL);
        latitude.setInputType(InputType.TYPE_NULL);

        longitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3= new Intent(AddPArkinActivity.this,Map.class);
                intent3.putExtra("nom",nom);
                intent3.putExtra("prenom",prenom);
                intent3.putExtra("designantion",designation.getText().toString());
                intent3.putExtra("date",date.getText().toString());
                intent3.putExtra("places",places.getText().toString());
                startActivity(intent3);
            }
        });
        latitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4= new Intent(AddPArkinActivity.this,Map.class);
                intent4.putExtra("nom",nom);
                intent4.putExtra("prenom",prenom);
                startActivity(intent4);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_proprietaire, menu);
        return true;
    }


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



    public void AjouterParking()
    {
        String in_designantion;
        int in_proprietaire,in_places;
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
            //in_proprietaire= Integer.valueOf(proprietaire.getText().toString());
            Long i= dataBase.insertParkingDetails(in_designantion,in_places,cin,in_long,in_lat,in_date,0);
            Intent intent= new Intent(this,DashbordProprietaireActivity.class);
            intent.putExtra("nom",nom);
            intent.putExtra("prenom",prenom);
            startActivity(intent);
            System.out.println("Resultat" + i);
        }
    }


    public int exitprofil(String nom,String prenom)
    {
        if(dataBase.getListPropriets(nom,prenom).size()>0)
        {
            ArrayList<HashMap<String,String>> proprietaire = new ArrayList<>();
            proprietaire= dataBase.getListPropriets(nom,prenom);

            for(int i=0;i<proprietaire.size();i++)
            {
                final HashMap<String,String> temdata=(HashMap<String ,String>) proprietaire.get(i);
                Set<String> key = temdata.keySet();
                Iterator it = key.iterator();
                cin=temdata.get("cin");
            }
        }
        return dataBase.getListPropriets(nom,prenom).size();
    }


}
