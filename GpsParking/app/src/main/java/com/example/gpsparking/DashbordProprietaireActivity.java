package com.example.gpsparking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DashbordProprietaireActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView Title;
    DataBase dataBase= new DataBase(this);
    String nom,prenom;
    private String cin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord_proprietaire);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Title= (TextView) toolbar.findViewById(R.id.toolbar_title);
        Title.setText("Tableau de bord");
        Intent intent= getIntent();
        if(intent!=null)
        {
            nom=intent.getStringExtra("nom");
            prenom=intent.getStringExtra("prenom");
        }


      ((TextView) findViewById(R.id.viewtext_parking_valide_P)).setText(String.valueOf(dataBase.getListParking(cin).size()));
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
            intent.putExtra("nom",nom);
            intent.putExtra("prenom",prenom);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public  void Accder(View view)
    {
        Intent intent= new Intent(this,MesParkingActivity.class);
        intent.putExtra("nom",nom);
        intent.putExtra("prenom",prenom);
        startActivity(intent);
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
