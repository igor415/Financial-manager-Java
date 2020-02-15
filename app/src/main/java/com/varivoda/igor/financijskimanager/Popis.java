package com.varivoda.igor.financijskimanager;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.varivoda.igor.financijskimanager.databinding.ActivityPopisBinding;

import java.util.Arrays;
import java.util.List;

import ArchitectureComponents.ViewModel;
import tablice.Kupac;
import tablice.Prodavac;
import tablice.Proizvod;

import static com.varivoda.igor.financijskimanager.MainActivity.changeToolbarFont;

public class Popis extends AppCompatActivity {
    private ViewModel viewModel;
    private EditText kupacIme;
    private EditText kupacPrezime;
    private EditText kupacAdresa;
    private NumberPicker postanski_broj_picker;
    private int switch_value;
    private ActivityPopisBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_popis);
        switch_value = getIntent().getIntExtra(MainActivity.INTENT_VALUE,0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle(getIntent().getStringExtra(ListAdapter.NASLOV));
        }
        changeToolbarFont(toolbar,this);
        final PopisAdapterKupac popisAdapterKupac = new PopisAdapterKupac();
        final popisAdapterString popisAdapterString = new popisAdapterString();
        final PopisAdapterZaposlenik popisAdapterZaposlenik = new PopisAdapterZaposlenik();
        final PopisAdapterProizvod popisAdapterProizvod = new PopisAdapterProizvod();
        final PopisAdapterPoslovnica popisAdapterPoslovnica = new PopisAdapterPoslovnica();
        binding.recyclerViewPopis.setLayoutManager(new LinearLayoutManager(this));
        switch(switch_value){
            case 20:
                binding.recyclerViewPopis.setAdapter(popisAdapterString);
                String month=getIntent().getStringExtra("month");
                String year=getIntent().getStringExtra("year");
                viewModel = ViewModelProviders.of(this).get(ViewModel.class);
                viewModel.popisSvihRacunaPomjesecuPoGodini(month,year).observe(this, popisAdapterString::submitList);
                break;
            case 30:
                binding.recyclerViewPopis.setAdapter(popisAdapterKupac);
                viewModel = ViewModelProviders.of(this).get(ViewModel.class);
                viewModel.prikaziSveKupce().observe(this, popisAdapterKupac::submitList);
                break;
            case 40:
                binding.recyclerViewPopis.setAdapter(popisAdapterPoslovnica);
                viewModel = ViewModelProviders.of(this).get(ViewModel.class);
                viewModel.prikaziSvePoslovniceLive().observe(this, popisAdapterPoslovnica::submitList);
                break;
            case 50:
                binding.recyclerViewPopis.setAdapter(popisAdapterProizvod);
                viewModel = ViewModelProviders.of(this).get(ViewModel.class);
                viewModel.prikaziSveProizvode().observe(this, popisAdapterProizvod::submitList);
                break;
            case 60:
                binding.recyclerViewPopis.setAdapter(popisAdapterZaposlenik);
                viewModel = ViewModelProviders.of(this).get(ViewModel.class);
                viewModel.prikaziSveZaposlenike().observe(this, popisAdapterZaposlenik::submitList);
                break;
        }


        if(switch_value == 30 || switch_value == 50 || switch_value ==60) {
            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    switch (switch_value) {
                        case 30:
                            viewModel.obrisiKupca(popisAdapterKupac.getItemAt(viewHolder.getAdapterPosition()));
                            break;
                        case 40:

                            break;
                        case 50:
                            viewModel.obrisiProizvod(popisAdapterProizvod.getItemAt(viewHolder.getAdapterPosition()));
                            break;
                        case 60:
                            viewModel.obrisiZaposlenika(popisAdapterZaposlenik.getItemAt(viewHolder.getAdapterPosition()));

                            break;
                    }
                }
            }).attachToRecyclerView(binding.recyclerViewPopis);
        }
        switch(switch_value){
            case 30:
                popisAdapterKupac.setOnKupacClickListener(kupac -> {
                    dialogEnum dialog_enum = dialogEnum.update;
                    dodajKupcaDialog(dialog_enum,kupac);

                });
                break;
            case 40:
                popisAdapterPoslovnica.setOnPoslovnicaClickListener(poslovnica -> {
                    //dialogEnum dialog_enum = dialogEnum.update;
                    //dodajProizvod(dialog_enum,proizvod);
                });
                break;
            case 50:
                popisAdapterProizvod.setOnProizvodClickListener(proizvod -> {
                    dialogEnum dialog_enum = dialogEnum.update;
                    dodajProizvod(dialog_enum,proizvod);
                });
                break;
            case 60:
                popisAdapterZaposlenik.setOnZaposlenikClickListener(prodavac -> {
                    dialogEnum dialog_enum = dialogEnum.update;
                    dodajProdavacaDialog(dialog_enum,prodavac);
                });
                break;
        }

    }
    private void dodajProizvod(dialogEnum dialogEnum1,Proizvod proizvod){
        AlertDialog.Builder builder = new AlertDialog.Builder(Popis.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_dodaj_kupca,new LinearLayout(Popis.this),false);
        EditText nazivProizvod = dialogView.findViewById(R.id.kupacIme);
        TextView naslov = dialogView.findViewById(R.id.naslov_popup_dodaj_kupca);
        naslov.setText(R.string.proizvod_naslov_res);
        naslov.setGravity(Gravity.CENTER_HORIZONTAL);
        nazivProizvod.setHint(R.string.naziv_proizvoda_res);
        EditText cijena = dialogView.findViewById(R.id.kupacPrezime);
        cijena.setHint("cijena");
        cijena.setInputType(InputType.TYPE_CLASS_NUMBER);
        dialogView.findViewById(R.id.postanskiBrojLabel).setVisibility(View.INVISIBLE);
        dialogView.findViewById(R.id.kupacAdresa).setVisibility(View.INVISIBLE);
        dialogView.findViewById(R.id.kupacPBR).setVisibility(View.INVISIBLE);
        dialogView.findViewById(R.id.kupacPBR1).setVisibility(View.INVISIBLE);
        Button spremi = dialogView.findViewById(R.id.spremiKupca);
        ImageView izlaz_dodaj_proizvod = dialogView.findViewById(R.id.izlaz_dodaj_kupca);
        if(dialogEnum1== dialogEnum.update) {
            naslov.setText(R.string.promijenite_podatke_o_proizvodu_res);
            nazivProizvod.setText(proizvod.getNazivProizvod());
            cijena.setText(String.valueOf(proizvod.getCijena()));
        }
        builder.setView(dialogView);
        final AlertDialog dialog;
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        izlaz_dodaj_proizvod.setOnClickListener(v -> dialog.dismiss());
        spremi.setOnClickListener(v -> {
            String naziv = nazivProizvod.getText().toString();
            int cij = Integer.valueOf(cijena.getText().toString());
            if(naziv.trim().isEmpty() || cij <=0){
                Toast.makeText(Popis.this, R.string.toast_msg, Toast.LENGTH_LONG).show();
                return;
            }
            if(dialogEnum1==dialogEnum.insert) {
                Proizvod proizvod1 = new Proizvod(naziv,cij);
                viewModel.unesiProizvod(proizvod1);
                dialog.dismiss();
            }else if(dialogEnum1==dialogEnum.update){
                Proizvod proizvod1 = new Proizvod(naziv,cij);
                proizvod1.setId(proizvod.getId());
                viewModel.promijeniPodatkeProizvoda(proizvod1);
                dialog.dismiss();
            }
        });
        if(dialog.getWindow()!=null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.show();

    }

    private void dodajProdavacaDialog(dialogEnum dialog_enum, Prodavac prodavac) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Popis.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_dodaj_kupca,new LinearLayout(Popis.this),false);
        TextView t = dialogView.findViewById(R.id.naslov_popup_dodaj_kupca);
        t.setText(R.string.novi_zaposlenik_res);
        t.setGravity(Gravity.CENTER_HORIZONTAL);
        EditText prodavacIme = dialogView.findViewById(R.id.kupacIme);
        prodavacIme.setHint(R.string.ime_prodavaca_res);
        EditText prodavacPrezime = dialogView.findViewById(R.id.kupacPrezime);
        prodavacPrezime.setHint(R.string.prezime_prodavaca_res);
        EditText prodavacAdresa = dialogView.findViewById(R.id.kupacAdresa);
        EditText idPoslovnice = dialogView.findViewById(R.id.kupacPBR);
        idPoslovnice.setHint(R.string.id_poslovnice_res);
        postanski_broj_picker = dialogView.findViewById(R.id.kupacPBR1);

        //pretvorba liste stringova u niz stringova
        List<String> stringList = Arrays.asList(getResources().getStringArray(R.array.postanskiBrojevi));
        postanski_broj_picker.setMinValue(0);
        postanski_broj_picker.setMaxValue(stringList.size()-1);
        String[] niz = new String[stringList.size()];
        for(int i=0; i< stringList.size(); i++){
            niz[i] = String.valueOf(stringList.get(i));
        }
        postanski_broj_picker.setDisplayedValues(niz);
        Button spremiProdavaca = dialogView.findViewById(R.id.spremiKupca);
        ImageView izlaz_dodaj_prodavaca = dialogView.findViewById(R.id.izlaz_dodaj_kupca);
        if(dialog_enum==dialogEnum.update) {
            //TextView naslov_popup_dodaj_prodavaca = dialogView.findViewById(R.id.naslov_popup_dodaj_kupca);
            t.setText(R.string.unesite_podatke_o_prodavacu_res);
            prodavacIme.setText(prodavac.getImeProdavac());
            prodavacPrezime.setText(prodavac.getPrezimeProdavac());
            prodavacIme.setGravity(Gravity.CENTER_HORIZONTAL);
            prodavacPrezime.setGravity(Gravity.CENTER_HORIZONTAL);
            if(prodavac.getAdresa() == null){
                prodavacAdresa.setText("");
            }else{
                prodavacAdresa.setText(prodavac.getAdresa());
            }
            idPoslovnice.setText(String.valueOf(prodavac.getIdPoslovnica()));
            idPoslovnice.setGravity(Gravity.CENTER_HORIZONTAL);
            for(int x=0; x<niz.length; x++){
                if(String.valueOf(prodavac.getPbrMjesto()).equals(niz[x])){
                    postanski_broj_picker.setValue(x);
                }
            }
        }
        builder.setView(dialogView);
        final AlertDialog dialog;
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        izlaz_dodaj_prodavaca.setOnClickListener(v -> dialog.dismiss());
        spremiProdavaca.setOnClickListener(v -> {
            String ime = prodavacIme.getText().toString();
            String prezime = prodavacPrezime.getText().toString();
            String adresa = prodavacAdresa.getText().toString();
            int idPos=-1;
            if(idPoslovnice.getText().toString().trim().length()>0){
                idPos = Integer.valueOf(idPoslovnice.getText().toString());
            }

            int pbr;
            pbr = postanski_broj_picker.getValue();
            if(ime.trim().isEmpty() || prezime.trim().isEmpty() || adresa.trim().isEmpty() || idPos==-1){
                Toast.makeText(Popis.this, "Molimo vas, ispunite sva polja", Toast.LENGTH_LONG).show();
                return;
            }
            if(dialog_enum==dialogEnum.insert) {
                Prodavac prodavac1 = new Prodavac(ime, prezime, adresa, idPos, Integer.valueOf(niz[pbr]));
                viewModel.unesiZaposlenika(prodavac1);
                dialog.dismiss();
            }else if(dialog_enum==dialogEnum.update){
                Prodavac p = new Prodavac(ime,prezime,adresa,idPos,Integer.valueOf(niz[pbr]));
                p.setId(prodavac.getId());
                viewModel.promijeniPodatkeZaposlenika(p);
                dialog.dismiss();
            }
        });
        if(dialog.getWindow()!=null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.popis_menu,menu);
        if(switch_value==40 || switch_value==20){
            menu.findItem(R.id.dodaj_kupca).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.dodaj_kupca){
            dialogEnum de = dialogEnum.insert;
            switch(switch_value){
                case 30:
                    dodajKupcaDialog(de,null);
                    break;
                case 40:
                    //dodajKupcaDialog(de,null);
                    break;
                case 50:
                    dodajProizvod(de,null);
                    break;
                case 60:
                    dodajProdavacaDialog(de,null);
                    break;
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void dodajKupcaDialog(final dialogEnum dialogenum, final Kupac kupac){
        AlertDialog.Builder builder = new AlertDialog.Builder(Popis.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_dodaj_kupca,new LinearLayout(Popis.this),false);
        kupacIme = dialogView.findViewById(R.id.kupacIme);
        kupacPrezime = dialogView.findViewById(R.id.kupacPrezime);
        kupacAdresa = dialogView.findViewById(R.id.kupacAdresa);
        EditText kupacPBR = dialogView.findViewById(R.id.kupacPBR);
        kupacPBR.setVisibility(View.INVISIBLE);
        postanski_broj_picker = dialogView.findViewById(R.id.kupacPBR1);
        List<String> stringList = Arrays.asList(getResources().getStringArray(R.array.postanskiBrojevi));
        postanski_broj_picker.setMinValue(0);
        postanski_broj_picker.setMaxValue(stringList.size()-1);
        String[] niz = new String[stringList.size()];
        for(int i=0; i< stringList.size(); i++){
            niz[i] = String.valueOf(stringList.get(i));
        }
        postanski_broj_picker.setDisplayedValues(niz);
        Button spremiKupca = dialogView.findViewById(R.id.spremiKupca);
        ImageView izlaz_dodaj_kupca = dialogView.findViewById(R.id.izlaz_dodaj_kupca);
        if(dialogenum==dialogEnum.update) {
            TextView naslov_popup_dodaj_kupca = dialogView.findViewById(R.id.naslov_popup_dodaj_kupca);
            naslov_popup_dodaj_kupca.setText(getResources().getString(R.string.promijenite_podatke_o_kupcu_res));
            kupacIme.setText(kupac.getImeKupac());
            kupacIme.setGravity(Gravity.CENTER_HORIZONTAL);
            kupacPrezime.setText(kupac.getPrezimeKupac());
            kupacPrezime.setGravity(Gravity.CENTER_HORIZONTAL);
            if(kupac.getAdresa() == null){
                kupacAdresa.setText("");
            }else{
                kupacAdresa.setText(kupac.getAdresa());
            }
            for(int x=0; x<niz.length; x++){
                if(String.valueOf(kupac.getPbrMjesto()).equals(niz[x])){
                    postanski_broj_picker.setValue(x);
                }
            }

        }
        builder.setView(dialogView);
        final AlertDialog dialog;
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        izlaz_dodaj_kupca.setOnClickListener(v -> dialog.dismiss());
        spremiKupca.setOnClickListener(v -> {
            String ime = kupacIme.getText().toString();
            String prezime = kupacPrezime.getText().toString();
            String adresa = kupacAdresa.getText().toString();
            int pbr;
            pbr = postanski_broj_picker.getValue();
            if(ime.trim().isEmpty() || prezime.trim().isEmpty() || adresa.trim().isEmpty() || pbr==-1){
                Toast.makeText(Popis.this, R.string.toast_msg, Toast.LENGTH_LONG).show();
                return;
            }
            if(dialogenum==dialogEnum.insert) {
                Kupac kupac1 = new Kupac(ime, prezime, adresa, 0, Integer.valueOf(niz[pbr]));
                viewModel.unesiKupca(kupac1);
                dialog.dismiss();
            }else if(dialogenum==dialogEnum.update){
                Kupac k = new Kupac(ime, prezime, adresa, 0, Integer.valueOf(niz[pbr]));
                k.setId(kupac.getId());
                viewModel.promijeniPodatkeKupca(k);
                dialog.dismiss();
            }
        });
        if(dialog.getWindow()!=null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.show();
    }
    public enum dialogEnum{
        insert,
        update
    }
}
