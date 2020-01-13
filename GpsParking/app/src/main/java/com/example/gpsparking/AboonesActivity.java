package com.example.gpsparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ListAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class AboonesActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView Title;
    TableRow row;
    TableLayout tableLayout;

    DataBase dataBase= new DataBase(AboonesActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abbones);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Title= (TextView) toolbar.findViewById(R.id.toolbar_title);
        Title.setText("Liste Abonnés");
        tableLayout= (TableLayout) findViewById(R.id.table_Abonnes);
        ListeAbones();
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
            Intent intent= new Intent(AboonesActivity.this, DashBordActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.demandes){
            Intent intent= new Intent(AboonesActivity.this,DemandesActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id== R.id.proprietaire){
            Intent intent= new Intent(AboonesActivity.this,ProprietaireActivity.class);
            startActivity(intent);
            return  true;

        }
        else if(id== R.id.parking){
            Intent intent= new Intent(AboonesActivity.this,ParkingActivity.class);
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


    public  void  ListeAbones()
    {
        ArrayList<HashMap<String,String>> lisusers = new ArrayList<>();
        lisusers= dataBase.getListUsers();

        for(int i=0;i<lisusers.size();i++)
        {
            final HashMap<String,String> temdata=(HashMap<String ,String>) lisusers.get(i);
            Set<String> key = temdata.keySet();
            Iterator it = key.iterator();
            row=(TableRow) LayoutInflater.from(AboonesActivity.this).inflate(R.layout.row_table_abonnes,null);
            ((TextView) row.findViewById(R.id.num_abbones)).setText(temdata.get("KEY_Users"));
            ((TextView) row.findViewById(R.id.nom_abbones)).setText(temdata.get("nom"));
            ((TextView) row.findViewById(R.id.prenom_abbones)).setText(temdata.get("prenom"));
            ((TextView) row.findViewById(R.id.email_abbones)).setText(temdata.get("email"));
            ((TextView) row.findViewById(R.id.role_abbones)).setText(temdata.get("role"));

            tableLayout.addView(row);

        }

    }
}
