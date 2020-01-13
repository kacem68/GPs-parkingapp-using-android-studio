package com.example.gpsparking;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ProfilActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView Title;
    DataBase dataBase;
    TableLayout table_parking;
    private GoogleMap mMap;
    View customView;
    PopupWindow popupWindow;
    Double lg;
    Double lt;
    String nom_parking;
    TableRow row;
    ImageButton btn_close;
    MarkerOptions markerOptions = new MarkerOptions();
    private String nom;
    private String prenom;

    EditText in_nom,in_prenom,in_cin,in_adresse;
    private String cin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityprofil);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Title= (TextView) toolbar.findViewById(R.id.toolbar_title);
        Title.setText("Ajouter Profil");


        in_prenom=(EditText) findViewById(R.id.in_prenom);
        in_nom=(EditText) findViewById(R.id.in_nom);
        in_cin=(EditText) findViewById(R.id.editTextCin);
        in_adresse=(EditText) findViewById(R.id.editTextAdress);




        dataBase = new DataBase(ProfilActivity.this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent intent= getIntent();
        if(intent!=null)
        {
            nom=intent.getStringExtra("nom");
            prenom=intent.getStringExtra("prenom");
        }

        in_nom.setText(nom);
        in_prenom.setText(prenom);
        in_nom.setEnabled(false);
        in_prenom.setEnabled(false);

        if(exitprofil(nom,prenom)>0)
        {
            Toast.makeText(this, "Vous avez déja un profil", Toast.LENGTH_LONG).show();
            Intent intent2= new Intent(this, Profil.class);
            intent2.putExtra("nom",nom);
            intent2.putExtra("prenom",prenom);
            startActivity(intent2);
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


    public void AjouterProfil(View view)
    {
        String in_designantion;
        int in_proprietaire,in_places;
        Double in_long, in_lat;
        Date in_date;




        if(  in_cin.getText().toString().isEmpty()|| in_prenom.getText().toString().isEmpty()||in_nom.getText().toString().isEmpty() ||
                in_adresse.getText().toString().isEmpty()
        )
        {
            Toast.makeText(this, "Tous les champs doivent être remplis", Toast.LENGTH_LONG).show();
        }
        else {
            Long i= dataBase.insertProprietaireDetails(in_nom.getText().toString(),in_prenom.getText().toString(),in_adresse.getText().toString(),in_cin.getText().toString());
            Intent intent= new Intent(this,DashbordProprietaireActivity.class);
            intent.putExtra("prenom",prenom);
            Toast.makeText(this, "Ajout avec succes", Toast.LENGTH_LONG).show();
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
