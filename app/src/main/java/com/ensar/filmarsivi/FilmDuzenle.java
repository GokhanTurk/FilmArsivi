package com.ensar.filmarsivi;

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
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by ensar on 25.12.2017.
 */

public class FilmDuzenle extends AppCompatActivity {
    Button b1;
    EditText e1,e2,e3,e4;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_duzenle);
        setTitle("Film Düzenle");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.turkuaz)));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.koyuturkuaz));
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Film Düzenle");

        b1 = (Button)findViewById(R.id.button1);
        e1 = (EditText)findViewById(R.id.editText1);
        e2 = (EditText)findViewById(R.id.editText2);
        e3 = (EditText)findViewById(R.id.editText3);
        e4 = (EditText)findViewById(R.id.editText4);

        Intent intent=getIntent();
        id = intent.getIntExtra("id", 0);

        Veritabani db = new Veritabani(getApplicationContext());
        HashMap<String, String> map = db.filmDetay(id);

        e1.setText(map.get("film_adi"));
        e2.setText(map.get("oyuncular").toString());
        e3.setText(map.get("aciklama").toString());
        e4.setText(map.get("yonetmen").toString());

        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String adi,oyuncular,aciklama,yonetmen;
                adi = e1.getText().toString();
                oyuncular = e2.getText().toString();
                aciklama = e3.getText().toString();
                yonetmen = e4.getText().toString();
                if(adi.matches("") || oyuncular.matches("") || aciklama.matches("") || yonetmen.matches("")  ){
                    Toast.makeText(getApplicationContext(), "Tüm Bilgileri Eksiksiz Doldurunuz", Toast.LENGTH_SHORT).show();
                }else{
                    Veritabani db = new Veritabani(getApplicationContext());
                    db.filmDuzenle(adi, oyuncular, aciklama, yonetmen,id);
                    db.close();
                    Toast.makeText(getApplicationContext(), "Film Başarıyla Düzenlendi.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
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
