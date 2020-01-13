package com.example.gpsparking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ConducteurActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    Toolbar toolbar;
    TextView Title;
    private GoogleMap mMap;
    Boolean test=true;
    private int numparking;
    MarkerOptions markerOptions = new MarkerOptions();
    DataBase dataBase = new DataBase(this);
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    Location ma_location;
    LatLng origin = new LatLng(36, -5.545);
   // LatLng dest = new LatLng(33.99831765331328,-6.8447608686983585);
    LatLng dest= null;
    ProgressDialog progressDialog;
    private Polyline mPolyline;

    LatLng ma_position= null;


    LocationManager locationManager;
    String nom_parking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conducteur);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Title.setText("Conducteur");
        Bundle extras = getIntent().getExtras();
        if(extras!=null)

        {
            dest= new LatLng(extras.getDouble("Latitude"),extras.getDouble("longitude"));
            nom_parking=extras.getString("nom");
        }


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkLocationPermission()){
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ConducteurActivity.this);

                ma_location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (ma_location!=null)
                {
                    ma_position= new LatLng(ma_location.getLatitude(),ma_location.getLongitude());
                    Log.d("long:","mon_long"+ma_location.getLongitude());

                }

            }
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_c);
        mapFragment.getMapAsync(this);
        if(dest!=null && ma_position!=null)  drawPolylines();


    }



    @Override
    protected void onStart() {
        super.onStart();
        if(dest!=null) {
            Intent intent = new Intent(this,Notation.class);
            intent.putExtra("nom",nom_parking);
            startActivity(intent);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.parking_C) {
            Intent intent= new Intent(this, ConducteurActivity.class);
            startActivity(intent);
            return true;
        }

        else if(id== R.id.deconnect_C){
            Intent intent= new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
                     return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conducteur, menu);
        return true;
    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location!= null)
        {
            if(test)
            {
                ma_position= new LatLng(location.getLatitude(),location.getLongitude());
                onMapReady(mMap);
                test=false;
            }
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
        Toast.makeText(getApplicationContext(),"GPS désactivé", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
        Toast.makeText(getApplicationContext(),"GPS Activé", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void drawPolylines() {
       // progressDialog = new ProgressDialog(ConducteurActivity.this);
        //progressDialog.setMessage("Please Wait, Polyline between two locations is building.");
        //progressDialog.setCancelable(false);
        //progressDialog.show();

        String url = getDirectionsUrl(ma_position, dest);
        Log.d("url", url + "");
        DownloadTask downloadTask = new DownloadTask();
        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }


    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
       mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.clear();






        if(ma_position!=null)
        {
            googleMap.addMarker(new MarkerOptions().position(ma_position).icon(BitmapDescriptorFactory.fromResource(R.drawable.automobil)));

        }

        ArrayList<HashMap<String, String>> listparking = new ArrayList<>();
        listparking = dataBase.getListParking();
        LatLng hyderadbad = new LatLng(0, 0);
        for (int i = 0; i < listparking.size(); i++) {
            final HashMap<String, String> temdata = (HashMap<String, String>) listparking.get(i);
            Set<String> key = temdata.keySet();
            Iterator it = key.iterator();
            hyderadbad = new LatLng(Double.valueOf(temdata.get("Latitude")), Double.valueOf(temdata.get("longitude")));
            markerOptions.position(hyderadbad);
            markerOptions.title(temdata.get("designation")).rotation((float) 3.4);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.carparking));
            mMap.addMarker(markerOptions).setTag((String) temdata.get("Num_parking"));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                        setNumparking(Integer.valueOf((String) marker.getTag()));
                        System.out.println("Parking:" + getNumparking());
                        Intent intent = new Intent(ConducteurActivity.this, ReservationActivity.class);
                        intent.putExtra("id_parking", getNumparking());
                        startActivity(intent);

                    return true;
                }
            });
        }

        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 15));
        if(ma_position!=null)
        {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ma_position, 15));
        }else  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hyderadbad, 15));
    }

    public void setNumparking(int numparking) {
        this.numparking = numparking;
    }

    public int getNumparking() {
        return numparking;
    }
    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }
    }


    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(15);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                if(mPolyline != null){
                    mPolyline.remove();
                }
                mPolyline = mMap.addPolyline(lineOptions);

            }else
                Toast.makeText(getApplicationContext(),"No route is found", Toast.LENGTH_LONG).show();
        }




    }

    /*private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
      //  String sensor = "sensor=false";
       // String mode = "mode=driving";
        // Building the parameters to the web service
        //String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;
        // Key
        String key = "key=" + getString(R.string.google_maps_key);

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+key;
        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }*/
    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String key = "key=AIzaSyCaZDKqG4fyoo9_P1Kc9TS0oPEPlSXskJw" ;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode+"&"+key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();
            Log.d("data", data);

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}