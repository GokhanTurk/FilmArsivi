package com.ensar.filmarsivi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by ensar on 25.12.2017.
 */

public class filmDetay extends AppCompatActivity{
    Button b1,b2;
    TextView t1,t2,t3,t4;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_detay);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.turkuaz)));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.koyuturkuaz));
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Film Detayları");

        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);

        t1 = (TextView)findViewById(R.id.adi);
        t2 = (TextView)findViewById(R.id.oyuncular);
        t3 = (TextView)findViewById(R.id.aciklama);
        t4 = (TextView)findViewById(R.id.yonetmen);

        Intent intent=getIntent();
        id = intent.getIntExtra("id", 0);

        Veritabani db = new Veritabani(getApplicationContext());
        HashMap<String, String> map = db.filmDetay(id);

        t1.setText(map.get("film_adi"));
        t2.setText(map.get("oyuncular").toString());
        t3.setText(map.get("aciklama").toString());
        t4.setText(map.get("yonetmen").toString());


        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FilmDuzenle.class);
                intent.putExtra("id", (int)id);
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(filmDetay.this);
                alertDialog.setTitle("Uyarı");
                alertDialog.setMessage("Film Silinsin mi?");
                alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Veritabani db = new Veritabani(getApplicationContext());
                        db.filmSil(id);
                        Toast.makeText(getApplicationContext(), "Film Başarıyla Silindi", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
                alertDialog.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                    }
                });
                alertDialog.show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}


