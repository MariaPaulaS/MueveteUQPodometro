package com.uniquindio.mueveteuq.activities.main;

import android.net.Uri;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import com.uniquindio.mueveteuq.R;

import android.text.Html;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uniquindio.mueveteuq.activities.ui.main.SectionsPagerAdapter;
import com.uniquindio.mueveteuq.fragments.helpZone.InstruccionAyudaFragment;
import com.uniquindio.mueveteuq.fragments.helpZone.InstruccionBotonesFragment;
import com.uniquindio.mueveteuq.fragments.helpZone.InstruccionFotosFragment;

public class ContenedorInstruccionesActivity extends AppCompatActivity implements InstruccionAyudaFragment.OnFragmentInteractionListener,
        InstruccionBotonesFragment.OnFragmentInteractionListener, InstruccionFotosFragment.OnFragmentInteractionListener{


    private LinearLayout linearPuntos;
    private TextView[] puntosSlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor_instrucciones);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        linearPuntos = findViewById(R.id.idLinearPuntos);
        agregarIndicadorPuntos(0);

        viewPager.addOnPageChangeListener(viewListener);


    }

    private void agregarIndicadorPuntos(int pos) {

        puntosSlide = new TextView[3];
        linearPuntos.removeAllViews();

        for(int i = 0; i<puntosSlide.length; i++){

            puntosSlide[i] = new TextView(this);
            puntosSlide[i].setText(Html.fromHtml("&#8226"));
            puntosSlide[i].setTextSize(35);
            puntosSlide[i].setTextColor(getResources().getColor(R.color.blanco_transparente));
            linearPuntos.addView(puntosSlide[i]);

        }

        if(puntosSlide.length>0){

            puntosSlide[pos].setTextColor(getResources().getColor(R.color.blanco));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            agregarIndicadorPuntos(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_contenedor_instrucciones, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}