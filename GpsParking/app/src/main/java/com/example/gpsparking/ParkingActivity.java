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
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

public class ParkingActivity extends AppCompatActivity implements OnMapReadyCallback {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_liste);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Title= (TextView) toolbar.findViewById(R.id.toolbar_title);
        Title.setText("Liste Parking Valides");


        table_parking=(TableLayout) findViewById(R.id.table_parking);
        dataBase = new DataBase(ParkingActivity.this);
        ListeParking();
        btn_close= (ImageButton) findViewById(R.id.close_popup);
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
            Intent intent= new Intent(ParkingActivity.this, DashBordActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.demandes){
            Intent intent= new Intent(ParkingActivity.this,DemandesActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.proprietaire){
            Intent intent= new Intent(ParkingActivity.this,ProprietaireActivity.class);
            startActivity(intent);
            return  true;

        }
        else if(id== R.id.parking){
            Intent intent= new Intent(ParkingActivity.this,ParkingActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.abonnes){
            Intent intent= new Intent(ParkingActivity.this,AboonesActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.deconnect){
            Intent intent= new Intent(ParkingActivity.this,AboonesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public  void  ListeParking()
    {
        ArrayList<HashMap<String,String>> listparking = new ArrayList<>();
        listparking= dataBase.getListParking();

        for(int i=0;i<listparking.size();i++)
        {
            final HashMap<String,String> temdata=(HashMap<String ,String>) listparking.get(i);
            Set<String> key = temdata.keySet();
            Iterator it = key.iterator();
            row=(TableRow) LayoutInflater.from(ParkingActivity.this).inflate(R.layout.row_table_parking,null);
            ((TextView) row.findViewById(R.id.id_parking)).setText(temdata.get("Num_parking"));
            ((TextView) row.findViewById(R.id.designation_paking)).setText(temdata.get("designation"));
            ((TextView) row.findViewById(R.id.proprietaire_parking)).setText(temdata.get("key_proprietaire"));
            ((TextView) row.findViewById(R.id.date_ajout_parking)).setText(temdata.get("date_ajout"));
            ((ImageButton) row.findViewById(R.id.btn_localisation)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopup(view);
                    lt= Double.valueOf(temdata.get("Latitude"));
                    lg=Double.valueOf(temdata.get("longitude"));
                    nom_parking=temdata.get("designation");

                }
            });

            table_parking.addView(row);

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


        popupWindow.showAtLocation( findViewById(R.id.parent_layout), Gravity.CENTER_HORIZONTAL,0,100);
    }
    public  void closePopuo(View view)
    {
        popupWindow.dismiss();
        Intent intent= new Intent(ParkingActivity.this,ParkingActivity.class);
        startActivity(intent);
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
