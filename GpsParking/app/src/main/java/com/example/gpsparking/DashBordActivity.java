package com.example.gpsparking;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class DashBordActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView Title;
    TextView traite;
    TextView demandes_non_traite;
    TextView non_traite;
    Button btn_parking_valides;
    Button btn_valider_parking;
    Button btn_Liste_propriétaire;
    TextView demandes_non_traites;

    TextView non_traites;
    CardView card_demandes_non_traite;
    DataBase dataBase = new DataBase(this);
    View customView;
    LayoutInflater layoutInflater;
    PopupWindow popupWindow;
    Button btn_Liste_Aboones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Title= (TextView) toolbar.findViewById(R.id.toolbar_title);
        Title.setText("Tableau De Bord");

        ((TextView) findViewById(R.id.viewtext_nombre_proprietaire)).setText(String.valueOf(getNombreProprieteir()));
        ((TextView) findViewById(R.id.viewtext_parking_valide)).setText(String.valueOf(NombreParkingValid()));
        ((TextView) findViewById(R.id.viewtext_abonnes)).setText(String.valueOf(NombreAbonnes()));

        if(NombreParkingNonValide()>0)
        {
            ((CardView) findViewById(R.id.card_new_parking)).setVisibility(View.VISIBLE);
            ((CardView) findViewById(R.id.card_new_nobre_parking)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.txt_nombre_parking_non_valides)).setText(String.valueOf(NombreParkingNonValide()));
        }

        ///action
        if(dataBase.getListDemande().size()==0) {
            ((CardView) findViewById(R.id.card_new_demandes)).setVisibility(View.INVISIBLE);
            ((CardView) findViewById(R.id.card_new_nobre_demandes)).setVisibility(View.INVISIBLE);
        }
        else {
            ((TextView) findViewById(R.id.txt_nombre_demandes_non_traites)).setText(String.valueOf(dataBase.getListDemande().size()));
        }
        traite= (TextView) findViewById(R.id.traite);
        traite.setText(String.valueOf(dataBase.getListDemandeTraitees().size())+" - "+"traitées");
        non_traite=(TextView) findViewById(R.id.text_demandes_non_traite);
        non_traite.setText(String.valueOf(dataBase.getListDemande().size())+" - "+"non traitées");



        btn_parking_valides=(Button) findViewById(R.id.btn_liste_parking_valides);
        btn_Liste_propriétaire=(Button) findViewById(R.id.btn_liste_proprietaire);
        btn_valider_parking=(Button) findViewById(R.id.btn_liste_parking_nonvalides);
        card_demandes_non_traite=(CardView) findViewById(R.id.card_new_nobre_demandes);
        ////
        non_traite.setOnClickListener(afficheDemandes);
        btn_parking_valides.setOnClickListener(afficherParkingValides);
        btn_Liste_propriétaire.setOnClickListener(AfficherProprietaires);
        ((Button) findViewById(R.id.btn_liste_parking_nonvalides)).setOnClickListener(AfficherListPakingNonValide);
        ((Button) findViewById(R.id.btn_liste_abonnes)).setOnClickListener(AfficherListAbonnes);





        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
            Intent intent= new Intent(DashBordActivity.this, DashBordActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.demandes){
            Intent intent= new Intent(DashBordActivity.this,DemandesActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.proprietaire){
            Intent intent= new Intent(DashBordActivity.this,ProprietaireActivity.class);
            startActivity(intent);
            return  true;

        }
        else if(id== R.id.parking){
            Intent intent= new Intent(DashBordActivity.this,ParkingActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.abonnes){
            Intent intent= new Intent(DashBordActivity.this,AboonesActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.deconnect){
            Intent intent= new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public View.OnClickListener afficheDemandes = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent= new Intent(DashBordActivity.this,DemandesActivity.class);
            startActivity(intent);
        }
    };

    public View.OnClickListener afficherParkingValides = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent= new Intent(DashBordActivity.this,ParkingActivity.class);
            startActivity(intent);
        }
    };

    public View.OnClickListener AfficherProprietaires = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent= new Intent(DashBordActivity.this,ProprietaireActivity.class);
            startActivity(intent);
        }
    };

    public  View.OnClickListener AfficherListPakingNonValide= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent= new Intent(DashBordActivity.this,ParkingNonValideActivity.class);
            startActivity(intent);
        }
    };
    public  View.OnClickListener AfficherListAbonnes= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent= new Intent(DashBordActivity.this,AboonesActivity.class);
            startActivity(intent);
        }
    };
    public int getNombreProprieteir()
    {
        return dataBase.getListPropriets().size();
    }
    public int NombreParkingValid(){
        return  dataBase.getListParking().size();
    }
    public int NombreParkingNonValide()
    {
        return dataBase.getListParkingNonValide().size();
    }
    public int NombreAbonnes()
    {
        return dataBase.getListUsers().size();
    }

}
