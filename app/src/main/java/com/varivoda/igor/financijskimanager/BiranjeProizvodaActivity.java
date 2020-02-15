package com.varivoda.igor.financijskimanager;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ArchitectureComponents.ViewModel;

import static android.widget.AbsListView.CHOICE_MODE_MULTIPLE;
import static com.varivoda.igor.financijskimanager.MainActivity.changeToolbarFont;

public class BiranjeProizvodaActivity extends AppCompatActivity {
    private ListView listView;
    private List<String> listaProizvoda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biranje_proizvoda);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.izaberite_proizvode_res));
            changeToolbarFont(toolbar, this);
        }
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        listaProizvoda = viewModel.vratiSveProizvode();
        String[] niz = new String[listaProizvoda.size()];
        int i = 0;
        for (String s : listaProizvoda) {
            niz[i] = s;
            i++;
        }
        listView = findViewById(R.id.recycler_view);
        listView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, niz));
        listView.setChoiceMode(CHOICE_MODE_MULTIPLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_receipt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.spremiNoviRacun) {
            ArrayList<String> listaProizvodaback = new ArrayList<>();
            SparseBooleanArray checkedPositions = listView.getCheckedItemPositions();
            if (checkedPositions.size() == 0) {
                Toast.makeText(this, "Molimo Vas, izaberite barem 1 proizvod!", Toast.LENGTH_LONG).show();
            } else {
                int count = listView.getCount();
                for (int i = 0; i < count; i++) {
                    if (checkedPositions.get(i)) {
                        listaProizvodaback.add(listaProizvoda.get(i));
                    }
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("listaProizvoda", listaProizvodaback);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
