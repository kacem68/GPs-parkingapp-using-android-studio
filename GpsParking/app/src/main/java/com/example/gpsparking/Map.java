package com.example.gpsparking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double lg,lt;
    private String nom,prenom;
    String designation,date,places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_dialog);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent intent= getIntent();
        if(intent!=null)
        {
            nom=intent.getStringExtra("nom");
            prenom=intent.getStringExtra("prenom");
            if(intent.getStringExtra("designantion")!=null) designation=intent.getStringExtra("designantion");
            if(intent.getStringExtra("date")!=null) date=intent.getStringExtra("date");
            if(intent.getStringExtra("places")!=null) places=intent.getStringExtra("places");

        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                lg=latLng.longitude;
                lt=latLng.latitude;
                mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.carparking)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                Toast.makeText(Map.this, "Long"+lg+"/lt"+lt, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Map.this,AddPArkinActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("long",String.valueOf(lg));
                intent.putExtra("lat",String.valueOf(lt));
                intent.putExtra("nom",nom);
                intent.putExtra("prenom",prenom);
                intent.putExtra("designantion",designation);
                intent.putExtra("date",date);
                intent.putExtra("places",places);

                startActivity(intent);
            }
        });
    }

}
