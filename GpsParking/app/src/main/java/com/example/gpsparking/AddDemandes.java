package com.example.gpsparking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddDemandes extends AppCompatActivity {
    Toolbar toolbar;
    TextView Title;
    EditText motif;
    EditText num_parking;
    DataBase dataBase = new DataBase(this);
    String nom,prenom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_demandes);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Title= (TextView) toolbar.findViewById(R.id.toolbar_title);
        Title.setText("Ajouter Demandes");
        Intent intent= getIntent();
        if(intent!=null)
        {
            nom=intent.getStringExtra("nom");
            prenom=intent.getStringExtra("prenom");
        }
        motif= (EditText) findViewById(R.id.motif_suppressiopn);
        num_parking= (EditText) findViewById(R.id.num_park_demandes_supression);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


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
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Demander(View view) {
        if(!motif.getText().toString().isEmpty() && !num_parking.getText().toString().isEmpty())
        {
            if(dataBase.getListParkingByid(Integer.valueOf(num_parking.getText().toString())).size()>0)
            {
                dataBase.insertDemande(motif.toString(), Integer.valueOf(num_parking.getText().toString()));
                Toast.makeText(this, "Demande envoyée", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,DashbordProprietaireActivity.class);
                intent.putExtra("nom",nom);
                intent.putExtra("prenom",prenom);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Numéro de parking inéxistant", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, "Remplir les champs", Toast.LENGTH_LONG).show();
        }

    }
}
