package com.example.gpsparking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class Layoutes  {

    public View SetLayout(LayoutInflater inflater,CoordinatorLayout layoutparent,View ancienVue,View nouvellevue,TextView title, String titre )
    {
        title.setText(titre);
        if(ancienVue!=null)
        {
            layoutparent.removeView(ancienVue);

        }
        layoutparent.addView(nouvellevue);
        return nouvellevue;
    }

    public View.OnClickListener afficher_demandes= new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };


}
