package com.example.gpsparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ParkingNonValideActivity extends AppCompatActivity implements OnMapReadyCallback {
    Toolbar toolbar;
    TextView Title;
    CardView cardView;
    View customView;
    PopupWindow popupWindow;
    Double lg;
    Double lt;
    MarkerOptions markerOptions = new MarkerOptions();
    private GoogleMap mMap;
    String nom_parking;


    CardView cardviewinparent;
    FrameLayout parentLayout;
    DataBase dataBase= new DataBase(ParkingNonValideActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_non_valides);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Title= (TextView) toolbar.findViewById(R.id.toolbar_title);
            Title.setText("Parking Non valides");

        ParkingNonvalide();






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
            Intent intent= new Intent(this, DashBordActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.demandes){
            Intent intent= new Intent(this,DemandesActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.proprietaire){
            Intent intent= new Intent(this,ProprietaireActivity.class);
            startActivity(intent);
            return  true;

        }
        else if(id== R.id.parking){
            Intent intent= new Intent(this,ParkingActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.abonnes){
            Intent intent= new Intent(this,AboonesActivity.class);
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

    public void ParkingNonvalide()
    {
        ArrayList<HashMap<String,String>> listparking = new ArrayList<>();
        listparking= dataBase.getListParkingNonValide();

        if (dataBase.getListParkingNonValide().size()>0)
        {
            for(int i=0;i<listparking.size();i++)
            {
                final HashMap<String,String> temdata=(HashMap<String ,String>) listparking.get(i);
                Set<String> key = temdata.keySet();
                Iterator it = key.iterator();
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

                cardView=(CardView) LayoutInflater.from(ParkingNonValideActivity.this).inflate(R.layout.parking_nonvlide,null);

                ((TextView) cardView.findViewById(R.id.parking_validation)).setText(temdata.get("designation"));
                ((TextView) cardView.findViewById(R.id.proprietaire_validation)).setText(temdata.get("key_proprietaire"));
                ((TextView) cardView.findViewById(R.id.date_ajout_validation)).setText(temdata.get("date_ajout"));
                ((ImageView) cardView.findViewById(R.id.image_localisation)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showPopup(view);
                        lt= Double.valueOf(temdata.get("Latitude"));
                        lg=Double.valueOf(temdata.get("longitude"));
                        nom_parking=temdata.get("designation");

                    }
                });
                ((Button) cardView.findViewById(R.id.btn_valider)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataBase.ValiderPArking(Integer.valueOf(temdata.get("Num_parking")));
                        Intent intent= new Intent(ParkingNonValideActivity.this,ParkingNonValideActivity.class);
                        startActivity(intent);
                    }
                });
                ((Button) cardView.findViewById(R.id.btn_rejetter)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataBase.RejetterPArking(Integer.valueOf(temdata.get("Num_parking")));
                        Intent intent= new Intent(ParkingNonValideActivity.this,ParkingNonValideActivity.class);
                        startActivity(intent);
                    }
                });

                parentLayout=(FrameLayout) findViewById(R.id.id_view_card);
                params.setMargins(0,i*650,0,0);
                parentLayout.addView(cardView,params);
            }
        }
        else{
            ((TextView) findViewById(R.id.testvies_pas_de_parking)).setVisibility(View.VISIBLE);
        }



    }

    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.clear();
        LatLng hyderadbad = new LatLng(lt, lg);

        CameraPosition googlePlex = CameraPosition.builder()
                .target(hyderadbad)
                .zoom(12)
                .bearing(0)
                .tilt(45)
                .build();
        // Add a marker in hyderabad and move the camera
        markerOptions.position(hyderadbad);
        markerOptions.title(nom_parking).rotation((float) 3.4);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.carparking));
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hyderadbad,12));
    }

    public void showPopup(final View v) {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        customView= inflater.inflate(R.layout.map,null);
        ((ImageButton) customView.findViewById(R.id.close_popup) ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePopuo(v);
            }
        });
        popupWindow= new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setWidth(1030);
        popupWindow.setHeight(1680);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        popupWindow.showAtLocation( findViewById(R.id.parent_layout_non_valide_parking), Gravity.CENTER_HORIZONTAL,0,100);
    }
    public  void closePopuo(View view)
    {
        popupWindow.dismiss();
        Intent intent= new Intent(ParkingNonValideActivity.this,ParkingNonValideActivity.class);
        startActivity(intent);
    }
}
