package com.example.gpsparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class Notation extends AppCompatActivity {

    RadioButton radioButton_1;
    RadioButton radioButton_2;
    RadioButton radioButton_3;
    RadioButton radioButton_4;
    RadioButton radioButton_5;
    Toolbar toolbar;
    String nom_parking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notation);
        Intent intent= getIntent();
        if(intent!=null)
        {
            nom_parking=intent.getStringExtra("nom");
            ((TextView) findViewById(R.id.titre_notation)).setText("Notaion du park :"+nom_parking);
        }


        radioButton_1=(RadioButton) findViewById(R.id.btn_radio_1);
        radioButton_2=(RadioButton) findViewById(R.id.btn_radio_2);
        radioButton_3=(RadioButton) findViewById(R.id.btn_radio_3);
        radioButton_4=(RadioButton) findViewById(R.id.btn_radio_5);
        radioButton_5=(RadioButton) findViewById(R.id.btn_radio_6);


        radioButton_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                noter(radioButton_1);
            }
        });
        radioButton_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                noter(radioButton_2);
            }
        });
        radioButton_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                noter(radioButton_3);
            }
        });        radioButton_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                noter(radioButton_4);
            }
        });        radioButton_5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                noter(radioButton_5);
            }
        });






    }


    public void noter(RadioButton radioButton)
    {
        Toast.makeText(this, "Note attribuée à "+nom_parking+" est "+radioButton.getText().toString(), Toast.LENGTH_LONG).show();
        this.finish();
    }
}
