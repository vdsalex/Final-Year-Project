package com.example.alex.rhymebot.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.alex.rhymebot.Adapter.SavedVersesAdapter;
import com.example.alex.rhymebot.R;

import java.util.ArrayList;
import java.util.List;

public class SavedVersesActivity extends AppCompatActivity
{
    private ArrayList<String> savedVerses;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_verses);

        ListView eminescuListView = (ListView) findViewById(R.id.eminescu_listview);
        ListView bacoviaListView = (ListView) findViewById(R.id.bacovia_listview);
        ListView stanescuListView = (ListView) findViewById(R.id.stanescu_listview);

        SavedVersesAdapter eminescuArrayAdapter = new SavedVersesAdapter(this);
        SavedVersesAdapter bacoviaArrayAdapter = new SavedVersesAdapter(this);
        SavedVersesAdapter stanescuArrayAdapter = new SavedVersesAdapter(this);

        eminescuArrayAdapter.add("Afară-i toamnă, frunza 'mprăştiată,\n" +
                "Iar vântul svârlă 'n geamuri grele picuri;\n" +
                "Şi tu citeşti scrisori din roase plicuri\n" +
                "Şi într'un ceas gândeşti la viaţa toată.");

        eminescuArrayAdapter.add("Pierzându-ţi timpul tău cu dulci nimicuri,\n" +
                "N'ai vrea ca nimeni 'n uşa ta să bată;\n" +
                "Dar şi mai bine-i, când afară-i sloată, \n" +
                "Să stai visând la foc, de somn să picuri.");

        bacoviaArrayAdapter.add("Eram să te aştept prin parc,\n" +
                "Văzând că singurătăţi pe aici m-au oprit...\n" +
                "Dar, mereu aceleaşi uitări!\n" +
                "Dar, tot aceeaşi poezie la infinit!?");

        bacoviaArrayAdapter.add("Filosofia vieţii mi-a zis:\n" +
                "Undeva este, cu mult mai departe...\n" +
                "Atâtea şi-atâtea... lasă!\n" +
                "Visezi, ca din carte!");

        stanescuArrayAdapter.add("E o întâmplare a fiinţei mele\n" +
                "şi atunci fericirea dinlăuntrul meu\n" +
                "e mai puternică decât mine, decât oasele mele,\n" +
                "pe care mi le scrâşneşti într-o îmbrăţişare \n" +
                "mereu dureroasă, minunată mereu.");

        stanescuArrayAdapter.add("Să stăm de vorbă, să vorbim, să spunem cuvinte\n" +
                "lungi, sticloase, ca nişte dălţi ce despart\n" +
                "fluviul rece în delta fierbinte,\n" +
                "ziua de noapte, bazaltul de bazalt.");

        eminescuListView.setAdapter(eminescuArrayAdapter);
        bacoviaListView.setAdapter(bacoviaArrayAdapter);
        stanescuListView.setAdapter(stanescuArrayAdapter);

        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("Tab Eminescu");
        tab1.setContent(R.id.eminescu_tab_layout);
        tab1.setIndicator("Eminescu");

        TabHost.TabSpec tab2 = tabHost.newTabSpec("Tab Bacovia");
        tab2.setContent(R.id.bacovia_tab_layout);
        tab2.setIndicator("Bacovia");

        TabHost.TabSpec tab3 = tabHost.newTabSpec("Tab Stanescu");
        tab3.setContent(R.id.stanescu_tab_layout);
        tab3.setIndicator("Stănescu");

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
    }

    public ArrayList<String> getSavedVerses() { return savedVerses; }
}
