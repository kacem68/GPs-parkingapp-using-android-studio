package com.example.gpsparking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MesParkingActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView Title;
    DataBase dataBase= new DataBase(MesParkingActivity.this);
    TableRow row;
    TableLayout table_parking;
    private String nom;
    private String prenom;
    private String cin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_parking);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Title= (TextView) toolbar.findViewById(R.id.toolbar_title);
        Title.setText("Mes Parking");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        table_parking=(TableLayout) findViewById(R.id.table_parking_P);
        ListeParking();
        Intent intent= getIntent();
        if(intent!=null)
        {
            nom=intent.getStringExtra("nom");
            prenom=intent.getStringExtra("prenom");
        }

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


    public  void  ListeParking()
    {
        ArrayList<HashMap<String,String>> listparking = new ArrayList<>();
        listparking= dataBase.getListParking(cin);

        for(int i=0;i<listparking.size();i++)
        {
            final HashMap<String,String> temdata=(HashMap<String ,String>) listparking.get(i);
            Set<String> key = temdata.keySet();
            Iterator it = key.iterator();
            row=(TableRow) LayoutInflater.from(MesParkingActivity.this).inflate(R.layout.row_table_parking_p,null);
            ((TextView) row.findViewById(R.id.id_parking_P)).setText(temdata.get("Num_parking"));
            ((TextView) row.findViewById(R.id.designation_paking_P)).setText(temdata.get("designation"));
            ((TextView) row.findViewById(R.id.proprietaire_parking_P)).setText(temdata.get("key_proprietaire"));
            ((TextView) row.findViewById(R.id.date_ajout_parking_P)).setText(temdata.get("date_ajout"));
            ((TextView) row.findViewById(R.id.place_P)).setText(temdata.get("places"));
            ((ImageButton) row.findViewById(R.id.btn_modifier)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ModifierParking(Integer.valueOf(temdata.get("Num_parking")));
                }
            });

            table_parking.addView(row);
        }
    }

    public  void ModifierParking(int num_parking)
    {
        Intent intent = new Intent(MesParkingActivity.this,ModifyParking.class);
        intent.putExtra("id_parking",num_parking);
        intent.putExtra("nom",nom);
        intent.putExtra("prenom",prenom);
        startActivity(intent);
    }

    public int getCin(String nom,String prenom)
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
