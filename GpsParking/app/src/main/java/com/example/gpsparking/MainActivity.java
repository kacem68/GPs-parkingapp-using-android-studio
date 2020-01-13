package com.example.gpsparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class MainActivity extends AppCompatActivity {


    private Button btn;
    private EditText mail;
    private EditText pass;
    private String role;

    private TextView inscription;
    long m;
    String s="iiiii";
    Boolean valide=false;
    DataBase dataBase = new DataBase(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.btn);
        mail=(EditText)findViewById(R.id.mail); pass=(EditText)findViewById(R.id.pass);
        inscription=(TextView)findViewById(R.id.inscrire);
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              inscrire();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Seconnecter();
            }
        });

    }

    public void inscrire()
    {
        Intent intent= new Intent(this, InscriptionActivity.class);
        startActivity(intent);
    }

    public void Seconnecter()
    {
        valide=false;
        String email= mail.getText().toString();
        String password = pass.getText().toString();
        String nom="",prenom="";
        ArrayList<HashMap<String,String>> listUser= new ArrayList<>();
        listUser= dataBase.getListUsers();
        for(int i=0;i<listUser.size();i++)
        {
            final HashMap<String,String> temdata=(HashMap<String ,String>) listUser.get(i);
            Set<String> key = temdata.keySet();
            Iterator it = key.iterator();
            if(temdata.get("email").equals(email) && temdata.get("password").equals(password))
            {
                valide=true;
                role=temdata.get("role");
                nom=temdata.get("nom");
                prenom=temdata.get("prenom");
                break;
            }
        }
        if(valide)
        {
            if(role.equals("admin"))
            {
                Intent intent= new Intent(this, DashBordActivity.class);
                startActivity(intent);
            }
            else if(role.equals("conducteur") )
            {
                Intent intent= new Intent(this, ConducteurActivity.class);
                startActivity(intent);
            }
            else if(role.equals("proprietaire") )
            {
                Intent intent= new Intent(this, DashbordProprietaireActivity.class);
                intent.putExtra("nom",nom);
                intent.putExtra("prenom",prenom);
                startActivity(intent);
            }

        }

        else {
            if(email.equals("admin@gmail.com") && password.equals("admin"))
            {
                Intent intent= new Intent(this, DashBordActivity.class);
                startActivity(intent);
            }
            else {
                ((TextView) findViewById(R.id.echec_connexion)).setVisibility(View.VISIBLE);
            }

        }

    }
}
