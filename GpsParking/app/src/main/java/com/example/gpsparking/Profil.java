package com.example.gpsparking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Profil extends AppCompatActivity {
    Toolbar toolbar;
    TextView Title;
    private String nom;
    private String prenom;
    private DataBase dataBase = new DataBase(this);
    private String cin;
    private String in_nom;
    private String in_prenom;
    private TextView profil_cin;
    private TextView profil_prenom;
    private TextView profil_nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Title.setText("Mon profil");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent intent= getIntent();
        if(intent!=null)
        {
            nom=intent.getStringExtra("nom");
            prenom=intent.getStringExtra("prenom");
        }
        getProfil(nom,prenom);
        if (getProfil(nom,prenom)>0)
        {
            profil_cin=(TextView) findViewById(R.id.profil_cin);
            profil_prenom=(TextView) findViewById(R.id.profil_prenom);
            profil_nom=(TextView) findViewById(R.id.profil_nom);
            profil_cin.setText(cin);
            profil_nom.setText(nom);
            profil_prenom.setText(prenom);
        }
        else if (getProfil(nom,prenom)==0) {
            Intent intent2= new Intent(this,ProfilActivity.class);
            intent2.putExtra("nom",nom);
            intent2.putExtra("prenom",prenom);
            startActivity(intent2);
            Toast.makeText(this, "Vous devez créer un profil", Toast.LENGTH_SHORT).show();
        }

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


    public int getProfil(String nom,String prenom)
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
                in_nom=temdata.get("nom");
                in_prenom=temdata.get("prenom");
            }
        }
        return dataBase.getListPropriets(nom,prenom).size();
    }

}
