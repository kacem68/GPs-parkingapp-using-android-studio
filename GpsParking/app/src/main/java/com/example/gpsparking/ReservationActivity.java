package com.example.gpsparking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ReservationActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView Title;
    FrameLayout frameplaces;
    int id_parking;
    String nom_parking;
    DataBase dataBase= new DataBase(ReservationActivity.this);
    CardView palce_G;
    CardView place_D;
    Button btn_reserver_D;
    CardView etat_D;
    double LONG, lat;
    int places;
    String title_parking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Title= (TextView) toolbar.findViewById(R.id.toolbar_title);
        Title.setText("Réservation");

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            id_parking= extras.getInt("id_parking");


        }

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getPlace();
        ((TextView) findViewById(R.id.textView_nom_parking)).setText("Parking:"+nom_parking+"/"+places);
        ((Button) findViewById(R.id.btn_naviguer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReservationActivity.this,ConducteurActivity.class);
                intent.putExtra("longitude",LONG);
                intent.putExtra("Latitude",lat);
                intent.putExtra("nom",nom_parking);
                startActivity(intent);
            }
        });

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


    public void  getPlace()
    {
        ArrayList<HashMap<String,String>> parking = new ArrayList<>();
        parking= dataBase.getListParkingByid(id_parking);

        for(int i=0;i<parking.size();i++)
        {
            final HashMap<String,String> temdata=(HashMap<String ,String>) parking.get(i);
            Set<String> key = temdata.keySet();
            Iterator it = key.iterator();
            nom_parking=temdata.get("designation");
            places=Integer.valueOf(temdata.get("places"));
            lat= Double.valueOf(temdata.get("Latitude"));
            LONG=Double.valueOf(temdata.get("longitude"));
        }

        frameplaces=(FrameLayout) findViewById(R.id.id_places_frame);

            int j= 1,i=0;
        if(places%2==0){
            while (j<=places){
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,i*400,0,0);
                FrameLayout parentLayout= new FrameLayout(this);
                parentLayout= (FrameLayout) LayoutInflater.from(ReservationActivity.this).inflate(R.layout.places,null);
                place_D=(CardView) parentLayout.findViewById(R.id.place_D);
                ((TextView) parentLayout.findViewById(R.id.textvie_num_place_gauche)).setText(String.valueOf(j));
                ((TextView) parentLayout.findViewById(R.id.textvie_num_place_D)).setText(String.valueOf(j+1));
                CardView etat_G= new CardView(ReservationActivity.this);
                CardView etat_D = new CardView(ReservationActivity.this);
                etat_G = (CardView) parentLayout.findViewById(R.id.etat_palce_G);
                etat_D=(CardView) parentLayout.findViewById(R.id.etat_palce_D);


                Button btn_reserver_G = new Button(this);
                Button btn_reserver_D = new Button(this);
                btn_reserver_G= (Button) parentLayout.findViewById(R.id.btn_place_G);
                btn_reserver_D= (Button) parentLayout.findViewById(R.id.btn_place_D);
                final Button finalBtn_reserver_G = btn_reserver_G;
                final Button finalBtn_reserver_D = btn_reserver_D;
                final CardView finalEtat_G = etat_G;
                final CardView finlEtat_D= etat_D;
                btn_reserver_G.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(finalBtn_reserver_G.getText().equals("réserver"))
                        {
                            finalEtat_G.setCardBackgroundColor(Color.GREEN);
                            finalBtn_reserver_G.setBackgroundDrawable(ContextCompat.getDrawable(ReservationActivity.this,R.drawable.btn_rejetter));
finalBtn_reserver_G.setText("libérer");
                        }
                        else if(finalBtn_reserver_G.getText().equals("libérer"))
                        {
                            finalEtat_G.setCardBackgroundColor(Color.RED);
                            finalBtn_reserver_G.setBackgroundDrawable(ContextCompat.getDrawable(ReservationActivity.this,R.drawable.btn_valider));
                            finalBtn_reserver_G.setText("réserver");
                        }


                    }
                });

                btn_reserver_D= (Button) parentLayout.findViewById(R.id.btn_place_D);

                btn_reserver_D.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(finalBtn_reserver_D.getText().equals("réserver"))
                        {
                            finlEtat_D.setCardBackgroundColor(Color.GREEN);
                            finalBtn_reserver_D.setBackgroundDrawable(ContextCompat.getDrawable(ReservationActivity.this,R.drawable.btn_rejetter));
                            finalBtn_reserver_D.setText("libérer");
                        }
                        else if(finalBtn_reserver_D.getText().equals("libérer"))
                        {
                            finlEtat_D.setCardBackgroundColor(Color.RED);
                            finalBtn_reserver_D.setBackgroundDrawable(ContextCompat.getDrawable(ReservationActivity.this,R.drawable.btn_valider));
                            finalBtn_reserver_D.setText("réserver");
                        }

                    }
                });
                frameplaces.addView(parentLayout,params);
                i++;
                j=j+2;
            }

        }
        else {
            while (j<=places){
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,i*350,0,0);
                FrameLayout parentLayout= new FrameLayout(this);
                parentLayout= (FrameLayout) LayoutInflater.from(ReservationActivity.this).inflate(R.layout.places,null);
                place_D=(CardView) parentLayout.findViewById(R.id.place_D);
                ((TextView) parentLayout.findViewById(R.id.textvie_num_place_gauche)).setText(String.valueOf(j));
                ((TextView) parentLayout.findViewById(R.id.textvie_num_place_D)).setText(String.valueOf(j+1));
                CardView etat_G= new CardView(ReservationActivity.this);
                CardView etat_D = new CardView(ReservationActivity.this);
                etat_G = (CardView) parentLayout.findViewById(R.id.etat_palce_G);
                etat_D=(CardView) parentLayout.findViewById(R.id.etat_palce_D);


                Button btn_reserver_G = new Button(this);
                Button btn_reserver_D = new Button(this);
                btn_reserver_G= (Button) parentLayout.findViewById(R.id.btn_place_G);
                btn_reserver_D= (Button) parentLayout.findViewById(R.id.btn_place_D);
                final Button finalBtn_reserver_G = btn_reserver_G;
                final Button finalBtn_reserver_D = btn_reserver_D;
                final CardView finalEtat_G = etat_G;
                final CardView finlEtat_D= etat_D;
                btn_reserver_G.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(finalBtn_reserver_G.getText().equals("réserver"))
                        {
                            finalEtat_G.setCardBackgroundColor(Color.GREEN);
                            finalBtn_reserver_G.setBackgroundDrawable(ContextCompat.getDrawable(ReservationActivity.this,R.drawable.btn_rejetter));
                            finalBtn_reserver_G.setText("libérer");
                        }
                        else if(finalBtn_reserver_G.getText().equals("libérer"))
                        {
                            finalEtat_G.setCardBackgroundColor(Color.RED);
                            finalBtn_reserver_G.setBackgroundDrawable(ContextCompat.getDrawable(ReservationActivity.this,R.drawable.btn_valider));
                            finalBtn_reserver_G.setText("réserver");
                        }


                    }
                });

                btn_reserver_D= (Button) parentLayout.findViewById(R.id.btn_place_D);

                btn_reserver_D.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(finalBtn_reserver_D.getText().equals("réserver"))
                        {
                            finlEtat_D.setCardBackgroundColor(Color.GREEN);
                            finalBtn_reserver_D.setBackgroundDrawable(ContextCompat.getDrawable(ReservationActivity.this,R.drawable.btn_rejetter));
                            finalBtn_reserver_D.setText("libérer");
                        }
                        else if(finalBtn_reserver_D.getText().equals("libérer"))
                        {
                            finlEtat_D.setCardBackgroundColor(Color.RED);
                            finalBtn_reserver_D.setBackgroundDrawable(ContextCompat.getDrawable(ReservationActivity.this,R.drawable.btn_valider));
                            finalBtn_reserver_D.setText("réserver");
                        }

                    }
                });
                frameplaces.addView(parentLayout,params);
                i++;
                j=j+2;
            }





            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,i*350,0,0);
            FrameLayout parentLayout= new FrameLayout(this);
            palce_G.setVisibility(View.INVISIBLE);

            palce_G=(CardView) parentLayout.findViewById(R.id.place_G);
            parentLayout= (FrameLayout) LayoutInflater.from(ReservationActivity.this).inflate(R.layout.places,null);
            place_D=(CardView) parentLayout.findViewById(R.id.place_D);
            ((TextView) parentLayout.findViewById(R.id.textvie_num_place_gauche)).setText(String.valueOf(j));
            ((TextView) parentLayout.findViewById(R.id.textvie_num_place_D)).setText(String.valueOf(j+1));
            CardView etat_G= new CardView(ReservationActivity.this);
            CardView etat_D = new CardView(ReservationActivity.this);
            etat_G = (CardView) parentLayout.findViewById(R.id.etat_palce_G);
            etat_D=(CardView) parentLayout.findViewById(R.id.etat_palce_D);


            Button btn_reserver_G = new Button(this);
            Button btn_reserver_D = new Button(this);
            btn_reserver_G= (Button) parentLayout.findViewById(R.id.btn_place_G);
            btn_reserver_D= (Button) parentLayout.findViewById(R.id.btn_place_D);
            final Button finalBtn_reserver_G = btn_reserver_G;
            final Button finalBtn_reserver_D = btn_reserver_D;
            final CardView finalEtat_G = etat_G;
            final CardView finlEtat_D= etat_D;
            btn_reserver_G.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(finalBtn_reserver_G.getText().equals("réserver"))
                    {
                        finalEtat_G.setCardBackgroundColor(Color.GREEN);
                        finalBtn_reserver_G.setBackgroundDrawable(ContextCompat.getDrawable(ReservationActivity.this,R.drawable.btn_rejetter));
                        finalBtn_reserver_G.setText("libérer");
                    }
                    else if(finalBtn_reserver_G.getText().equals("libérer"))
                    {
                        finalEtat_G.setCardBackgroundColor(Color.RED);
                        finalBtn_reserver_G.setBackgroundDrawable(ContextCompat.getDrawable(ReservationActivity.this,R.drawable.btn_valider));
                        finalBtn_reserver_G.setText("réserver");
                    }


                }
            });

            btn_reserver_D= (Button) parentLayout.findViewById(R.id.btn_place_D);

            btn_reserver_D.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(finalBtn_reserver_D.getText().equals("réserver"))
                    {
                        finlEtat_D.setCardBackgroundColor(Color.GREEN);
                        finalBtn_reserver_D.setBackgroundDrawable(ContextCompat.getDrawable(ReservationActivity.this,R.drawable.btn_rejetter));
                        finalBtn_reserver_D.setText("libérer");
                    }
                    else if(finalBtn_reserver_D.getText().equals("libérer"))
                    {
                        finlEtat_D.setCardBackgroundColor(Color.RED);
                        finalBtn_reserver_D.setBackgroundDrawable(ContextCompat.getDrawable(ReservationActivity.this,R.drawable.btn_valider));
                        finalBtn_reserver_D.setText("réserver");
                    }

                }
            });
            frameplaces.addView(parentLayout,params);


        }



    }




}
