package com.ensar.filmarsivi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ensar on 25.12.2017.
 */

public class Veritabani extends SQLiteOpenHelper {


 private static final int DATABASE_VERSION = 1;


private static final String DATABASE_NAME = "Film_db";
private static final String TABLE_NAME = "Film_Table";
private static String FILM_ADI = "film_adi";
private static String FILM_ID = "id";
private static String OYUNCULAR = "oyuncular";
private static String ACİKLAMA = "aciklama";
private static String YONETMEN = "yonetmen";

public Veritabani(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + FILM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FILM_ADI + " TEXT,"
            + OYUNCULAR + " TEXT,"
            + ACİKLAMA + " TEXT,"
            + YONETMEN + " TEXT" + ")";
            db.execSQL(CREATE_TABLE);
        }


        public void filmSil(int id) {

            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME, FILM_ID + " = ?",
                    new String[]{String.valueOf(id)});
            db.close();
        }

        public void filmEkle(String film_adi, String oyuncular, String aciklama, String yonetmen) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FILM_ADI, film_adi);
            values.put(OYUNCULAR, oyuncular);
            values.put(ACİKLAMA, aciklama);
            values.put(YONETMEN, yonetmen);

            db.insert(TABLE_NAME, null, values);
            db.close();
        }


        public HashMap<String, String> filmDetay(int id) {

            HashMap<String, String> film = new HashMap<String, String>();
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE id=" + id;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                film.put(FILM_ADI, cursor.getString(1));
                film.put(OYUNCULAR, cursor.getString(2));
                film.put(ACİKLAMA, cursor.getString(3));
                film.put(YONETMEN, cursor.getString(4));
            }
            cursor.close();
            db.close();
            return film;
        }

        public ArrayList<HashMap<String, String>> filmler() {

            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(selectQuery, null);
            ArrayList<HashMap<String, String>> filmlist = new ArrayList<HashMap<String, String>>();

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<String, String>();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        map.put(cursor.getColumnName(i), cursor.getString(i));
                    }

                    filmlist.add(map);
                } while (cursor.moveToNext());
            }
            db.close();
            return filmlist;
        }

        public void filmDuzenle(String film_adi, String oyuncular, String aciklama, String yonetmen, int id) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FILM_ADI,film_adi);
            values.put(OYUNCULAR, oyuncular);
            values.put(ACİKLAMA, aciklama);
            values.put(YONETMEN, yonetmen);

            db.update(TABLE_NAME, values, FILM_ID + " = ?",
                    new String[]{String.valueOf(id)});
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub

        }
    }

