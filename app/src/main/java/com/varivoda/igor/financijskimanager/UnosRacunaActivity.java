package com.varivoda.igor.financijskimanager;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.varivoda.igor.financijskimanager.databinding.ActivityUnosRacunaBinding;

import java.util.ArrayList;
import java.util.List;

import ArchitectureComponents.ViewModel;
import tablice.ProizvodiNaRacunu;
import tablice.Racun;

public class UnosRacunaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    private int izabranaGodina = 0, izabranMjesec = 0, izabranDan = 0;
    private int izabraniKupac = -1, izabranaPoslovnica = -1, izabranProdavac = -1;
    private List<String> listaProdavacaIndexi;
    private List<String> listaPoslovnicaIndexi;
    private List<String> listaKupacaIndexi;
    private ArrayList<String> listaProizvoda;
    private ViewModel viewModel;
    public static final int REQUEST_CODE = 717;
    private ActivityUnosRacunaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_unos_racuna);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Unos računa");
            MainActivity.changeToolbarFont(toolbar, this);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                UnosRacunaActivity.this, UnosRacunaActivity.this, 2020, 1, 1);
        binding.odaberiDatum.setOnClickListener(view -> datePickerDialog.show());
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        listaProizvoda =  new ArrayList<>();
        List<String> temp;
        temp = viewModel.vratiSveKupce();
        listaKupacaIndexi = new ArrayList<>();
        List<String> listaKupaca = new ArrayList<>();
        listaKupacaIndexi.add("0");
        for (int i = 0; i < temp.size(); i++) {
            String[] dijelovi = temp.get(i).split("#");
            listaKupacaIndexi.add(dijelovi[0]);
            listaKupaca.add(dijelovi[1]);
        }
        listaKupaca.add(0, "Izaberite kupca:");
        temp = viewModel.vratiSveZaposlenike();
        listaProdavacaIndexi = new ArrayList<>();
        List<String> listaProdavaca = new ArrayList<>();
        listaProdavacaIndexi.add("0");
        for (int i = 0; i < temp.size(); i++) {
            String[] dijelovi = temp.get(i).split("#");
            listaProdavacaIndexi.add(dijelovi[0]);
            listaProdavaca.add(dijelovi[1]);
        }
        listaProdavaca.add(0, "Izaberite prodavača:");

        temp = viewModel.prikaziSvePoslovnice();
        List<String> listaPoslovnica = new ArrayList<>();
        listaPoslovnicaIndexi = new ArrayList<>();
        listaPoslovnicaIndexi.add("0");

        for (int i = 0; i < temp.size(); i++) {
            String[] dijelovi = temp.get(i).split("#", 2);
            listaPoslovnicaIndexi.add(dijelovi[0]);
            listaPoslovnica.add(dijelovi[1]);
        }
        listaPoslovnica.add(0, "Izaberite poslovnicu:");

        ArrayAdapter<String> adapterKupci = new ArrayAdapter<>(this, R.layout.spinner_item, listaKupaca);
        adapterKupci.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerIzaberiKupca.setAdapter(adapterKupci);

        ArrayAdapter<String> adapterProdavaci = new ArrayAdapter<>(this, R.layout.spinner_item, listaProdavaca);
        adapterProdavaci.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerIzaberiProdavaca.setAdapter(adapterProdavaci);

        ArrayAdapter<String> adapterPoslovnice = new ArrayAdapter<>(this, R.layout.spinner_item, listaPoslovnica);
        adapterPoslovnice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerIzaberiPoslovnicu.setAdapter(adapterPoslovnice);

        binding.spinnerIzaberiPoslovnicu.setOnItemSelectedListener(UnosRacunaActivity.this);
        binding.spinnerIzaberiProdavaca.setOnItemSelectedListener(UnosRacunaActivity.this);
        binding.spinnerIzaberiKupca.setOnItemSelectedListener(UnosRacunaActivity.this);

        binding.odaberiProizvode.setOnClickListener(view -> {
            Intent intent = new Intent(UnosRacunaActivity.this,BiranjeProizvodaActivity.class);
            startActivityForResult(intent,REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK){
                if(data.getStringArrayListExtra("listaProizvoda") != null) {
                    listaProizvoda = data.getStringArrayListExtra("listaProizvoda");
                    setButtonDrawableColor(binding.odaberiProizvode);
                }
            }
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int god, int mj, int dan) {
        izabranaGodina = god;
        izabranMjesec = mj + 1;
        izabranDan = dan;
        setButtonDrawableColor(binding.odaberiDatum);
    }
    private void setButtonDrawableColor(Button button) {
        for (Drawable drawable : button.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(button.getContext(),R.color.bijela), PorterDuff.Mode.SRC_IN));
            }
        }
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
            if (izabranaGodina != 0 && izabranMjesec != 0 && izabranDan != 0 && izabraniKupac > 0 && izabranaPoslovnica > 0
                    && izabranProdavac > 0 && listaProizvoda.size()>0) {
                String dan = String.valueOf(izabranDan);
                String mjesec = String.valueOf(izabranMjesec);
                if (izabranMjesec < 10 || izabranDan < 10) {
                    if (izabranDan < 10) {
                        dan = "0" + dan;
                    }
                    if (izabranMjesec < 10) {
                        mjesec = "0" + mjesec;
                    }
                }
                String datum = izabranaGodina + "-" + mjesec + "-" + dan;
                viewModel.unesiRacun(new Racun(Integer.valueOf(listaProdavacaIndexi.get(izabranProdavac)), Integer.valueOf(listaPoslovnicaIndexi.get(izabranaPoslovnica)),
                        Integer.valueOf(listaKupacaIndexi.get(izabraniKupac)), datum));
                for(int i =0; i< listaProizvoda.size(); i++){
                    int brojRacuna = viewModel.vratiZadnjiBrojRacuna();
                    int idProizvoda = viewModel.vratiIdProizvoda(listaProizvoda.get(i));
                    viewModel.unesiProizvodiNaRacunu(new ProizvodiNaRacunu(brojRacuna,idProizvoda,1));
                }
                Toast.makeText(this, "Uspješno ste unijeli novi račun!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Molimo Vas, unesite sve vrijednosti!", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        if (parent.getId() == R.id.spinnerIzaberiKupca) {
            izabraniKupac = i;
        } else if (parent.getId() == R.id.spinnerIzaberiProdavaca) {
            izabranProdavac = i;
        } else if (parent.getId() == R.id.spinnerIzaberiPoslovnicu) {
            izabranaPoslovnica = i;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
