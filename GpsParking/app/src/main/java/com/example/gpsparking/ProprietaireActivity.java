package com.example.gpsparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Set;

public class ProprietaireActivity extends AppCompatActivity implements OnMapReadyCallback {
    Toolbar toolbar;
    TextView Title;
    TableLayout table_proprietaires;
    TableRow row;
    DataBase dataBase= new DataBase(ProprietaireActivity.this);
    View customView;
    LayoutInflater layoutInflater;
    PopupWindow popupWindow;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proprietaire);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Title= (TextView) toolbar.findViewById(R.id.toolbar_title);
        Title.setText("Liste Propriétaires");
        //affcihcage
        table_proprietaires=(TableLayout) findViewById(R.id.table_propriete);
        //database


        listProprietaire();


        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void listProprietaire()
    {
        ArrayList<HashMap<String,String>> listProprietaire= new ArrayList<>();
        listProprietaire= dataBase.getListPropriets();


        for(int i=0; i<listProprietaire.size();i++)
        {
            final HashMap<String,String> temdata=(HashMap<String ,String>) listProprietaire.get(i);
            Set<String> key = temdata.keySet();
            Iterator it = key.iterator();
            row=(TableRow) LayoutInflater.from(ProprietaireActivity.this).inflate(R.layout.row_table_proprietaire,null);
            ((TextView) row.findViewById(R.id.id_proprietaire)).setText(temdata.get("KEY_ID"));
            ((TextView) row.findViewById(R.id.nom_proprietaire)).setText(temdata.get("nom"));
            ((TextView) row.findViewById(R.id.prenom_proprietaire)).setText(temdata.get("prenom"));
            ((TextView) row.findViewById(R.id.cin_propriateire)).setText(temdata.get("cin"));

            ((ImageButton) row.findViewById(R.id.btn_action_proprietaire)).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dataBase.deleteoneProprietaire(Integer.valueOf(temdata.get("KEY_ID")));
                            Intent intent= new Intent(ProprietaireActivity.this,ProprietaireActivity.class);
                            startActivity(intent);
                        }
                    }
            );
            table_proprietaires.addView(row);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.tableau_debord) {
            Intent intent= new Intent(ProprietaireActivity.this, DashBordActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.demandes){
            Intent intent= new Intent(ProprietaireActivity.this,DemandesActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.proprietaire){
            Intent intent= new Intent(ProprietaireActivity.this,ProprietaireActivity.class);
            startActivity(intent);
            return  true;

        }
        else if(id== R.id.parking){
            Intent intent= new Intent(ProprietaireActivity.this,ParkingActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.abonnes){
            Intent intent= new Intent(ProprietaireActivity.this,AboonesActivity.class);
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

    public void showPopup(View v) {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        customView= inflater.inflate(R.layout.map,null);

        popupWindow= new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(1300);
        popupWindow.setWidth(900);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
/*
        ((ImageButton) customView.findViewById(R.id.btn_action_proprietaire)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ProprietaireActivity.this,Map.class);
                startActivity(intent);
            }
        });*/
        popupWindow.showAtLocation( findViewById(R.id.parent_layout), Gravity.CENTER,0,0);
    }
    public  void closePopuo(View view)
    {
        popupWindow.dismiss();
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        // Add a marker in hyderabad and move the camera
        LatLng hyderadbad = new LatLng(17, 78);
        mMap.addMarker(new MarkerOptions().position(hyderadbad).title("Tutlane in India"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hyderadbad));
    }
}
