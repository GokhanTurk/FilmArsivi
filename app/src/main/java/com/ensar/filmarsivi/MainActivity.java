package com.ensar.filmarsivi;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ensar on 25.12.2017.
 */

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<HashMap<String, String>> film_liste;
    String film_adlari[];
    int film_idler[];
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Film Arşivi");


        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.turkuaz)));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.koyuturkuaz));
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        Veritabani db = new Veritabani(getApplicationContext());
        film_liste = db.filmler();
        if(film_liste.size()==0){
            Toast.makeText(getApplicationContext(), "Henüz Film Eklenmemiş.\nYukarıdaki + Butonundan Ekleyiniz", Toast.LENGTH_LONG).show();
        }else{
            film_adlari = new String[film_liste.size()];
            film_idler = new int[film_liste.size()];
            for(int i = 0; i< film_liste.size(); i++){
                film_adlari[i] = film_liste.get(i).get("film_adi");
                film_idler[i] = Integer.parseInt(film_liste.get(i).get("id"));
            }
            lv = (ListView) findViewById(R.id.list_view);
            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.film_adi, film_adlari);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {

                    Intent intent = new Intent(getApplicationContext(), filmDetay.class);
                    intent.putExtra("id", film_idler[arg2]);
                    startActivity(intent);

                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.ekle:
                FilmEkle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void FilmEkle() {
        Intent i = new Intent(MainActivity.this, FilmEkle.class);
        startActivity(i);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //geri butonunu yakalıyoruz
        if(keyCode == KeyEvent.KEYCODE_BACK && isTaskRoot()) {
            //Programdan çıkmak isteyip istemediğini soruyoruz
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.quit)
                    .setMessage(R.string.really_quit)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Aktiviteyi durduruyoruz
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.no, null)
                    .show();

            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }
}