package com.varivoda.igor.financijskimanager;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.varivoda.igor.financijskimanager.databinding.ActivityOptionsBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OptionsActivity extends AppCompatActivity {
    private ActivityOptionsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_options);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.naslovnica);
        }
        MainActivity.changeToolbarFont(toolbar,this);
        Intent i = getIntent();
        int switch_value = i.getIntExtra(MainActivity.INTENT_VALUE,0);
        List<String> lista = new ArrayList<>();
        switch (switch_value){
            case 1:
                lista.clear();
                getSupportActionBar().setTitle(getResources().getString(R.string.statistika_res));
                lista = Arrays.asList(getResources().getStringArray(R.array.statistikaLista));
                break;
            case 2:
                lista.clear();
                getSupportActionBar().setTitle(R.string.racuni_res);
                lista = Arrays.asList(getResources().getStringArray(R.array.racuniLista));
                break;
            case 3:
                lista.clear();
                getSupportActionBar().setTitle(R.string.kupci_res);
                lista = Arrays.asList(getResources().getStringArray(R.array.kupciLista));
                break;
            case 4:
                lista.clear();
                getSupportActionBar().setTitle(R.string.poslovnice_res);
                lista = Arrays.asList(getResources().getStringArray(R.array.poslovniceLista));
                break;
            case 5:
                lista.clear();
                getSupportActionBar().setTitle(R.string.proizvodi_res);
                lista = Arrays.asList(getResources().getStringArray(R.array.proizvodiLista));
                break;
            case 6:
                lista.clear();
                getSupportActionBar().setTitle(R.string.zaposlenici_res);
                lista = Arrays.asList(getResources().getStringArray(R.array.zaposleniciLista));
                break;

        }


        ListAdapter adapter = new ListAdapter(lista,switch_value);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }

}
