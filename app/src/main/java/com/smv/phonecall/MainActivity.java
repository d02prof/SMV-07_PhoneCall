package com.smv.phonecall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private static final int REQUEST_CALL = 1;

    private EditText editTextStevilka;
    private ImageView imageViewPoklici;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextStevilka = findViewById(R.id.editTextStevilka);
        imageViewPoklici = findViewById(R.id.imageViewTelefon);

        imageViewPoklici.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Telefoniraj();
            }
        });
    }

    public void Telefoniraj()
    {
        String stevilka = editTextStevilka.getText().toString();

        //če je vpisana številka
        if (stevilka.trim().length() > 0)
        {
            //preverimo če imamo pravice za klicanje
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            {
                //če jih še nimamo
                //lahko bi zahtevali več dovoljenj, zato polje stringov, v tem primeru rabim ole eno
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }
            else
            {
                //če jih že imamo
                String poklici = "tel:" + stevilka;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(poklici)));
            }
        }
        else Toast.makeText(this, "Vpiši številko", Toast.LENGTH_SHORT).show();
    }

    //metoda, ki obdela rezultat zahteve za dovoljenja (lahko jih je več, tokrat imamo samo eno)

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //će gre za dovoljenje za klicanje
        if (requestCode == REQUEST_CALL)
        {
            //če smo za klicanje dobili odobritev
            //takole je v tutorialu, ampak ne vidim razloga, zakaj je treba preverjati dolžino polja
            //if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Telefoniraj();
            else
                Toast.makeText(this, "Aplikacija nima dovoljenja za klic", Toast.LENGTH_SHORT).show();
        }
    }
}