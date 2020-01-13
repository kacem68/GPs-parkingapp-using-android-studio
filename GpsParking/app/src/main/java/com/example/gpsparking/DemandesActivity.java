package com.example.gpsparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DemandesActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView Title;
    DataBase dataBase= new DataBase(this);
    CardView cardView;
    FrameLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demandes);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Title= (TextView) toolbar.findViewById(R.id.toolbar_title);
        Title.setText("Demandes");

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DemandesNontraites();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.tableau_debord) {
            Intent intent= new Intent(DemandesActivity.this, DashBordActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.demandes){
            Intent intent= new Intent(DemandesActivity.this,DemandesActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.proprietaire){
            Intent intent= new Intent(DemandesActivity.this,ProprietaireActivity.class);
            startActivity(intent);
            return  true;

        }
        else if(id== R.id.parking){
            Intent intent= new Intent(DemandesActivity.this,ParkingActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.deconnect){
            Intent intent= new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.abonnes){
            Intent intent= new Intent(DemandesActivity.this,AboonesActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void DemandesNontraites()
    {
        ArrayList<HashMap<String,String>> listdemandes = new ArrayList<>();
        listdemandes= dataBase.getListDemande();

        if (dataBase.getListDemande().size()>0)
        {
            for(int i=0;i<listdemandes.size();i++)
            {
                final HashMap<String,String> temdata=(HashMap<String ,String>) listdemandes.get(i);
                Set<String> key = temdata.keySet();
                Iterator it = key.iterator();
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

                cardView=(CardView) LayoutInflater.from(DemandesActivity.this).inflate(R.layout.demandes_non_traites,null);

                ((TextView) cardView.findViewById(R.id.motif_demande)).setText(temdata.get("motif"));
                ((TextView) cardView.findViewById(R.id.parking_demande)).setText(temdata.get("parking"));

                ((Button) cardView.findViewById(R.id.btn_valider)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataBase.deleteoneParkin(Integer.valueOf(temdata.get("parking")));
                        dataBase.ValiderDemande(Integer.valueOf(temdata.get("parking")));
                        Toast.makeText(DemandesActivity.this, "Parking Supprimer", Toast.LENGTH_LONG).show();
                        Intent intent= new Intent(DemandesActivity.this,DemandesActivity.class);
                        startActivity(intent);
                    }
                });
                ((Button) cardView.findViewById(R.id.btn_rejetter)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

                parentLayout=(FrameLayout) findViewById(R.id.id_view_card_demandes);
                params.setMargins(0,i*650,0,0);
                parentLayout.addView(cardView,params);
            }
        }
        else{
            ((TextView) findViewById(R.id.testvies_pas_de_demandes)).setVisibility(View.VISIBLE);
        }



    }
}
