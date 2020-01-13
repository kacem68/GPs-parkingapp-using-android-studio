package com.example.gpsparking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBase extends SQLiteOpenHelper {


    public DataBase(Context context) {
        super(context, "dbparking", null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        cretaTbalePRoprietaire(sqLiteDatabase);
        cretaTbaleParking(sqLiteDatabase);
        cretaTbaleUsers(sqLiteDatabase);
        cretaTbaleDemandes(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS table_proprietaire");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS table_parking");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS table_users");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS table_demande");

        onCreate(sqLiteDatabase);
    }

    public void cretaTbalePRoprietaire(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE table_proprietaire ( KEY_ID INTEGER PRIMARY  KEY AUTOINCREMENT, nom TEXT, prenom TEXT,adress TEXT,cin TEXT)";
        db.execSQL(CREATE_TABLE);
    }


    long insertProprietaireDetails(String nom, String prenom, String adress, String cin) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put("nom", nom);
        cValues.put("prenom", prenom);
        cValues.put("adress", adress);
        cValues.put("cin", cin);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("table_proprietaire", null, cValues);
        db.close();
        return newRowId;
    }

    public ArrayList<HashMap<String, String>> getListPropriets() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> proprietairesList = new ArrayList<>();
        String query = "SELECT * from table_proprietaire ";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> proprietaire = new HashMap<>();
            proprietaire.put("KEY_ID", cursor.getString(cursor.getColumnIndex("KEY_ID")));
            proprietaire.put("nom", cursor.getString(cursor.getColumnIndex("nom")));
            proprietaire.put("prenom", cursor.getString(cursor.getColumnIndex("prenom")));
            proprietaire.put("adress", cursor.getString(cursor.getColumnIndex("adress")));
            proprietaire.put("cin", cursor.getString(cursor.getColumnIndex("cin")));
            proprietairesList.add(proprietaire);
        }

        return proprietairesList;
    }
    public ArrayList<HashMap<String, String>> getListPropriets(String nom, String prenom) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> proprietairesList = new ArrayList<>();
        String query = "SELECT * from table_proprietaire where nom like '"+nom+"'"+" and prenom like '"+prenom+"'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> proprietaire = new HashMap<>();
            proprietaire.put("KEY_ID", cursor.getString(cursor.getColumnIndex("KEY_ID")));
            proprietaire.put("nom", cursor.getString(cursor.getColumnIndex("nom")));
            proprietaire.put("prenom", cursor.getString(cursor.getColumnIndex("prenom")));
            proprietaire.put("adress", cursor.getString(cursor.getColumnIndex("adress")));
            proprietaire.put("cin", cursor.getString(cursor.getColumnIndex("cin")));
            proprietairesList.add(proprietaire);
        }

        return proprietairesList;
    }


    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("table_proprietaire", null, null);
        db.close();
    }

    public void deleteoneProprietaire(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("table_proprietaire", "KEY_ID=?", new String[]{String.valueOf(id)});

    }


    ////////////////////////////////////////   Table parking

    public void cretaTbaleParking(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE table_parking ( Num_parking INTEGER PRIMARY  KEY AUTOINCREMENT, designation TEXT, key_proprietaire TEXT, longitude REAL,Latitude REAL,date_ajout date,places INTEGER,valide INTEGER,FOREIGN KEY(key_proprietaire) REFERENCES table_proprietaire(cin) )";
        db.execSQL(CREATE_TABLE);
    }

    long  insertParkingDetails(String designation,int places, String proprietaire, double longitude,double Latitude, Date date_ajout,int valide ) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put("designation", designation);
        cValues.put("key_proprietaire", proprietaire);
        cValues.put("longitude", longitude);
        cValues.put("Latitude", Latitude);
        cValues.put("places", places);

        cValues.put("date_ajout", String.valueOf(date_ajout));
        cValues.put("valide", valide);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("table_parking", null, cValues);
        db.close();
        return  newRowId;
    }


    long  updateParkingDetails(int num_parking,String designation,int places, String cin, double longitude,double Latitude, Date date_ajout,int valide ) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put("designation", designation);
        cValues.put("key_proprietaire", cin);
        cValues.put("longitude", longitude);
        cValues.put("Latitude", Latitude);
        cValues.put("places", places);

        cValues.put("date_ajout", String.valueOf(date_ajout));
        cValues.put("valide", valide);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.update("table_parking", cValues,"Num_parking=?", new String[]{String.valueOf(num_parking)});
        db.close();
        return  newRowId;
    }



    public ArrayList<HashMap<String, String>> getListParking() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> parkinList = new ArrayList<>();
        String query = "SELECT * from table_parking where valide=1 ";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> parking = new HashMap<>();
            parking.put("Num_parking", cursor.getString(cursor.getColumnIndex("Num_parking")));
            parking.put("designation", cursor.getString(cursor.getColumnIndex("designation")));
            parking.put("places", cursor.getString(cursor.getColumnIndex("places")));
            parking.put("key_proprietaire", cursor.getString(cursor.getColumnIndex("key_proprietaire")));
            parking.put("longitude", cursor.getString(cursor.getColumnIndex("longitude")));
            parking.put("Latitude", cursor.getString(cursor.getColumnIndex("Latitude")));
            parking.put("date_ajout", cursor.getString(cursor.getColumnIndex("date_ajout")));
            parking.put("valide", cursor.getString(cursor.getColumnIndex("valide")));
            parkinList.add(parking);
        }
        return parkinList;
    }
    public ArrayList<HashMap<String, String>> getListParking(String cin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> parkinList = new ArrayList<>();
        String query = "SELECT * from table_parking where valide=1 and key_proprietaire like '"+cin+"'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> parking = new HashMap<>();
            parking.put("Num_parking", cursor.getString(cursor.getColumnIndex("Num_parking")));
            parking.put("designation", cursor.getString(cursor.getColumnIndex("designation")));
            parking.put("places", cursor.getString(cursor.getColumnIndex("places")));
            parking.put("key_proprietaire", cursor.getString(cursor.getColumnIndex("key_proprietaire")));
            parking.put("longitude", cursor.getString(cursor.getColumnIndex("longitude")));
            parking.put("Latitude", cursor.getString(cursor.getColumnIndex("Latitude")));
            parking.put("date_ajout", cursor.getString(cursor.getColumnIndex("date_ajout")));
            parking.put("valide", cursor.getString(cursor.getColumnIndex("valide")));
            parkinList.add(parking);
        }
        return parkinList;
    }

    public ArrayList<HashMap<String, String>> getListParkingNonValide() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> parkinList = new ArrayList<>();
        String query = "SELECT * from table_parking where valide=0 ";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> parking = new HashMap<>();
            parking.put("Num_parking", cursor.getString(cursor.getColumnIndex("Num_parking")));
            parking.put("designation", cursor.getString(cursor.getColumnIndex("designation")));
            parking.put("places", cursor.getString(cursor.getColumnIndex("places")));

            parking.put("key_proprietaire", cursor.getString(cursor.getColumnIndex("key_proprietaire")));
            parking.put("longitude", cursor.getString(cursor.getColumnIndex("longitude")));
            parking.put("Latitude", cursor.getString(cursor.getColumnIndex("Latitude")));
            parking.put("date_ajout", cursor.getString(cursor.getColumnIndex("date_ajout")));
            parking.put("valide", cursor.getString(cursor.getColumnIndex("valide")));
            parkinList.add(parking);
        }
        return parkinList;
    }

    public ArrayList<HashMap<String, String>> getListParkingByid(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> parkinList = new ArrayList<>();
        String query = "SELECT * from table_parking where Num_parking="+id;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> parking = new HashMap<>();
            parking.put("Num_parking", cursor.getString(cursor.getColumnIndex("Num_parking")));
            parking.put("designation", cursor.getString(cursor.getColumnIndex("designation")));
            parking.put("places", cursor.getString(cursor.getColumnIndex("places")));

            parking.put("key_proprietaire", cursor.getString(cursor.getColumnIndex("key_proprietaire")));
            parking.put("longitude", cursor.getString(cursor.getColumnIndex("longitude")));
            parking.put("Latitude", cursor.getString(cursor.getColumnIndex("Latitude")));
            parking.put("date_ajout", cursor.getString(cursor.getColumnIndex("date_ajout")));
            parking.put("valide", cursor.getString(cursor.getColumnIndex("valide")));
            parkinList.add(parking);
        }
        return parkinList;
    }

    public void deleteAllParking() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("table_parking", null, null);
        db.close();
    }

    public void deleteoneParkin(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("table_parking", "Num_parking=?", new String[]{String.valueOf(id)});

    }
    public int ValiderPArking(Integer Num_parking){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put("valide", 1);
        int count = db.update("table_parking", cVals, "Num_parking =?",new String[]{String.valueOf(Num_parking)});
        return  count;
    }
    public int RejetterPArking(Integer Num_parking){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put("valide", 2);
        int count = db.update("table_parking", cVals, "Num_parking =?",new String[]{String.valueOf(Num_parking)});
        return  count;
    }

    ////// users
    public void cretaTbaleUsers(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE table_users ( KEY_Users INTEGER PRIMARY  KEY AUTOINCREMENT, nom TEXT, prenom TEXT, email TEXT,password TEXT,role TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    long insertUser(String nom, String prenom, String email, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put("nom", nom);
        cValues.put("prenom", prenom);
        cValues.put("email", email);
        cValues.put("password", password);
        cValues.put("role", role);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("table_users", null, cValues);
        db.close();
        return newRowId;
    }
    public ArrayList<HashMap<String, String>> getListUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> UserList = new ArrayList<>();
        String query = "SELECT * from table_users";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("KEY_Users", cursor.getString(cursor.getColumnIndex("KEY_Users")));
            user.put("nom", cursor.getString(cursor.getColumnIndex("nom")));
            user.put("prenom", cursor.getString(cursor.getColumnIndex("prenom")));
            user.put("email", cursor.getString(cursor.getColumnIndex("email")));
            user.put("password", cursor.getString(cursor.getColumnIndex("password")));
            user.put("role", cursor.getString(cursor.getColumnIndex("role")));
            UserList.add(user);
        }
        return UserList;
    }
    public void deleteAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("table_users", null, null);
        db.close();
    }


    //**************************************** table demandes
    public void cretaTbaleDemandes(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE table_demande ( num_demande INTEGER PRIMARY  KEY AUTOINCREMENT, motif TEXT, parking TEXT,traite INTEGER )";
        db.execSQL(CREATE_TABLE);
    }

    public void deleteoneDemande(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("table_demande", "num_demande=?", new String[]{String.valueOf(id)});

    }
    public ArrayList<HashMap<String, String>> getListDemande() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> UserList = new ArrayList<>();
        String query = "SELECT * from table_demande where traite=0";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("num_demande", cursor.getString(cursor.getColumnIndex("num_demande")));
            user.put("motif", cursor.getString(cursor.getColumnIndex("motif")));
            user.put("parking", cursor.getString(cursor.getColumnIndex("parking")));
            user.put("traite", cursor.getString(cursor.getColumnIndex("traite")));
            UserList.add(user);
        }
        return UserList;
    }
    public ArrayList<HashMap<String, String>> getListDemandeTraitees() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> UserList = new ArrayList<>();
        String query = "SELECT * from table_demande where traite=1";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("num_demande", cursor.getString(cursor.getColumnIndex("num_demande")));
            user.put("motif", cursor.getString(cursor.getColumnIndex("motif")));
            user.put("parking", cursor.getString(cursor.getColumnIndex("parking")));
            user.put("traite", cursor.getString(cursor.getColumnIndex("traite")));
            UserList.add(user);
        }
        return UserList;
    }

    public int ValiderDemande(Integer Num_demande){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put("traite", 1);
        int count = db.update("table_demande", cVals, "num_demande =?",new String[]{String.valueOf(Num_demande)});
        return  count;
    }

    long  insertDemande(String motif,int num_parking ) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put("motif", motif);
        cValues.put("parking", num_parking);
        cValues.put("traite", 0);
        long newRowId = db.insert("table_demande", null, cValues);
        db.close();
        return  newRowId;
    }



}

