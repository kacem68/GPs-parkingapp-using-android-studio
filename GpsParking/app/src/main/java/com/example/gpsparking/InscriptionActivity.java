package com.example.gpsparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class InscriptionActivity extends AppCompatActivity {
    private EditText nom,prenom,email,password;
    private Button btn;
    private TextView message;
    private  long m;
    private String type;
    Boolean exist=false;
    Boolean ok=false;
    DataBase dataBase = new DataBase(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
       nom=(EditText)findViewById(R.id.nom);
       prenom=(EditText)findViewById(R.id.prenom);
       email=(EditText)findViewById(R.id.email);
       password=(EditText)findViewById(R.id.motdepasse);
       btn=(Button)findViewById(R.id.btntInscrire);
       message=(TextView) findViewById(R.id.messageerror);
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ajouter();
           }
       });
    }
    public void ajouter(){
        exist=false;
        ArrayList<HashMap<String,String>> listUser= new ArrayList<>();
        listUser= dataBase.getListUsers();
        System.out.println("Apres Ajout");
        String inNom,inPassword,inmail,inprenom;
        inmail=email.getText().toString();
        inprenom=prenom.getText().toString();
        inPassword=password.getText().toString();
        inNom=nom.getText().toString();

        if(inNom.isEmpty() || inprenom.isEmpty() || inPassword.isEmpty() || inmail.isEmpty() || type.isEmpty() )
        {
            message.setText("remplir les champs");
        }
        else {

            for(int i=0;i<listUser.size();i++)
            {
                final HashMap<String,String> temdata=(HashMap<String ,String>) listUser.get(i);
                Set<String> key = temdata.keySet();
                Iterator it = key.iterator();
                if(temdata.get("email").equals(inmail))
                {
                    exist=true;
                    break;
                }
            }
            if(exist){
                message.setText("email existant");
            }
            else {
                if(dataBase.insertUser(inNom,inprenom,inmail,inPassword,type)>0)
                {
                    Intent intent= new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent= new Intent(this, InscriptionActivity.class);
                    startActivity(intent);
                }
            }



        }



    }
    public void  verifier(){
        System.out.println("Avant verifier");

    }

    public void onRadioButtonClicked(View view){

        boolean checked=((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.choix1:
                if(checked)  type="conducteur";break;
            case R.id.choix2:
                if(checked)  type="proprietaire";break;

        }
    }
}
