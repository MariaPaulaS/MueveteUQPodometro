package com.uniquindio.mueveteuq.activities;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import com.uniquindio.mueveteuq.R;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.uniquindio.mueveteuq.activities.ui.main.SectionsPagerAdapter;
import com.uniquindio.mueveteuq.fragments.InstruccionAyudaFragment;
import com.uniquindio.mueveteuq.fragments.InstruccionBotonesFragment;
import com.uniquindio.mueveteuq.fragments.InstruccionFotosFragment;

public class ContenedorInstruccionesActivity extends AppCompatActivity implements InstruccionAyudaFragment.OnFragmentInteractionListener,
        InstruccionBotonesFragment.OnFragmentInteractionListener, InstruccionFotosFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor_instrucciones);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_contenedor_instrucciones, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}